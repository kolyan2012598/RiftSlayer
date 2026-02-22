package vortex.client.vortexclient.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.player.AutoBuy;
import vortex.client.vortexclient.module.modules.visuals.Theme;
import vortex.client.vortexclient.module.settings.BooleanSetting;
import vortex.client.vortexclient.module.settings.ModeSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;
import vortex.client.vortexclient.module.settings.Setting;
import vortex.client.vortexclient.util.RenderUtil;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Panel {
    public Module.Category category;
    public int x, y, width, height;
    private boolean dragging, open = true;
    private int dragX, dragY;
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private NumberSetting draggingSlider = null;

    public Panel(Module.Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        int themeColor = Theme.getAnimatedColor();
        
        RenderUtil.drawRoundedRect(matrices, x, y, width, height, 5, new Color(themeColor));
        mc.textRenderer.drawWithShadow(matrices, category.name(), x + 5, y + 4, Color.WHITE.getRGB());

        if (open) {
            int yOffset = height + 2;
            List<Module> modules = ModuleManager.INSTANCE.getModulesInCategory(category);
            for (Module module : modules) {
                Color moduleColor = module.isEnabled() ? new Color(themeColor).darker() : new Color(40, 40, 40, 180);
                RenderUtil.drawRoundedRect(matrices, x, y + yOffset, width, height, 2, moduleColor);
                mc.textRenderer.drawWithShadow(matrices, module.getName(), x + 5, y + yOffset + 4, Color.WHITE.getRGB());
                yOffset += height + 2;

                if (module.settingsOpen) {
                    for (Setting setting : module.settings) {
                        int settingY = y + yOffset;
                        RenderUtil.drawRoundedRect(matrices, x, settingY, width, height, 2, new Color(25, 25, 25, 180));

                        if (setting instanceof BooleanSetting) {
                            BooleanSetting bs = (BooleanSetting) setting;
                            mc.textRenderer.drawWithShadow(matrices, bs.name, x + 10, settingY + 4, bs.enabled ? Color.GREEN.getRGB() : Color.RED.getRGB());
                        } else if (setting instanceof NumberSetting) {
                            NumberSetting ns = (NumberSetting) setting;
                            double sliderWidth = ((ns.value - ns.min) / (ns.max - ns.min)) * (width - 10);
                            RenderUtil.drawRoundedRect(matrices, x + 5, settingY, (int)sliderWidth, height, 2, new Color(themeColor));
                            mc.textRenderer.drawWithShadow(matrices, ns.name + ": " + ns.value, x + 10, settingY + 4, Color.WHITE.getRGB());
                        } else if (setting instanceof ModeSetting) {
                            ModeSetting ms = (ModeSetting) setting;
                            mc.textRenderer.drawWithShadow(matrices, ms.name + ": " + ms.getMode(), x + 10, settingY + 4, Color.WHITE.getRGB());
                        }
                        yOffset += height + 2;
                    }
                }
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY, x, y, width, height)) {
            if (button == 0) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else if (button == 1) {
                open = !open;
            }
            return;
        }

        if (open) {
            int yOffset = height + 2;
            for (Module module : ModuleManager.INSTANCE.getModulesInCategory(category)) {
                if (isMouseOver(mouseX, mouseY, x, y + yOffset, width, height)) {
                    if (button == 0) module.toggle();
                    if (button == 1) {
                        if (module instanceof AutoBuy) {
                            mc.openScreen(new AutoBuyScreen());
                        } else {
                            module.settingsOpen = !module.settingsOpen;
                        }
                    }
                    return;
                }
                yOffset += height + 2;
                if (module.settingsOpen) {
                    for (Setting setting : module.settings) {
                        if (isMouseOver(mouseX, mouseY, x, y + yOffset, width, height)) {
                            if (setting instanceof BooleanSetting) ((BooleanSetting) setting).enabled = !((BooleanSetting) setting).enabled;
                            if (setting instanceof NumberSetting) draggingSlider = (NumberSetting) setting;
                            if (setting instanceof ModeSetting) ((ModeSetting) setting).cycle();
                            return;
                        }
                        yOffset += height + 2;
                    }
                }
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
            draggingSlider = null;
        }
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && draggingSlider != null) {
            double diff = Math.min(width - 10, Math.max(0, mouseX - (x + 5)));
            double newValue = (diff / (width - 10)) * (draggingSlider.max - draggingSlider.min) + draggingSlider.min;
            draggingSlider.value = new BigDecimal(newValue).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
    }

    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
