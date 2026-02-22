package vortex.client.vortexclient.ui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import vortex.client.vortexclient.auth.WhitelistManager;

public class AdminPanelScreen extends Screen {
    public AdminPanelScreen() {
        super(Text.of("Admin Panel"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, "Whitelisted Users", this.width / 2, 20, -1);
        
        int y = 40;
        for (String user : WhitelistManager.getWhitelistedUsers()) {
            drawCenteredText(matrices, this.textRenderer, user, this.width / 2, y, -1);
            y += 12;
        }
        
        super.render(matrices, mouseX, mouseY, delta);
    }
}
