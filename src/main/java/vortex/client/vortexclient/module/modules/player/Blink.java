package vortex.client.vortexclient.module.modules.player;

import net.minecraft.network.Packet;
import vortex.client.vortexclient.module.Module;

import java.util.ArrayList;
import java.util.List;

public class Blink extends Module {
    private final List<Packet<?>> packets = new ArrayList<>();

    public Blink() {
        super("Blink", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        packets.clear();
    }

    @Override
    public void onDisable() {
        if (mc.player == null || mc.player.networkHandler == null) {
            packets.clear();
            return;
        }
        
        // Отправляем все накопленные пакеты
        for (Packet<?> packet : packets) {
            mc.player.networkHandler.sendPacket(packet);
        }
        packets.clear();
    }

    public void addPacket(Packet<?> packet) {
        packets.add(packet);
    }
}
