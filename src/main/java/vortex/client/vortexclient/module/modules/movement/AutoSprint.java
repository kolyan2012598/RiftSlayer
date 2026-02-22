package vortex.client.vortexclient.module.modules.movement;

import vortex.client.vortexclient.module.Module;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player != null && mc.player.forwardSpeed > 0 && !mc.player.isSprinting()) {
            mc.player.setSprinting(true);
        }
    }
}
