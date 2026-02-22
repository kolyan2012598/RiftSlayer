package vortex.client.vortexclient.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    // Этот файл больше не отвечает за чат.
    // Вся логика перенесена в ChatScreenMixin для надежности.
}
