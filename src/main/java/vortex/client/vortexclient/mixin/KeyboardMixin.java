package vortex.client.vortexclient.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.binds.BindManager;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;

import java.util.HashMap;
import java.util.Map;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    private static final Map<Integer, String> KEY_NAMES = new HashMap<>();
    static {
        KEY_NAMES.put(GLFW.GLFW_KEY_RIGHT_SHIFT, "right_shift");
        KEY_NAMES.put(GLFW.GLFW_KEY_LEFT_SHIFT, "left_shift");
        KEY_NAMES.put(GLFW.GLFW_KEY_RIGHT_CONTROL, "right_control");
        KEY_NAMES.put(GLFW.GLFW_KEY_LEFT_CONTROL, "left_control");
        KEY_NAMES.put(GLFW.GLFW_KEY_RIGHT_ALT, "right_alt");
        KEY_NAMES.put(GLFW.GLFW_KEY_LEFT_ALT, "left_alt");
        // Добавьте другие специальные клавиши по мере необходимости
    }

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action == GLFW.GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null) {
            String keyName;
            if (KEY_NAMES.containsKey(key)) {
                keyName = KEY_NAMES.get(key);
            } else {
                keyName = GLFW.glfwGetKeyName(key, scancode);
            }

            if (keyName == null) return;

            for (Map.Entry<String, String> entry : BindManager.INSTANCE.getBinds().entrySet()) {
                String moduleName = entry.getKey();
                String boundKey = entry.getValue();
                
                if (boundKey.equalsIgnoreCase(keyName)) {
                    ModuleManager.INSTANCE.getModules().stream()
                            .filter(m -> m.getName().equalsIgnoreCase(moduleName))
                            .findFirst()
                            .ifPresent(Module::toggle);
                }
            }
        }
    }
}
