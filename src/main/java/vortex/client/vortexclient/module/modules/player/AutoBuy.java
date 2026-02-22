package vortex.client.vortexclient.module.modules.player;

import net.minecraft.item.Item;
import vortex.client.vortexclient.module.Module;

import java.util.ArrayList;
import java.util.List;

public class AutoBuy extends Module {
    public static final List<Item> itemsToBuy = new ArrayList<>();
    public static boolean buyTeamWool = false;
    public static boolean shouldBuy = false;

    public AutoBuy() {
        super("AutoBuy", Category.PLAYER);
    }
}
