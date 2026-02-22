package vortex.client.vortexclient.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.ui.hud.Hud;
import vortex.client.vortexclient.ui.notification.NotificationManager;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        // ПРАВИЛЬНЫЙ СПОСОБ: Вызываем единственный, рабочий HUD
        Hud.render(matrices, tickDelta);
        NotificationManager.INSTANCE.render(matrices);
    }
}
