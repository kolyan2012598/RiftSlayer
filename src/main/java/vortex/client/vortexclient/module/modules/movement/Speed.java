package vortex.client.vortexclient.module.modules.movement;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.modules.bypass.Bypass;
import vortex.client.vortexclient.module.settings.ModeSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class Speed extends Module {
    public ModeSetting mode = new ModeSetting("Mode", this, "Hypixel", "Strafe", "Hop", "LowHop", "Hypixel", "MatrixHop");
    public NumberSetting speed = new NumberSetting("Speed", this, 1.2, 1.0, 2.0);

    public Speed() {
        super("Speed", Category.MOVEMENT);
        settings.add(mode);
        settings.add(speed);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.player.input.movementForward == 0 && mc.player.input.movementSideways == 0) return;

        String bypassMode = Bypass.getBypassMode();

        if (mc.player.isOnGround()) {
            switch (mode.getMode()) {
                case "Hop":
                    mc.player.jump();
                    mc.player.setVelocity(mc.player.getVelocity().x * speed.value, mc.player.getVelocity().y, mc.player.getVelocity().z * speed.value);
                    break;
                case "LowHop":
                    mc.player.jump();
                    mc.player.setVelocity(mc.player.getVelocity().x, 0.3, mc.player.getVelocity().z);
                    break;
                case "Hypixel":
                    mc.player.jump();
                    break;
                case "MatrixHop":
                    mc.player.jump();
                    mc.player.setVelocity(mc.player.getVelocity().x * 1.05, 0.42, mc.player.getVelocity().z * 1.05);
                    break;
            }
        }
        
        if (mode.getMode().equals("Strafe")) {
            mc.player.setVelocity(mc.player.getVelocity().x * speed.value, mc.player.getVelocity().y, mc.player.getVelocity().z * speed.value);
        }
    }
}
