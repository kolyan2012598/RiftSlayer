package vortex.client.vortexclient.module.modules.render;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class FOV extends Module {
    public NumberSetting fov = new NumberSetting("FOV", this, 100, 30, 150);
    private double originalFov;

    public FOV() {
        super("FOV", Category.RENDER);
        settings.add(fov);
    }

    @Override
    public void onEnable() {
        if (mc.options != null) {
            // Правильный способ для 1.16.5
            originalFov = mc.options.fov;
        }
    }

    @Override
    public void onDisable() {
        if (mc.options != null) {
            mc.options.fov = originalFov;
        }
    }

    @Override
    public void onTick() {
        if (mc.options != null) {
            mc.options.fov = fov.value;
        }
    }
}
