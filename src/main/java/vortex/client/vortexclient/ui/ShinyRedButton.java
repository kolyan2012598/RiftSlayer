package vortex.client.vortexclient.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.Color;

public class ShinyRedButton extends ButtonWidget {

    public ShinyRedButton(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        TextRenderer textRenderer = mc.textRenderer;

        // ПРАВИЛЬНЫЙ СПОСОБ: Вызываем методы напрямую, так как ButtonWidget наследуется от DrawableHelper
        
        // Пульсирующий красный градиент
        float sine = 0.5f * (float)Math.sin(System.currentTimeMillis() / 400.0) + 0.5f;
        int red1 = 180 + (int)(75 * sine);
        int red2 = 150 + (int)(75 * sine);
        
        fillGradient(matrices, this.x, this.y, this.x + this.width, this.y + this.height, 
                     new Color(red1, 20, 20, 200).getRGB(), new Color(red2, 10, 10, 200).getRGB());

        // Блеск при наведении
        if (this.isHovered()) {
            fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, new Color(255, 100, 100, 40).getRGB());
        }

        // Текст
        drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF);
    }
}
