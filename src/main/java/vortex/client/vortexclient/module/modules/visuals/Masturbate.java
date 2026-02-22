package vortex.client.vortexclient.module.modules.visuals;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class Masturbate extends Module {

    public NumberSetting speed = new NumberSetting("Speed", this, 1.0, 0.1, 5.0);
    public NumberSetting intensity = new NumberSetting("Intensity", this, 0.3, 0.05, 1.5);

    private float animationTime;

    public float swing; // готовое значение для рендера

    public Masturbate() {
        super("Masturbate", Category.VISUALS);
        settings.add(speed);
        settings.add(intensity);
    }

    @Override
    public void onEnable() {
        animationTime = 0f;
        swing = 0f;
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;

        // увеличиваем время анимации
        animationTime += 0.05f * speed.value;

        // плавная синусная анимация
        swing = (float) (Math.sin(animationTime) * intensity.value);
    }

    @Override
    public void onDisable() {
        swing = 0f;
    }
}
