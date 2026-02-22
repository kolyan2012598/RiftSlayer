package vortex.client.vortexclient.mixin;

import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.movement.ElytraFly;

@Mixin(ElytraEntityModel.class)
public class ElytraInvertedMixin {

    @Inject(method = "setAngles", at = @At("TAIL"))
    public void setAngles(LivingEntity entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        ElytraFly fly = ModuleManager.INSTANCE.getModule(ElytraFly.class);
        if (fly != null && fly.isEnabled() && fly.isDeceptionMode()) {
            // Ваша логика для инвертирования модели
        }
    }
}
