package vortex.client.vortexclient.mixin;

import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.player.Timer;

@Mixin(RenderTickCounter.class)
public class RenderTickCounterMixin {
    @Shadow private float lastFrameDuration;

    @Inject(method = "beginRenderTick", at = @At("HEAD"), cancellable = true)
    private void onBeginRenderTick(long timeMillis, CallbackInfoReturnable<Integer> cir) {
        Timer timerModule = (Timer) ModuleManager.INSTANCE.getModules().stream()
                .filter(m -> m instanceof Timer && m.isEnabled())
                .findFirst()
                .orElse(null);
        
        if (timerModule != null) {
            this.lastFrameDuration *= timerModule.speed.value;
        }
    }
}
