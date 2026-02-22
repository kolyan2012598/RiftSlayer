package vortex.client.vortexclient.module.modules.combat;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class Velocity extends Module {
    public NumberSetting horizontal = new NumberSetting("Horizontal", this, 0, 0, 100);
    public NumberSetting vertical = new NumberSetting("Vertical", this, 0, 0, 100);

    public Velocity() {
        super("Velocity", Category.COMBAT);
        settings.add(horizontal);
        settings.add(vertical);
    }
}
