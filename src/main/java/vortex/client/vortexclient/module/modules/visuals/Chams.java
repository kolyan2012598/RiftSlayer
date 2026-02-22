package vortex.client.vortexclient.module.modules.visuals;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.ColorSetting;

public class Chams extends Module {
    public ColorSetting color = new ColorSetting("Color", this, 1.0f, 0.0f, 0.0f);

    public Chams() {
        super("Chams", Category.VISUALS);
        settings.add(color);
    }
}
