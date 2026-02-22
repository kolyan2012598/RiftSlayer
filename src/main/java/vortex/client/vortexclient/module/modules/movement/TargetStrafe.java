package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.combat.KillAura;

public class TargetStrafe extends Module {

    private boolean clockwise = true;
    private double strafeRadius = 2.5;
    private double strafeSpeed = 0.25;

    public TargetStrafe() {
        super("TargetStrafe", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        KillAura killAura = ModuleManager.INSTANCE.getModule(KillAura.class);
        if (killAura == null || !killAura.isEnabled()) return;

        // ПРАВИЛЬНЫЙ СПОСОБ: Получаем цель из публичного поля
        PlayerEntity target = killAura.target;
        if (target == null) return;

        strafe(target);
    }

    private void strafe(PlayerEntity target) {
        Vec3d playerPos = mc.player.getPos();
        Vec3d targetPos = target.getPos();

        double dx = playerPos.x - targetPos.x;
        double dz = playerPos.z - targetPos.z;

        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance > strafeRadius) {
            mc.player.setVelocity(
                    (targetPos.x - playerPos.x) * strafeSpeed,
                    mc.player.getVelocity().y,
                    (targetPos.z - playerPos.z) * strafeSpeed
            );
            return;
        }

        double angle = Math.atan2(dz, dx);
        angle += clockwise ? Math.PI / 60 : -Math.PI / 60;

        double newX = targetPos.x + strafeRadius * Math.cos(angle);
        double newZ = targetPos.z + strafeRadius * Math.sin(angle);

        mc.player.setVelocity(
                (newX - playerPos.x) * strafeSpeed,
                mc.player.getVelocity().y,
                (newZ - playerPos.z) * strafeSpeed
        );
    }
}
