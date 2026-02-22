package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import vortex.client.vortexclient.module.Module;

public class AirStuck extends Module {

    private double stuckX;
    private double stuckY;
    private double stuckZ;

    public AirStuck() {
        super("AirStuck", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (mc.player == null) {
            setEnabled(false);
            return;
        }
        // Сохраняем позицию при включении
        this.stuckX = mc.player.getX();
        this.stuckY = mc.player.getY();
        this.stuckZ = mc.player.getZ();
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.player.networkHandler == null) {
            return;
        }

        // 1. Визуально замораживаем игрока на клиенте
        mc.player.setVelocity(0, 0, 0);

        // 2. Постоянно отправляем серверу пакет с замороженной позицией
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(stuckX, stuckY, stuckZ, mc.player.isOnGround()));
    }
}
