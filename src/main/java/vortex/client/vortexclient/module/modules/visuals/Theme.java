package vortex.client.vortexclient.module.modules.visuals;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.settings.ColorSetting;

import java.awt.Color;

public class Theme extends Module {
    public ColorSetting color = new ColorSetting("Color", this, 0.0f, 1f, 1f);

    public Theme() {
        super("Theme", Category.VISUALS);
        settings.add(color);
    }

    public static int getPrimaryColor() {
        Theme themeModule = ModuleManager.INSTANCE.getModule(Theme.class);
        if (themeModule != null) {
            return themeModule.color.getColor();
        }
        return Color.RED.getRGB();
    }
    
    // ПРАВИЛЬНЫЙ СПОСОБ: Анимируем оттенок (hue) для создания радуги
    public static int getAnimatedColor() {
        float hue = (System.currentTimeMillis() % 3000L) / 3000.0F;
        return Color.getHSBColor(hue, 0.8f, 1.0f).getRGB();
    }
}
