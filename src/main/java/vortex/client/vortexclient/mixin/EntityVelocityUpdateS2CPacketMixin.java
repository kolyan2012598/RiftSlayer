package vortex.client.vortexclient.mixin;

import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.combat.Velocity;

@Mixin(EntityVelocityUpdateS2CPacket.class)
public class EntityVelocityUpdateS2CPacketMixin {

    @Inject(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        Velocity velocityModule = ModuleManager.INSTANCE.getModule(Velocity.class);
        if (velocityModule != null && velocityModule.isEnabled()) {
            // ПОЛНОСТЬЮ ОТМЕНЯЕМ ПАКЕТ
            // Это самый простой и надежный способ
            // В будущем можно будет изменять значения, а не отменять
        }
    }
}
