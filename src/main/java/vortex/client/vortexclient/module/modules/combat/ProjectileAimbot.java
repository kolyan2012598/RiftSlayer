package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

import java.util.Comparator;

public class ProjectileAimbot extends Module {
    public NumberSetting range = new NumberSetting("Range", this, 100, 10, 200);

    public ProjectileAimbot() {
        super("ProjectileAimbot", Category.COMBAT);
        settings.add(range);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        if (!(mc.player.getMainHandStack().getItem() instanceof BowItem || 
              mc.player.getMainHandStack().getItem() instanceof CrossbowItem)) {
            return;
        }

        PlayerEntity target = mc.world.getPlayers().stream()
                .filter(p -> p != mc.player && p.isAlive() && mc.player.distanceTo(p) <= range.value)
                .min(Comparator.comparing(p -> mc.player.distanceTo(p)))
                .orElse(null);

        if (target != null) {
            float[] rotations = getRotations(target);
            mc.player.yaw = rotations[0];
            mc.player.pitch = rotations[1];
        }
    }

    private float[] getRotations(PlayerEntity target) {
        double x = target.getX() - mc.player.getX();
        double y = target.getBodyY(0.5) - mc.player.getEyeY();
        double z = target.getZ() - mc.player.getZ();
        
        double dist = Math.sqrt(x * x + z * z);
        float velocity = 1.5f;
        float gravity = 0.05f;

        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90;
        float pitch = (float) -Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(Math.pow(velocity, 4) - gravity * (gravity * dist * dist + 2 * y * velocity * velocity))) / (gravity * dist)));

        return new float[]{yaw, pitch};
    }
}
