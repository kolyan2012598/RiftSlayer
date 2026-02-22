package vortex.client.vortexclient.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import vortex.client.vortexclient.module.settings.Setting;

import java.util.ArrayList;
import java.util.List;

public class Module {
    public String name;
    public Category category;
    public boolean enabled;
    public int key;
    public List<Setting> settings = new ArrayList<>();
    public boolean settingsOpen = false;
    public MinecraftClient mc = MinecraftClient.getInstance(); // ПРАВИЛЬНЫЙ СПОСОБ: Делаем поле публичным

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) {
            onEnable();
            sendMessage(Formatting.GREEN + "Enabled!");
        } else {
            onDisable();
            sendMessage(Formatting.RED + "Disabled!");
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) onEnable();
        else onDisable();
    }

    private void sendMessage(String message) {
        if (mc.player != null) {
            mc.player.sendMessage(new LiteralText(Formatting.BOLD + "[" + this.name + "] " + Formatting.RESET + message), false);
        }
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    public void onWorldRender(MatrixStack matrices) {}
    public void onRender2D(MatrixStack matrices) {}

    public String getName() { return name; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }

    public enum Category {
        COMBAT, MOVEMENT, PLAYER, RENDER, MISC, TROLLING, VISUALS, BYPASS
    }
}
