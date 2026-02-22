package vortex.client.vortexclient.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.combat.Velocity;
import vortex.client.vortexclient.module.modules.movement.NoClip;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Vec3d getVelocity();
    @Shadow public abstract void setVelocity(Vec3d velocity);

    @Inject(method = "handleAttack", at = @At("HEAD"), cancellable = true)
    private void onHandleAttack(CallbackInfoReturnable<Boolean> cir) {
        if (!((Object)this instanceof net.minecraft.client.network.ClientPlayerEntity)) return;
        Velocity velocityModule = (Velocity) ModuleManager.INSTANCE.getModules().stream().filter(m -> m instanceof Velocity && m.isEnabled()).findFirst().orElse(null);
        if (velocityModule != null) {
            double h = velocityModule.horizontal.value / 100.0;
            double v = velocityModule.vertical.value / 100.0;
            setVelocity(getVelocity().multiply(h, v, h));
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
    private void onIsPushable(CallbackInfoReturnable<Boolean> cir) {
        if (!((Object)this instanceof net.minecraft.client.network.ClientPlayerEntity)) return;
        NoClip noClipModule = (NoClip) ModuleManager.INSTANCE.getModules().stream().filter(m -> m instanceof NoClip && m.isEnabled()).findFirst().orElse(null);
        if (noClipModule != null) {
            cir.setReturnValue(false);
        }
    }
}
