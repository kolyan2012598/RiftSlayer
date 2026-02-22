package vortex.client.vortexclient.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;

import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {
    private final List<Panel> panels = new ArrayList<>();

    public ClickGui() {
        super(Text.of("RiftSlayer GUI"));
        int panelX = 10;
        for (Module.Category category : Module.Category.values()) {
            panels.add(new Panel(category, panelX, 10, 100, 15));
            panelX += 110;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Эффект размытия фона (Frosted Glass)
        if (this.client != null && this.client.world != null) {
            this.fillGradient(matrices, 0, 0, this.width, this.height, 0x40000000, 0x50000000);
        } else {
            this.renderBackground(matrices);
        }
        
        for (Panel panel : panels) {
            panel.render(matrices, mouseX, mouseY, delta);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Panel panel : panels) {
            panel.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Panel panel : panels) {
            panel.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (Panel panel : panels) {
            panel.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
