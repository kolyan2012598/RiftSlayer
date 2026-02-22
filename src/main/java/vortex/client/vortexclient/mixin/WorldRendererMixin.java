package vortex.client.vortexclient.mixin;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.render.ESP;

import java.awt.Color;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci) {
        ESP esp = ModuleManager.INSTANCE.getModule(ESP.class);
        if (esp == null || !esp.isEnabled()) return;

        VertexConsumerProvider.Immediate vertexConsumers = esp.mc.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer lineBuffer = vertexConsumers.getBuffer(RenderLayer.getLines());

        if (esp.players.enabled) {
            for (Entity entity : esp.mc.world.getEntities()) {
                if (entity instanceof PlayerEntity && entity != esp.mc.player) {
                    
                    Vec3d camPos = camera.getPos();
                    double x = MathHelper.lerp(tickDelta, entity.prevX, entity.getX()) - camPos.x;
                    double y = MathHelper.lerp(tickDelta, entity.prevY, entity.getY()) - camPos.y;
                    double z = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ()) - camPos.z;
                    
                    Box box = entity.getBoundingBox().offset(-entity.getX(), -entity.getY(), -entity.getZ()).offset(x, y, z);
                    
                    Color color = new Color(esp.color.getColor());
                    
                    // ПРАВИЛЬНЫЙ СПОСОБ: Передаем VertexConsumer в метод
                    WorldRenderer.drawBox(matrices, lineBuffer, box, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.5f);
                }
            }
        }
        vertexConsumers.draw();
    }
}
