package vortex.client.vortexclient.module.settings;

import vortex.client.vortexclient.module.Module;
import java.awt.Color;

public class ColorSetting extends Setting {
    public float hue, saturation, brightness, alpha;

    public ColorSetting(String name, Module parent, float hue, float saturation, float brightness, float alpha) {
        super(name, parent);
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.alpha = alpha;
    }
    
    public ColorSetting(String name, Module parent, float hue, float saturation, float brightness) {
        this(name, parent, hue, saturation, brightness, 1.0f);
    }

    public int getColor() {
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    public Color getColorObject() {
        return Color.getHSBColor(hue, saturation, brightness);
    }

    public int getRed() { return getColorObject().getRed(); }
    public int getGreen() { return getColorObject().getGreen(); }
    public int getBlue() { return getColorObject().getBlue(); }
    public float getAlpha() { return alpha; }
}
