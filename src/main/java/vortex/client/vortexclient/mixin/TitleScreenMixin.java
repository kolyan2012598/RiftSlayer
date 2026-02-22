package vortex.client.vortexclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.ui.ThemeButton;
import vortex.client.vortexclient.ui.alt.AltManagerScreen;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Unique
    private static final Identifier CUSTOM_BACKGROUND = new Identifier("vortex", "textures/gui/menu.png");

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.buttons.clear();
        this.children.clear();

        int buttonWidth = 98;
        int spacing = 5;
        // ВОЗВРАЩАЕМ РАСЧЕТ ДЛЯ 4 КНОПОК
        int totalWidth = (buttonWidth * 4) + (spacing * 3);
        int x = (this.width - totalWidth) / 2;
        int y = 20;

        this.addButton(new ThemeButton(x, y, buttonWidth, 20, Text.of("Одиночная"), (button) -> this.client.openScreen(new net.minecraft.client.gui.screen.world.SelectWorldScreen(this))));
        this.addButton(new ThemeButton(x + buttonWidth + spacing, y, buttonWidth, 20, Text.of("Сетевая"), (button) -> this.client.openScreen(new net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen(this))));
        this.addButton(new ThemeButton(x + (buttonWidth + spacing) * 2, y, buttonWidth, 20, Text.of("Аккаунты"), (button) -> this.client.openScreen(new AltManagerScreen(this))));
        this.addButton(new ThemeButton(x + (buttonWidth + spacing) * 3, y, buttonWidth, 20, Text.of("Настройки"), (button) -> this.client.openScreen(new net.minecraft.client.gui.screen.option.OptionsScreen(this, this.client.options))));
        
        // ВОЗВРАЩАЕМ КНОПКУ "ТЕЛЕГРАМ" В УГОЛ
        this.addButton(new ThemeButton(this.width - 102, 5, 98, 20, Text.of("Телеграм"), (button) -> this.client.openScreen(new ConfirmScreen((confirmed) -> {
            if (confirmed) Util.getOperatingSystem().open("https://t.me/vortexclientbedwars");
            this.client.openScreen(this);
        }, new TranslatableText("chat.link.confirm"), Text.of("https://t.me/vortexclientbedwars")))));
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        ci.cancel(); 

        this.client.getTextureManager().bindTexture(CUSTOM_BACKGROUND);
        drawTexture(matrices, 0, 0, this.width, this.height, 0, 0, 1, 1);

        for (ClickableWidget button : this.buttons) {
            button.render(matrices, mouseX, mouseY, delta);
        }
    }
}
