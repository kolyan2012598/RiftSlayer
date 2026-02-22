package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.block.Blocks;
import vortex.client.vortexclient.module.Module;

public class Jesus extends Module {
    public Jesus() {
        super("Jesus", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player != null && mc.world != null && mc.player.isTouchingWater() && !mc.player.isSneaking()) {
            if (mc.world.getBlockState(mc.player.getBlockPos().down()).isOf(Blocks.WATER)) {
                mc.player.setVelocity(mc.player.getVelocity().x, 0.1, mc.player.getVelocity().z);
            }
        }
    }
}
