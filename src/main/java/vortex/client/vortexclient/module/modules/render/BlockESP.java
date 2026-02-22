package vortex.client.vortexclient.module.modules.render;

import net.minecraft.block.Block;
import vortex.client.vortexclient.module.Module;
import java.util.ArrayList;
import java.util.List;

public class BlockESP extends Module {
    public static List<Block> blocks = new ArrayList<>();

    public BlockESP() {
        super("BlockESP", Category.RENDER);
    }
    // Logic will be in a mixin
}
