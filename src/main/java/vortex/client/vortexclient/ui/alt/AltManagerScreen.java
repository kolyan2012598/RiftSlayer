package vortex.client.vortexclient.ui.alt;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import vortex.client.vortexclient.mixin.SessionAccessor;
import vortex.client.vortexclient.ui.ShinyBlueButton;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class AltManagerScreen extends Screen {
    private final Screen parent;
    private int selectedAlt = -1;
    private OtherClientPlayerEntity playerPreview;
    private long lastClickTime = 0;

    public AltManagerScreen(Screen parent) {
        super(Text.of("Alt Manager"));
        this.parent = parent;
        AltManager.INSTANCE.loadAlts();
    }

    @Override
    protected void init() {
        int buttonWidth = 98;
        int spacing = 5;
        int y = this.height - 40;

        this.addButton(new ShinyBlueButton(this.width / 2 - buttonWidth * 2 - spacing * 2, y, buttonWidth, 20, Text.of("Войти"), button -> login()));
        this.addButton(new ShinyBlueButton(this.width / 2 - buttonWidth - spacing, y, buttonWidth, 20, Text.of("Добавить"), button -> client.openScreen(new AddAltScreen(this))));
        this.addButton(new ShinyBlueButton(this.width / 2 + spacing, y, buttonWidth, 20, Text.of("Удалить"), button -> removeSelectedAlt()));
        this.addButton(new ShinyBlueButton(this.width / 2 + buttonWidth + spacing * 2, y, buttonWidth, 20, Text.of("Назад"), button -> client.openScreen(parent)));
    }

    private void login() {
        if (selectedAlt != -1 && selectedAlt < AltManager.INSTANCE.getAlts().size()) {
            String name = AltManager.INSTANCE.getAlts().get(selectedAlt).getName();
            UUID offlineUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
            Session newSession = new Session(name, offlineUUID.toString(), "0", "mojang");
            ((SessionAccessor) this.client).setSession(newSession);
        }
    }

    private void removeSelectedAlt() {
        if (selectedAlt != -1 && selectedAlt < AltManager.INSTANCE.getAlts().size()) {
            AltManager.INSTANCE.removeAlt(AltManager.INSTANCE.getAlts().get(selectedAlt));
            if (selectedAlt >= AltManager.INSTANCE.getAlts().size()) {
                selectedAlt = AltManager.INSTANCE.getAlts().size() - 1;
            }
            updatePlayerPreview();
        }
    }

    private void updatePlayerPreview() {
        if (this.client.world != null && selectedAlt != -1 && selectedAlt < AltManager.INSTANCE.getAlts().size()) {
            String name = AltManager.INSTANCE.getAlts().get(selectedAlt).getName();
            this.playerPreview = new OtherClientPlayerEntity(this.client.world, new GameProfile(UUID.randomUUID(), name));
        } else {
            this.playerPreview = null;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);

        int yOffset = 40;
        for (int i = 0; i < AltManager.INSTANCE.getAlts().size(); i++) {
            String altName = AltManager.INSTANCE.getAlts().get(i).getName();
            int color = (i == selectedAlt) ? 0xFF0090FF : 0xFFFFFFFF;
            drawCenteredText(matrices, this.textRenderer, altName, this.width / 2, yOffset, color);
            yOffset += 12;
        }

        if (playerPreview != null) {
            InventoryScreen.drawEntity(this.width - 50, this.height / 2 + 50, 50, -30, 0, playerPreview);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int yOffset = 40;
        for (int i = 0; i < AltManager.INSTANCE.getAlts().size(); i++) {
            int textWidth = this.textRenderer.getWidth(AltManager.INSTANCE.getAlts().get(i).getName());
            if (mouseY >= yOffset && mouseY < yOffset + 10 && mouseX > (this.width / 2 - textWidth / 2) && mouseX < (this.width / 2 + textWidth / 2)) {
                selectedAlt = i;
                updatePlayerPreview();
                if (button == 0 && System.currentTimeMillis() - lastClickTime < 250) {
                    login();
                }
                lastClickTime = System.currentTimeMillis();
                return true;
            }
            yOffset += 12;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
