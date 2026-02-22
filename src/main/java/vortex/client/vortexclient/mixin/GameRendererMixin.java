package vortex.client.vortexclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.render.NoHurtCam;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void onBobView(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (ModuleManager.INSTANCE.getModule(NoHurtCam.class).isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderWorld", at = @At("RETURN"))
    private void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        client.getProfiler().push("RiftSlayer_WorldRender");
        RenderSystem.pushMatrix();
        
        for (Module module : ModuleManager.INSTANCE.getEnabledModules()) {
            module.onWorldRender(matrices);
        }
        
        RenderSystem.popMatrix();
        client.getProfiler().pop();
    }
}
