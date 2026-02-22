package vortex.client.vortexclient.module.modules.movement;

import vortex.client.vortexclient.module.Module;

public class Spider extends Module {
    public Spider() {
        super("Spider", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player != null && mc.player.horizontalCollision) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.2, mc.player.getVelocity().z);
        }
    }
}
