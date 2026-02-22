package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;

public class Surround extends Module {
    public Surround() {
        super("Surround", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        int obsidianSlot = findObsidian();
        if (obsidianSlot == -1) {
            return;
        }

        BlockPos playerPos = mc.player.getBlockPos();
        BlockPos[] targets = {
                playerPos.north(),
                playerPos.south(),
                playerPos.east(),
                playerPos.west()
        };

        int oldSlot = mc.player.inventory.selectedSlot;
        mc.player.inventory.selectedSlot = obsidianSlot;

        for (BlockPos target : targets) {
            if (mc.world.getBlockState(target).getMaterial().isReplaceable()) {
                mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, new BlockHitResult(
                        new Vec3d(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5),
                        Direction.UP,
                        target,
                        false
                ));
            }
        }

        mc.player.inventory.selectedSlot = oldSlot;
    }

    private int findObsidian() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStack(i);
            if (stack.getItem() == Blocks.OBSIDIAN.asItem()) {
                return i;
            }
        }
        return -1;
    }
}
