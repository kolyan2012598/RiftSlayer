package vortex.client.vortexclient.module.modules.trolling;

import vortex.client.vortexclient.module.Module;

public class Spinner extends Module {
    private float spin = 0;

    public Spinner() {
        super("Spinner", Category.TROLLING);
    }

    @Override
    public void onTick() {
        if (mc.player != null) {
            spin += 30;
            if (spin > 360) spin = 0;
            mc.player.yaw = spin;
        }
    }
}
