package vortex.client.vortexclient.binds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.MinecraftClient;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BindManager {
    public static final BindManager INSTANCE = new BindManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private File bindFile;
    private Map<String, String> binds;

    private File getBindFile() {
        if (bindFile == null) {
            bindFile = new File(MinecraftClient.getInstance().runDirectory, "riftslayer/binds.json");
        }
        return bindFile;
    }

    public Map<String, String> getBinds() {
        if (binds == null) {
            loadBinds();
        }
        return binds;
    }

    public void setBinds(Map<String, String> newBinds) {
        this.binds = newBinds;
        saveBinds();
    }

    public void addBind(String moduleName, String keyName) {
        if (binds == null) loadBinds();
        binds.put(moduleName, keyName.toUpperCase());
        saveBinds();
    }

    public void removeBind(String moduleName) {
        if (binds == null) loadBinds();
        binds.remove(moduleName);
        saveBinds();
    }

    public void saveBinds() {
        if (binds == null) return;
        File file = getBindFile();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(binds, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBinds() {
        binds = new HashMap<>();
        File file = getBindFile();
        if (!file.exists()) return;
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<HashMap<String, String>>() {}.getType();
            binds = GSON.fromJson(reader, type);
            if (binds == null) {
                binds = new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
