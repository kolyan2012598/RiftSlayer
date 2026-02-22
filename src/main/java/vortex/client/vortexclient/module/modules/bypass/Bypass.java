package vortex.client.vortexclient.module.modules.bypass;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.settings.ModeSetting;

public class Bypass extends Module {
    public ModeSetting mode = new ModeSetting("AntiCheat", this, "Vanilla", "Vanilla", "Matrix", "Grim", "Vulcan", "Intave", "AAC5");

    public Bypass() {
        super("Bypass", Category.BYPASS);
        settings.add(mode);
    }

    public static String getBypassMode() {
        Bypass bypassModule = ModuleManager.INSTANCE.getModule(Bypass.class);
        if (bypassModule != null && bypassModule.isEnabled()) {
            return bypassModule.mode.getMode();
        }
        return "Vanilla";
    }
}
