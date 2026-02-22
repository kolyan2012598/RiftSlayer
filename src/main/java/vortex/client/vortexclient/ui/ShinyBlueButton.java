package vortex.client.vortexclient.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ShinyBlueButton extends ButtonWidget {

    public ShinyBlueButton(int x, int y, int w, int h, Text text, PressAction action) {
        super(x, y, w, h, text, action);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int top = isHovered() ? 0xFF4FA3FF : 0xFF2B6EDC;
        int bottom = isHovered() ? 0xFF1C4FA3 : 0xFF163A7A;

        // фон
        fillGradient(
                matrices,
                x, y,
                x + width, y + height,
                top, bottom
        );

        // обводка
        int outline = 0xFF7EC8FF;
        fill(matrices, x, y, x + width, y + 1, outline);
        fill(matrices, x, y + height - 1, x + width, y + height, outline);
        fill(matrices, x, y, x + 1, y + height, outline);
        fill(matrices, x + width - 1, y, x + width, y + height, outline);

        // текст
        drawCenteredText(
                matrices,
                MinecraftClient.getInstance().textRenderer,
                getMessage(),
                x + width / 2,
                y + (height - 8) / 2,
                0xEAF6FF
        );
    }
}
