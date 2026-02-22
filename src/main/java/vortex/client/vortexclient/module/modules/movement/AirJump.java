package vortex.client.vortexclient.module.modules.movement;

import vortex.client.vortexclient.module.Module;

public class AirJump extends Module {

    private boolean wasJumping;

    public AirJump() {
        super("AirJump", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;

        boolean jumping = mc.options.keyJump.isPressed();

        // Если нажали пробел в воздухе
        if (jumping && !wasJumping && !mc.player.isOnGround()) {
            mc.player.jump();
        }

        wasJumping = jumping;
    }
}
