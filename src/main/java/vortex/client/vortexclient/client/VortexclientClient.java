package vortex.client.vortexclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import vortex.client.vortexclient.config.ConfigManager;
import vortex.client.vortexclient.gui.ClickGui;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.service.ServiceManager;

public class VortexclientClient implements ClientModInitializer {

    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        ConfigManager.INSTANCE.init();

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("Open GUI", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "VortexClient"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBinding.wasPressed()) {
                client.openScreen(new ClickGui());
            }
            
            if (client.player != null) {
                ModuleManager.INSTANCE.onTick();
                ServiceManager.INSTANCE.onTick();
                // Проверка вайтлиста во время игры ("Удар Молота") удалена.
            }
        });
    }
}
