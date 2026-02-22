package vortex.client.vortexclient.module.modules.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import vortex.client.vortexclient.module.Module;

import java.awt.Color;

public class Hat extends Module {
    public Hat() {
        super("Hat", Category.RENDER);
    }

    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0, 0.5, 0);
        matrices.multiply(net.minecraft.util.math.Vec3f.POSITIVE_Y.getDegreesQuaternion(System.currentTimeMillis() % 4000L / 4000.0F * 360));

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        RenderSystem.disableCull();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // ПРАВИЛЬНЫЙ СПОСОБ ДЛЯ 1.16.5: Используем числовой код 7 (GL_QUADS)
        buffer.begin(7, VertexFormats.POSITION_COLOR);
        float radius = 0.5f;
        float innerRadius = 0.45f;

        for (int i = 0; i < 360; i++) {
            float hue = (float)i / 360f;
            Color color = Color.getHSBColor(hue, 0.8f, 1.0f);

            float angle1 = (float) (i * Math.PI / 180);
            float sin1 = (float) Math.sin(angle1);
            float cos1 = (float) Math.cos(angle1);

            float angle2 = (float) ((i + 1) * Math.PI / 180);
            float sin2 = (float) Math.sin(angle2);
            float cos2 = (float) Math.cos(angle2);

            buffer.vertex(matrices.peek().getModel(), sin1 * radius, 0, cos1 * radius).color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.8f).next();
            buffer.vertex(matrices.peek().getModel(), sin1 * innerRadius, 0, cos1 * innerRadius).color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.8f).next();
            buffer.vertex(matrices.peek().getModel(), sin2 * innerRadius, 0, cos2 * innerRadius).color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.8f).next();
            buffer.vertex(matrices.peek().getModel(), sin2 * radius, 0, cos2 * radius).color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.8f).next();
        }

        tessellator.draw();

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        
        matrices.pop();
    }
}
