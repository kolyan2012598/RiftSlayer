package vortex.client.vortexclient.module.modules.visuals;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.util.RenderUtil;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;

public class HUD extends Module {
    public HUD() {
        super("HUD", Category.VISUALS);
    }

    @Override
    public void onRender2D(MatrixStack matrices) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.debugEnabled) return;

        int themeColor = Theme.getAnimatedColor();

        // Watermark
        mc.textRenderer.drawWithShadow(matrices, "RiftSlayer", 2, 2, themeColor);

        // Info
        if (mc.player != null) {
            String fpsText = mc.fpsDebugString.split(" ")[0];
            mc.textRenderer.drawWithShadow(matrices, "FPS: " + fpsText, 2, 12, -1);

            String coords = String.format("XYZ: %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
            mc.textRenderer.drawWithShadow(matrices, coords, 2, 22, -1);
        }

        // Module List (iOS Style)
        int y = 2;
        int screenWidth = mc.getWindow().getScaledWidth();
        List<Module> modules = ModuleManager.INSTANCE.getEnabledModules();
        modules.sort(Comparator.comparingInt((Module m) -> mc.textRenderer.getWidth(m.getName())).reversed());

        for (Module module : modules) {
            String name = module.getName();
            int textWidth = mc.textRenderer.getWidth(name);
            
            float x = screenWidth - textWidth - 5;
            
            // Рисуем красивый, скругленный фон
            RenderUtil.drawRoundedRect(matrices, x - 3, y - 2, textWidth + 6, mc.textRenderer.fontHeight + 2, 3, new Color(0, 0, 0, 120));
            
            // Рисуем текст
            mc.textRenderer.drawWithShadow(matrices, name, x, y, themeColor);
            
            y += mc.textRenderer.fontHeight + 4; // Увеличиваем отступ для красоты
        }
    }
}
