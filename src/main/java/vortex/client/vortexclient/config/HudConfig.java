package vortex.client.vortexclient.config;

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

public class HudConfig {
    public static final HudConfig INSTANCE = new HudConfig();
    private static final File CONFIG_FILE = new File(MinecraftClient.getInstance().runDirectory, "vortex/hud_config.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public Map<String, Position> componentPositions = new HashMap<>();

    private HudConfig() {
        // Добавляем позиции по умолчанию
        componentPositions.put("Watermark", new Position(5, 5));
        componentPositions.put("ArrayList", new Position(0, 5)); // X будет рассчитан
        componentPositions.put("TargetHUD", new Position(300, 50));
    }

    public void save() {
        CONFIG_FILE.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(componentPositions, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (!CONFIG_FILE.exists()) {
            return;
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Type type = new TypeToken<HashMap<String, Position>>() {}.getType();
            Map<String, Position> loadedPositions = GSON.fromJson(reader, type);
            if (loadedPositions != null) {
                componentPositions.putAll(loadedPositions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
