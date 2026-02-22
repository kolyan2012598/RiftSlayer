package vortex.client.vortexclient.module.modules.movement;

import vortex.client.vortexclient.module.Module;

public class Step extends Module {
    public Step() {
        super("Step", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) mc.player.stepHeight = 1.0f;
    }

    @Override
    public void onDisable() {
        if (mc.player != null) mc.player.stepHeight = 0.6f;
    }
}
