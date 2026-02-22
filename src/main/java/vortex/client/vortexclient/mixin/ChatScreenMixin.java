package vortex.client.vortexclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vortex.client.vortexclient.command.CommandManager;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Shadow protected TextFieldWidget chatField;

    // ПРАВИЛЬНЫЙ, НАДЕЖНЫЙ И ГАРАНТИРОВАННЫЙ СПОСОБ: Перехватываем нажатие клавиши Enter
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        // Проверяем, что нажат Enter
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            String message = this.chatField.getText().trim();
            
            if (message.startsWith(CommandManager.PREFIX)) {
                // 1. Обрабатываем команду
                CommandManager.INSTANCE.handleMessage(message);
                
                // 2. Добавляем ее в историю чата
                MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(message);
                
                // 3. Закрываем экран чата
                MinecraftClient.getInstance().openScreen(null);
                
                // 4. Отменяем оригинальный метод, чтобы команда не отправилась на сервер
                cir.setReturnValue(true);
            }
            // Если это не команда, мы ничего не делаем, и оригинальный метод отправит сообщение как обычно
        }
    }
}
