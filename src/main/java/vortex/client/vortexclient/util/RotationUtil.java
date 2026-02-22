package vortex.client.vortexclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static float[] getRotations(Entity entity) {
        // ПРАВИЛЬНЫЙ И 100% НАДЕЖНЫЙ СПОСОБ
        return getRotations(new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ()));
    }

    public static float[] getRotations(Vec3d pos) {
        double dx = pos.x - mc.player.getX();
        double dy = pos.y - mc.player.getEyeY();
        double dz = pos.z - mc.player.getZ();
        double h = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, h));
        return new float[]{yaw, pitch};
    }

    public static float getYawToEntity(Entity entity) {
        return getRotations(entity)[0];
    }

    public static float smooth(float current, float target, float factor) {
        return current + MathHelper.wrapDegrees(target - current) * factor;
    }

    public static boolean isLookingAt(PlayerEntity entity, Vec3d targetPos, double maxAngle) {
        float[] rotations = getRotations(targetPos);
        float yawDiff = Math.abs(MathHelper.wrapDegrees(entity.yaw - rotations[0]));
        float pitchDiff = Math.abs(MathHelper.wrapDegrees(entity.pitch - rotations[1]));
        return yawDiff <= maxAngle && pitchDiff <= maxAngle;
    }
}
