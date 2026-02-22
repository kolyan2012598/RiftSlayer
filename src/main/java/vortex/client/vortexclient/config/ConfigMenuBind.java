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

public class ConfigMenuBind {
    public static final ConfigMenuBind INSTANCE = new ConfigMenuBind();
    private static final File CONFIG_FILE = new File(MinecraftClient.getInstance().runDirectory, "configmenubind.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public Map<String, Position> buttonPositions = new HashMap<>();

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(buttonPositions, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean load() {
        if (!CONFIG_FILE.exists()) {
            return false;
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Type type = new TypeToken<HashMap<String, Position>>() {}.getType();
            buttonPositions = GSON.fromJson(reader, type);
            if (buttonPositions == null) {
                buttonPositions = new HashMap<>();
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
