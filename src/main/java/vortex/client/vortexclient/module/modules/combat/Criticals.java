package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import vortex.client.vortexclient.module.Module;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", Category.COMBAT);
    }

    public void onAttack() {
        if (mc.player == null || !mc.player.isOnGround()) return;

        // ПРАВИЛЬНЫЙ И РАБОЧИЙ СПОСОБ ДЛЯ 1.16.5
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(mc.player.getX(), mc.player.getY() + 0.0625, mc.player.getZ(), true));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
    }
}
