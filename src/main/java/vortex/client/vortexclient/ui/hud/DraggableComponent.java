package vortex.client.vortexclient.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import vortex.client.vortexclient.config.HudConfig;
import vortex.client.vortexclient.config.Position;

public class DraggableComponent extends DrawableHelper {
    private final String id;
    private Position position;
    private int width, height;
    private boolean editing = false;
    private boolean dragging = false;
    private int dragX, dragY;

    public DraggableComponent(String id) {
        this.id = id;
        this.position = HudConfig.INSTANCE.componentPositions.getOrDefault(id, new Position(0, 0));
    }

    public Position getPosition() {
        return position;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public void render(MatrixStack matrices) {
        if (editing) {
            fill(matrices, position.x, position.y, position.x + width, position.y + height, 0x80FFFFFF);
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (editing && isMouseOver(mouseX, mouseY) && button == 0) {
            dragging = true;
            dragX = (int) (mouseX - position.x);
            dragY = (int) (mouseY - position.y);
        }
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging) {
            dragging = false;
            return true; // Возвращаем true, чтобы сохранить конфиг
        }
        return false;
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            position.x = (int) (mouseX - dragX);
            position.y = (int) (mouseY - dragY);
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= position.x && mouseX <= position.x + width && mouseY >= position.y && mouseY <= position.y + height;
    }
}
