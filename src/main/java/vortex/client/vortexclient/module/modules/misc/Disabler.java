package vortex.client.vortexclient.module.modules.misc;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class Disabler extends Module {
    public NumberSetting amount = new NumberSetting("Amount", this, 20, 1, 100);

    public Disabler() {
        super("Disabler", Category.MISC);
        settings.add(amount);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.player.networkHandler == null) return;

        for (int i = 0; i < amount.value; i++) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(
                    mc.player.getX(),
                    mc.player.getY() + 1e-6, // Отправляем слегка измененную позицию, чтобы пакет не игнорировался
                    mc.player.getZ(),
                    false
            ));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(
                    mc.player.getX(),
                    mc.player.getY() - 1e-6,
                    mc.player.getZ(),
                    true
            ));
        }
    }
}
