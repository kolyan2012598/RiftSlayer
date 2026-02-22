package vortex.client.vortexclient.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.visuals.Masturbate;
import vortex.client.vortexclient.module.modules.visuals.ViewModel;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void onRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        
        Masturbate masturbateModule = (Masturbate) ModuleManager.INSTANCE.getModules().stream()
                .filter(m -> m instanceof Masturbate && m.isEnabled())
                .findFirst()
                .orElse(null);

        if (masturbateModule != null) {
            matrices.translate(0.0, -0.5, -0.8);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(70));
            
            float swing = masturbateModule.swing;
            matrices.translate(0, swing * masturbateModule.intensity.value, 0);
            
        } else {
            ViewModel viewModel = (ViewModel) ModuleManager.INSTANCE.getModules().stream()
                    .filter(m -> m instanceof ViewModel && m.isEnabled())
                    .findFirst()
                    .orElse(null);
            
            if (viewModel != null) {
                matrices.translate(viewModel.translateX.value, viewModel.translateY.value, viewModel.translateZ.value);
                float s = (float) viewModel.scale.value;
                matrices.scale(s, s, s);
            }
        }
    }
}
