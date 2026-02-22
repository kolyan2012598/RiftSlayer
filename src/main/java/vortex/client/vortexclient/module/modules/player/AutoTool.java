package vortex.client.vortexclient.module.modules.player;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import vortex.client.vortexclient.module.Module;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", Category.PLAYER);
    }

    public void equipBestTool(BlockPos pos) {
        if (!isEnabled() || mc.player == null || mc.world == null) return;

        BlockState state = mc.world.getBlockState(pos);
        int bestSlot = -1;
        float bestSpeed = 0;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStack(i);
            if (stack.isEmpty()) continue;

            float speed = stack.getMiningSpeedMultiplier(state);
            if (speed > 1) {
                int efficiency = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
                if (efficiency > 0) {
                    speed += (float) (efficiency * efficiency + 1);
                }
            }

            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
        }

        if (bestSlot != -1) {
            mc.player.inventory.selectedSlot = bestSlot;
        }
    }
}
