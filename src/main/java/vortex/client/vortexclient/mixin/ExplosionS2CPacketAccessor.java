package vortex.client.vortexclient.mixin;

import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExplosionS2CPacket.class)
public interface ExplosionS2CPacketAccessor {
    @Mutable
    @Accessor("playerVelocityX")
    void setPlayerVelocityX(float x);

    @Mutable
    @Accessor("playerVelocityY")
    void setPlayerVelocityY(float y);

    @Mutable
    @Accessor("playerVelocityZ")
    void setPlayerVelocityZ(float z);
}
