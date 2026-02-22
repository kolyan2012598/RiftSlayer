package vortex.client.vortexclient.module.modules.render;

import vortex.client.vortexclient.module.Module;

public class FullBright extends Module {
    private double oldGamma;

    public FullBright() {
        super("FullBright", Category.RENDER);
    }

    @Override
    public void onEnable() {
        if (mc.options != null) {
            oldGamma = mc.options.gamma;
            mc.options.gamma = 100.0;
        }
    }

    @Override
    public void onDisable() {
        if (mc.options != null) {
            mc.options.gamma = oldGamma;
        }
    }
}
