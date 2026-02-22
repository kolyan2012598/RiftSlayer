package vortex.client.vortexclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Hand;

public class EntityUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean interactWithShopVillager() {
        if (mc.world == null || mc.player == null) return false;

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof VillagerEntity && entity.getDisplayName().getString().contains("Магазин")) {
               // ПРАВИЛЬНЫЙ СПОСОБ: Используем конструктор пакета, который существует в 1.16.5
                // Этот пакет неявно использует Hand.MAIN_HAND для INTERACT
                mc.player.networkHandler.sendPacket(new PlayerInteractEntityC2SPacket(entity, mc.player.isSneaking()));
                return true;
            }
        }
        return false;
    }
}
