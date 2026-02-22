package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class GodBridge extends Module {

    public NumberSetting delay = new NumberSetting("Delay", this, 60, 0, 500);

    private long lastPlaceTime = 0L;

    public GodBridge() {
        super("GodBridge", Category.MOVEMENT);
        settings.add(delay);
    }

    @Override
    public void onTick() {
        if (mc == null || mc.player == null || mc.world == null)
            return;

        if (!mc.player.isOnGround())
            return;

        long now = System.currentTimeMillis();
        if (now - lastPlaceTime < delay.value)
            return;

        BlockPos playerPos = mc.player.getBlockPos();
        BlockPos placePos = playerPos.down();

        if (!mc.world.getBlockState(placePos).getMaterial().isReplaceable())
            return;

        Direction supportSide = getSupportSide(placePos);
        if (supportSide == null)
            return;

        int blockSlot = findBlockSlot();
        if (blockSlot == -1)
            return;

        int oldSlot = mc.player.inventory.selectedSlot;
        mc.player.inventory.selectedSlot = blockSlot;

        BlockPos supportPos = placePos.offset(supportSide);

        Vec3d hitVec = Vec3d.ofCenter(supportPos);
        BlockHitResult hitResult = new BlockHitResult(
                hitVec,
                supportSide.getOpposite(),
                supportPos,
                false
        );

        mc.interactionManager.interactBlock(
                mc.player,
                mc.world,
                Hand.MAIN_HAND,
                hitResult
        );

        mc.player.inventory.selectedSlot = oldSlot;
        lastPlaceTime = now;
    }

    private Direction getSupportSide(BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (dir == Direction.UP)
                continue;

            BlockPos neighbor = pos.offset(dir);

            if (!mc.world.getBlockState(neighbor).getMaterial().isReplaceable())
                return dir;
        }
        return null;
    }

    private int findBlockSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStack(i);

            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem)
                return i;
        }
        return -1;
    }
}
