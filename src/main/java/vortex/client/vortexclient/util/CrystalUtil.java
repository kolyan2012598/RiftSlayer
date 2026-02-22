package vortex.client.vortexclient.util;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;

import java.util.ArrayList;
import java.util.List;

public class CrystalUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static List<BlockPos> getPlaceableBlocks(double range) {
        List<BlockPos> list = new ArrayList<>();
        for (int x = (int) -range; x < range; x++) {
            for (int y = (int) -range; y < range; y++) {
                for (int z = (int) -range; z < range; z++) {
                    BlockPos pos = mc.player.getBlockPos().add(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(pos).getBlock() == Blocks.BEDROCK) {
                        if (mc.world.getBlockState(pos.up()).isAir()) {
                            list.add(pos);
                        }
                    }
                }
            }
        }
        return list;
    }

    public static float getDamage(BlockPos pos, PlayerEntity target) {
        Vec3d crystalPos = Vec3d.ofCenter(pos).add(0, 1, 0);
        float power = 12.0f;
        double exposure = Explosion.getExposure(crystalPos, target);
        double dist = target.getPos().distanceTo(crystalPos);
        double impact = (1.0 - dist / power) * exposure;
        
        float damage = (float) ((impact * impact + impact) / 2.0 * 7.0 * power + 1.0);
        
        return DamageUtil.getDamageLeft(damage, (float)target.getArmor(), (float)target.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
    }
}
