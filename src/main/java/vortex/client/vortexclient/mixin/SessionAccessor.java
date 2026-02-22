package vortex.client.vortexclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface SessionAccessor {
    // Этот код говорит миксину: "Сделай поле 'session' изменяемым и создай для него сеттер"
    @Accessor("session")
    @Mutable
    void setSession(Session session);
}
