package vortex.client.vortexclient.ui.alt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AltManager {
    public static final AltManager INSTANCE = new AltManager();
    private static final File ALT_FILE = new File(MinecraftClient.getInstance().runDirectory, "vortex/alts.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private List<Alt> alts = new ArrayList<>();

    public void loadAlts() {
        if (!ALT_FILE.exists()) return;
        try (FileReader reader = new FileReader(ALT_FILE)) {
            Type type = new TypeToken<ArrayList<Alt>>() {}.getType();
            alts = GSON.fromJson(reader, type);
            if (alts == null) {
                alts = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAlts() {
        try (FileWriter writer = new FileWriter(ALT_FILE)) {
            GSON.toJson(alts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Alt> getAlts() {
        return alts;
    }

    public void addAlt(Alt alt) {
        alts.add(alt);
        saveAlts();
    }

    public void removeAlt(Alt alt) {
        alts.remove(alt);
        saveAlts();
    }
}
