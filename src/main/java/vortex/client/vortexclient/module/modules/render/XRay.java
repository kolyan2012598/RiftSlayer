package vortex.client.vortexclient.module.modules.render;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import vortex.client.vortexclient.module.Module;
import java.util.ArrayList;
import java.util.List;

public class XRay extends Module {
    public static List<Block> blocks = new ArrayList<>();
    private static boolean enabled = false;

    public XRay() {
        super("XRay", Category.RENDER);
        blocks.add(Blocks.DIAMOND_ORE);
        blocks.add(Blocks.GOLD_ORE);
        blocks.add(Blocks.IRON_ORE);
        blocks.add(Blocks.COAL_ORE);
        blocks.add(Blocks.LAPIS_ORE);
        blocks.add(Blocks.REDSTONE_ORE);
        blocks.add(Blocks.EMERALD_ORE);
        blocks.add(Blocks.NETHER_GOLD_ORE);
        blocks.add(Blocks.NETHER_QUARTZ_ORE);
        blocks.add(Blocks.ANCIENT_DEBRIS);
        blocks.add(Blocks.CHEST);
        blocks.add(Blocks.ENDER_CHEST);
    }

    public static boolean isXrayEnabled() {
        return enabled;
    }

    @Override
    public void onEnable() {
        enabled = true;
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        enabled = false;
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }
}
