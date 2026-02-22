package vortex.client.vortexclient.module.modules.visuals;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.combat.KillAura;
import vortex.client.vortexclient.util.RenderUtil;

import java.awt.Color;

public class TargetHUD extends Module {
    public TargetHUD() {
        super("TargetHUD", Category.VISUALS);
    }

    @Override
    public void onRender2D(MatrixStack matrices) {
        KillAura killAura = ModuleManager.INSTANCE.getModule(KillAura.class);
        if (killAura == null || !killAura.isEnabled() || killAura.target == null) {
            return;
        }

        PlayerEntity target = killAura.target;
        
        int x = mc.getWindow().getScaledWidth() / 2 + 20;
        int y = mc.getWindow().getScaledHeight() / 2;
        int width = 140;
        int height = 60;

        RenderUtil.drawRoundedRect(matrices, x, y, width, height, 5, new Color(0, 0, 0, 120));
        mc.textRenderer.drawWithShadow(matrices, target.getName().getString(), x + 40, y + 5, -1);

        float health = target.getHealth();
        float maxHealth = target.getMaxHealth();
        float healthPercentage = health / maxHealth;
        
        int healthBarWidth = 80;
        int healthBarColor = Color.HSBtoRGB(healthPercentage / 3.0f, 1.0f, 1.0f);
        
        DrawableHelper.fill(matrices, x + 40, y + 18, x + 40 + healthBarWidth, y + 28, new Color(0, 0, 0, 150).getRGB());
        DrawableHelper.fill(matrices, x + 40, y + 18, x + 40 + (int)(healthBarWidth * healthPercentage), y + 28, healthBarColor);
        mc.textRenderer.drawWithShadow(matrices, String.format("%.1f HP", health), x + 42, y + 20, -1);

        drawEntity(x + 20, y + 55, 25, -target.yaw, -target.pitch, target);
    }

    private void drawEntity(int x, int y, int size, float yaw, float pitch, PlayerEntity entity) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(x, y, 1050.0);
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        matrixStack.scale(size, size, size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(pitch);
        quaternion.hamiltonProduct(quaternion2);
        matrixStack.multiply(quaternion);
        
        // ПРАВИЛЬНЫЙ СПОСОБ: Используем enableGuiDepthLighting
        DiffuseLighting.enableGuiDepthLighting();

        EntityRenderDispatcher entityRenderDispatcher = mc.getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        
        VertexConsumerProvider.Immediate immediate = mc.getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, yaw, 1.0F, matrixStack, immediate, 15728880);
        });
        
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        DiffuseLighting.enableGuiDepthLighting();
    }
}
