package vortex.client.vortexclient.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hud {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void render(MatrixStack matrices, float tickDelta) {
        if (mc.options.debugEnabled) return;

        renderWatermark(matrices);
        renderArrayList(matrices);
        renderPlayerArmor(matrices);
        renderTargetInfo(matrices);
    }

    // 🔥 WATERMARK
    private static void renderWatermark(MatrixStack matrices) {
        float hue = (System.currentTimeMillis() % 3000L) / 3000f;
        int color = Color.HSBtoRGB(hue, 1f, 1f);

        mc.textRenderer.drawWithShadow(
                matrices,
                "RiftSlayer",
                4,
                4,
                color
        );
    }

    // 💎 ARRAYLIST
    private static void renderArrayList(MatrixStack matrices) {

        int screenWidth = mc.getWindow().getScaledWidth();
        int y = 6;

        List<Module> enabledModules = ModuleManager.INSTANCE.getModules().stream()
                .filter(Module::isEnabled)
                .sorted(Comparator.comparingInt(
                        (Module m) -> mc.textRenderer.getWidth(m.getName())
                ).reversed())
                .collect(Collectors.toList());

        int index = 0;

        for (Module module : enabledModules) {
            String name = module.getName();
            int width = mc.textRenderer.getWidth(name);

            float hue = ((System.currentTimeMillis() + (index * 150)) % 3000L) / 3000f;
            int color = Color.HSBtoRGB(hue, 0.9f, 1f);

            int x = screenWidth - width - 6;

            DrawableHelper.fill(
                    matrices,
                    x - 4,
                    y - 2,
                    screenWidth,
                    y + mc.textRenderer.fontHeight,
                    new Color(0, 0, 0, 120).getRGB()
            );

            mc.textRenderer.drawWithShadow(
                    matrices,
                    name,
                    x,
                    y,
                    color
            );

            y += mc.textRenderer.fontHeight + 2;
            index++;
        }
    }

    // 🛡️ Отображение брони игрока
    private static void renderPlayerArmor(MatrixStack matrices) {
        PlayerEntity player = mc.player;
        if (player == null) return;

        int x = 4;
        int y = mc.getWindow().getScaledHeight() - 20;

        int slotIndex = 0;
        // В 1.16.5 броня хранится в inventory.armor
        for (ItemStack stack : player.inventory.armor) {
            if (!stack.isEmpty()) {
                mc.getItemRenderer().renderInGui(stack, x + slotIndex * 20, y);
                mc.getItemRenderer().renderGuiItemOverlay(mc.textRenderer, stack, x + slotIndex * 20, y);
            }
            slotIndex++;
        }
    }


    // ❤️ Информация о ближайшей цели
    private static void renderTargetInfo(MatrixStack matrices) {
        PlayerEntity target = mc.world.getPlayers().stream()
                .filter(p -> p != mc.player && mc.player.squaredDistanceTo(p) < 10000) // дистанция до цели
                .findFirst()
                .orElse(null);

        if (target == null) return;

        String info = target.getName().getString() + " " + (int) target.getHealth() + "❤";

        int x = mc.getWindow().getScaledWidth() / 2 - mc.textRenderer.getWidth(info) / 2;
        int y = 20;

        mc.textRenderer.drawWithShadow(matrices, info, x, y, Color.RED.getRGB());
    }
}
