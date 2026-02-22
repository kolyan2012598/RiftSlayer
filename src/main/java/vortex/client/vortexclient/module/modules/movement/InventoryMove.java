package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import vortex.client.vortexclient.module.Module;

public class InventoryMove extends Module {
    public InventoryMove() {
        super("InventoryMove", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.currentScreen == null || mc.currentScreen instanceof ChatScreen) {
            return;
        }

        mc.options.keyForward.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_W));
        mc.options.keyBack.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_S));
        mc.options.keyLeft.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_A));
        mc.options.keyRight.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_D));
        mc.options.keyJump.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_SPACE));
        mc.options.keySprint.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL));
    }
}
