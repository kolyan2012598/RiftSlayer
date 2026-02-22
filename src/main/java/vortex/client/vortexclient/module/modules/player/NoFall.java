package vortex.client.vortexclient.module.modules.player;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import vortex.client.vortexclient.module.Module;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Category.PLAYER);
    }

    @Override
    public void onTick() {
        // ПРАВИЛЬНЫЙ И БЕЗОПАСНЫЙ СПОСОБ:
        // Проверяем, что мы в мире, что сетевой обработчик существует,
        // и что мы действительно падаем, прежде чем отправлять пакет.
        if (mc.player != null && mc.player.networkHandler != null && mc.player.fallDistance > 2.0f) {
            // Отправляем пакет, который сообщает серверу, что мы на земле
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
        }
    }
}
