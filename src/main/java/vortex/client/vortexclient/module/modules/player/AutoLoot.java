package vortex.client.vortexclient.module.modules.player;

import net.minecraft.entity.ItemEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

import java.util.Comparator;
import java.util.stream.StreamSupport;

public class AutoLoot extends Module {
    public NumberSetting range = new NumberSetting("Range", this, 5.0, 1.0, 10.0);

    public AutoLoot() {
        super("AutoLoot", Category.PLAYER);
        settings.add(range);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        // ПРАВИЛЬНЫЙ СПОСОБ: Используем StreamSupport для создания стрима из Iterable
        StreamSupport.stream(mc.world.getEntities().spliterator(), false)
                .filter(e -> e instanceof ItemEntity)
                .map(e -> (ItemEntity) e)
                .filter(item -> mc.player.distanceTo(item) <= range.value)
                .min(Comparator.comparing(item -> mc.player.distanceTo(item)))
                .ifPresent(this::teleportToItem);
    }

    private void teleportToItem(ItemEntity item) {
        double originalX = mc.player.getX();
        double originalY = mc.player.getY();
        double originalZ = mc.player.getZ();

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(item.getX(), item.getY(), item.getZ(), true));
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionOnly(originalX, originalY, originalZ, mc.player.isOnGround()));
        
        mc.player.setPos(originalX, originalY, originalZ);
    }
}
