package vortex.client.vortexclient.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;

// Этот миксин больше не нужен, так как проверка вайтлиста при запуске удалена.
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    // Пусто.
}
