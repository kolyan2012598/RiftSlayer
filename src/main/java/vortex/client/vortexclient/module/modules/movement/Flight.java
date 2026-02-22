package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.ModeSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class Flight extends Module {
    public ModeSetting mode = new ModeSetting("Mode", this, "Vulcan", "Vanilla", "Vulcan", "Grim");
    public NumberSetting speed = new NumberSetting("Speed", this, 1.0, 0.1, 10.0);

    public Flight() {
        super("Flight", Category.MOVEMENT);
        settings.add(mode);
        settings.add(speed);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;

        switch (mode.getMode()) {
            case "Vanilla":
                mc.player.abilities.flying = true;
                mc.player.abilities.setFlySpeed((float)speed.value / 10f);
                break;
            case "Vulcan":
                handleVulcanFlight();
                break;
            case "Grim":
                handleGrimFlight();
                break;
        }
    }

    private void handleVulcanFlight() {
        if (mc.player.age % 20 == 0) {
            mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        }
        Vec3d lookVec = mc.player.getRotationVector();
        mc.player.setVelocity(lookVec.x * speed.value, 0, lookVec.z * speed.value);
    }

    private void handleGrimFlight() {
        mc.player.setVelocity(0, -0.01, 0);
        if (mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0) {
            Vec3d forward = new Vec3d(0, 0, speed.value).rotateY(-(float)Math.toRadians(mc.player.yaw));
            mc.player.addVelocity(forward.x, 0, forward.z);
        }
    }
    
    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.abilities.flying = false;
        }
    }
}
