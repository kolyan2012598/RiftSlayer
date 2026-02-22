package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.modules.bypass.Bypass;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class Jetpack extends Module {
    public NumberSetting speed = new NumberSetting("Speed", this, 0.5, 0.1, 2.0);
    
    public Jetpack() {
        super("Jetpack", Category.MOVEMENT);
        settings.add(speed);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;

        String bypassMode = Bypass.getBypassMode();

        switch (bypassMode) {
            case "Vanilla":
                handleVanilla();
                break;
            case "Intave":
                handleIntave();
                break;
            case "Vulcan":
                handleVulcan();
                break;
            case "Grim":
                handleGrim();
                break;
        }
    }

    private void handleVanilla() {
        if (mc.options.keyJump.isPressed()) {
            mc.player.addVelocity(0, speed.value / 2, 0);
        }
    }

    private void handleIntave() {
        if (mc.options.keyJump.isPressed()) {
            if (mc.player.age % 4 == 0) {
                mc.player.setVelocity(mc.player.getVelocity().x, 0.42F, mc.player.getVelocity().z);
            }
        }
    }
    
    private void handleVulcan() {
        if (mc.options.keyJump.isPressed()) {
            if (mc.player.age % 20 < 10) {
                mc.player.setVelocity(mc.player.getVelocity().x, -0.1, mc.player.getVelocity().z);
            } else {
                if (mc.player.isOnGround()) mc.player.jump();
            }
        }
    }

    private void handleGrim() {
        if (mc.options.keyJump.isPressed()) {
            mc.player.setOnGround(true);
            mc.player.jump();
        }
    }
}
