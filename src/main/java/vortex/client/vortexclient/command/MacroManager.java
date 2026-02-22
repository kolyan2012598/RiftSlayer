package vortex.client.vortexclient.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MacroManager {
    public static final MacroManager INSTANCE = new MacroManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private File macroFile;
    private Map<String, String> macros;

    private File getMacroFile() {
        if (macroFile == null) {
            macroFile = new File(MinecraftClient.getInstance().runDirectory, "vortex/macros.json");
        }
        return macroFile;
    }

    public Map<String, String> getMacros() {
        if (macros == null) {
            loadMacros();
        }
        return macros;
    }

    public void loadMacros() {
        macros = new HashMap<>();
        File file = getMacroFile();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) return;
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<HashMap<String, String>>() {}.getType();
            macros = GSON.fromJson(reader, type);
            if (macros == null) {
                macros = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMacros() {
        if (macros == null) return;
        try (FileWriter writer = new FileWriter(getMacroFile())) {
            GSON.toJson(macros, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMacro(String name, String message) {
        if (macros == null) loadMacros();
        macros.put(name, message);
        saveMacros();
    }

    public void removeMacro(String name) {
        if (macros == null) loadMacros();
        macros.remove(name);
        saveMacros();
    }
}
//n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8n8a3xs4c5v6b7n8m9,im9un8a3xs4c5v6b7n8m9,im9un8
