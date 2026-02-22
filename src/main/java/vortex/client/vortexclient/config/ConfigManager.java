package vortex.client.vortexclient.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.settings.BooleanSetting;
import vortex.client.vortexclient.module.settings.ModeSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;
import vortex.client.vortexclient.module.settings.Setting;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    public static ConfigManager INSTANCE;
    private File configDir;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public ConfigManager() {
        INSTANCE = this;
    }

    public void init() {
        this.configDir = new File(MinecraftClient.getInstance().runDirectory, "vortexclient");
        if (!configDir.exists()) configDir.mkdirs();
        loadConfig("default");
    }

    public void saveConfig(String name) {
        if (configDir == null) return;
        File configFile = new File(configDir, name + ".json");
        JsonObject json = new JsonObject();
        JsonObject modules = new JsonObject();

        for (Module module : ModuleManager.INSTANCE.getModules()) {
            JsonObject moduleConfig = new JsonObject();
            moduleConfig.addProperty("enabled", module.isEnabled());
            moduleConfig.addProperty("key", module.getKey());
            JsonObject settings = new JsonObject();
            for (Setting setting : module.settings) {
                if (setting instanceof BooleanSetting) settings.addProperty(setting.name, ((BooleanSetting) setting).enabled);
                else if (setting instanceof NumberSetting) settings.addProperty(setting.name, ((NumberSetting) setting).value);
                else if (setting instanceof ModeSetting) settings.addProperty(setting.name, ((ModeSetting) setting).getMode());
            }
            moduleConfig.add("settings", settings);
            modules.add(module.getName(), moduleConfig);
        }
        json.add("modules", modules);

        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(String name) {
        if (configDir == null) return;
        File configFile = new File(configDir, name + ".json");
        if (!configFile.exists()) return;

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            if (json == null || !json.has("modules")) return;
            JsonObject modules = json.getAsJsonObject("modules");
            for (Module module : ModuleManager.INSTANCE.getModules()) {
                if (modules.has(module.getName())) {
                    JsonObject moduleConfig = modules.getAsJsonObject(module.getName());
                    module.setEnabled(moduleConfig.get("enabled").getAsBoolean());
                    module.setKey(moduleConfig.get("key").getAsInt());
                    if (moduleConfig.has("settings")) {
                        JsonObject settings = moduleConfig.getAsJsonObject("settings");
                        for (Setting setting : module.settings) {
                            if (settings.has(setting.name)) {
                                if (setting instanceof BooleanSetting) ((BooleanSetting) setting).enabled = settings.get(setting.name).getAsBoolean();
                                else if (setting instanceof NumberSetting) ((NumberSetting) setting).value = settings.get(setting.name).getAsDouble();
                                else if (setting instanceof ModeSetting) ((ModeSetting) setting).setMode(settings.get(setting.name).getAsString());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File[] getConfigFiles() {
        if (configDir == null) return new File[0];
        return configDir.listFiles((dir, name) -> name.endsWith(".json"));
    }

    public void removeConfig(String name) {
        if (configDir == null) return;
        File configFile = new File(configDir, name + ".json");
        if (configFile.exists()) configFile.delete();
    }
}
