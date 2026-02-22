package vortex.client.vortexclient.module.modules.player;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import vortex.client.vortexclient.module.Module;

public class FakeCreative extends Module {
    public FakeCreative() {
        super("FakeCreative", Category.PLAYER);
        this.enabled = false;
    }

    @Override
    public void onEnable() {
        if (mc.player == null) return;
        mc.player.abilities.allowFlying = true;
        mc.player.abilities.flying = true;
        mc.openScreen(new CreativeInventoryScreen(mc.player));
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        mc.player.abilities.allowFlying = false;
        mc.player.abilities.flying = false;
    }
}
