package vortex.client.vortexclient.ui.alt;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import vortex.client.vortexclient.ui.ShinyBlueButton;

public class AddAltScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget nameField;

    public AddAltScreen(Screen parent) {
        super(Text.of("Добавить аккаунт"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.nameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 - 10, 200, 20, Text.of("Никнейм"));
        this.children.add(this.nameField);

        this.addButton(new ShinyBlueButton(this.width / 2 - 100, this.height / 2 + 20, 200, 20, Text.of("Добавить"), button -> {
            if (!nameField.getText().isEmpty()) {
                AltManager.INSTANCE.addAlt(new Alt(nameField.getText()));
                this.client.openScreen(parent);
            }
        }));
        this.addButton(new ShinyBlueButton(this.width / 2 - 100, this.height / 2 + 45, 200, 20, Text.of("Отмена"), button -> {
            this.client.openScreen(parent);
        }));
        
        this.setInitialFocus(this.nameField);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        this.nameField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
