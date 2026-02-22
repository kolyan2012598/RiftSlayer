package vortex.client.vortexclient.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import vortex.client.vortexclient.module.modules.visuals.Theme;

import java.awt.Color;

public class ThemeButton extends ButtonWidget {

    public ThemeButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer textRenderer = mc.textRenderer;

        int color = Theme.getAnimatedColor();
        Color c = new Color(color);
        
        fillGradient(matrices, this.x, this.y, this.x + this.width, this.y + this.height, 
                     new Color(c.getRed(), c.getGreen(), c.getBlue(), 200).getRGB(), 
                     new Color(c.getRed(), c.getGreen(), c.getBlue(), 150).darker().getRGB());

        if (this.isHovered()) {
            fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, new Color(255, 255, 255, 40).getRGB());
        }

        drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF);
    }
}
