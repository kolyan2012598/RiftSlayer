package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.ModeSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class ElytraFly extends Module {
    public ModeSetting mode = new ModeSetting("Mode", this, "Control", "Control", "Boost", "Deception");
    public NumberSetting speed = new NumberSetting("Speed", this, 1.8, 0.1, 5.0);
    public NumberSetting vSpeed = new NumberSetting("VSpeed", this, 1.0, 0.1, 5.0);

    public ElytraFly() {
        super("ElytraFly", Category.MOVEMENT);
        settings.add(mode);
        settings.add(speed);
        settings.add(vSpeed);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.inventory.getArmorStack(2).getItem() != Items.ELYTRA) return;

        if (!mc.player.isFallFlying()) {
            if (!mc.player.isOnGround() && mc.player.fallDistance > 0.1) {
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }
            return;
        }

        if (mode.getMode().equals("Control") || mode.getMode().equals("Deception")) {
            handleControlFlight();
        }
    }

    private void handleControlFlight() {
        mc.player.abilities.flying = false;
        mc.player.setVelocity(0, 0, 0);
        
        double ySpeed = 0;
        if (mc.options.keyJump.isPressed()) ySpeed = vSpeed.value / 2;
        else if (mc.options.keySneak.isPressed()) ySpeed = -vSpeed.value / 2;
        
        Vec3d moveVec = getMoveVec().multiply(speed.value);
        mc.player.setVelocity(moveVec.x, ySpeed, moveVec.z);
    }

    private Vec3d getMoveVec() {
        float moveForward = mc.player.input.movementForward;
        float moveSideways = mc.player.input.movementSideways;
        float yaw = mc.player.yaw;

        if (moveForward == 0 && moveSideways == 0) return Vec3d.ZERO;
        
        double moveX = Math.sin(Math.toRadians(yaw)) * -moveForward + Math.cos(Math.toRadians(yaw)) * moveSideways;
        double moveZ = Math.cos(Math.toRadians(yaw)) * moveForward + Math.sin(Math.toRadians(yaw)) * moveSideways;
        return new Vec3d(moveX, 0, moveZ).normalize();
    }
    
    public boolean isDeceptionMode() {
        return isEnabled() && mode.getMode().equals("Deception");
    }
    
    public void onLagBack() {}
}
