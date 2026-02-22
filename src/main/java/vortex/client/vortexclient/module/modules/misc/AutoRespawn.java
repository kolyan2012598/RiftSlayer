package vortex.client.vortexclient.module.modules.misc;

import net.minecraft.client.gui.screen.DeathScreen;
import vortex.client.vortexclient.module.Module;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", Category.MISC);
    }

    @Override
    public void onTick() {
        if (mc.player != null && mc.currentScreen instanceof DeathScreen) {
            mc.player.requestRespawn();
            mc.openScreen(null);
        }
    }
}
