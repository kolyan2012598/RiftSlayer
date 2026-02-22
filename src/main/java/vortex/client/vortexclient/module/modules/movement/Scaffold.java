package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.BooleanSetting;
import vortex.client.vortexclient.util.RotationUtil;

public class Scaffold extends Module {
    public BooleanSetting tower = new BooleanSetting("Tower", this, true);
    public BooleanSetting safeWalk = new BooleanSetting("SafeWalk", this, true);
    
    private float[] rotations;

    public Scaffold() {
        super("Scaffold", Category.MOVEMENT);
        settings.add(tower);
        settings.add(safeWalk);
    }
    
    @Override
    public void onDisable() {
        if (mc.options != null) mc.options.keySneak.setPressed(false);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        
        if (safeWalk.enabled) {
            mc.options.keySneak.setPressed(mc.world.isAir(mc.player.getBlockPos().down()));
        }

        BlockPos placePos = mc.player.getBlockPos().down();
        if (!mc.world.getBlockState(placePos).getMaterial().isReplaceable()) return;

        rotations = RotationUtil.getRotations(Vec3d.ofCenter(placePos).add(0.5, 0.5, 0.5));
        
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Both(mc.player.getX(), mc.player.getY(), mc.player.getZ(), rotations[0], rotations[1], mc.player.isOnGround()));

        int blockSlot = findBlockSlot();
        if (blockSlot == -1) return;

        int oldSlot = mc.player.inventory.selectedSlot;
        mc.player.inventory.selectedSlot = blockSlot;

        mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, 
            new BlockHitResult(Vec3d.ofCenter(placePos), Direction.UP, placePos, false));
        
        mc.player.inventory.selectedSlot = oldSlot;

        if (tower.enabled && mc.options.keyJump.isPressed()) {
            mc.player.setVelocity(0, 0.42, 0);
        }
    }

    private int findBlockSlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStack(i).getItem() instanceof BlockItem) return i;
        }
        return -1;
    }
}
