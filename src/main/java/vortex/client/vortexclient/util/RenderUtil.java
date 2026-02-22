package vortex.client.vortexclient.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

public class RenderUtil {

    public static void drawRoundedRect(MatrixStack matrices, double x, double y, double width, double height, double radius, Color color) {
        Matrix4f matrix = matrices.peek().getModel();
        float r = (float) color.getRed() / 255.0F;
        float g = (float) color.getGreen() / 255.0F;
        float b = (float) color.getBlue() / 255.0F;
        float a = (float) color.getAlpha() / 255.0F;

        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        
        bufferBuilder.vertex(matrix, (float)(x + radius), (float)(y + radius), 0).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)(x + width - radius), (float)(y + radius), 0).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)(x + width - radius), (float)(y + height - radius), 0).color(r, g, b, a).next();
        bufferBuilder.vertex(matrix, (float)(x + radius), (float)(y + height - radius), 0).color(r, g, b, a).next();
        
        tessellator.draw();

        drawArc(matrix, (float)(x + radius), (float)(y + radius), (float)radius, 180, 270, color);
        drawArc(matrix, (float)(x + width - radius), (float)(y + radius), (float)radius, 270, 360, color);
        drawArc(matrix, (float)(x + width - radius), (float)(y + height - radius), (float)radius, 0, 90, color);
        drawArc(matrix, (float)(x + radius), (float)(y + height - radius), (float)radius, 90, 180, color);

        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private static void drawArc(Matrix4f matrix, float x, float y, float radius, int startAngle, int endAngle, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x, y, 0).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f).next();

        for (int i = startAngle; i <= endAngle; i++) {
            float angle = (float) (i * Math.PI / 180);
            bufferBuilder.vertex(matrix, (float) (x + Math.sin(angle) * radius), (float) (y + Math.cos(angle) * radius), 0)
                .color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f).next();
        }
        tessellator.draw();
    }
}
