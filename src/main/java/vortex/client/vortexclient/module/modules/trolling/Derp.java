package vortex.client.vortexclient.module.modules.trolling;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.ModeSetting;

public class Derp extends Module {
    public ModeSetting mode = new ModeSetting("Mode", this, "UpsideDown", "UpsideDown", "Sideways");

    public Derp() {
        super("Derp", Category.TROLLING);
        settings.add(mode);
    }
}
