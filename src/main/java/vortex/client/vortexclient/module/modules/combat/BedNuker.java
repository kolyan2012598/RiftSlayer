package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.block.BedBlock;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

import java.util.ArrayList;
import java.util.List;

public class BedNuker extends Module {
    public NumberSetting range = new NumberSetting("Range", this, 5, 1, 7);

    public BedNuker() {
        super("BedNuker", Category.COMBAT);
        settings.add(range);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        for (BlockPos pos : getBeds()) {
            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, Direction.UP));
            mc.player.swingHand(Hand.MAIN_HAND);
            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, Direction.UP));
        }
    }

    private List<BlockPos> getBeds() {
        List<BlockPos> beds = new ArrayList<>();
        for (int x = (int) -range.value; x < range.value; x++) {
            for (int y = (int) -range.value; y < range.value; y++) {
                for (int z = (int) -range.value; z < range.value; z++) {
                    BlockPos pos = mc.player.getBlockPos().add(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock() instanceof BedBlock) {
                        beds.add(pos);
                    }
                }
            }
        }
        return beds;
    }
}
