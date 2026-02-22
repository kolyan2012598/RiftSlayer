package vortex.client.vortexclient.module.settings;

import vortex.client.vortexclient.module.Module;

public class BooleanSetting extends Setting {
    public boolean enabled;

    public BooleanSetting(String name, Module parent, boolean enabled) {
        super(name, parent);
        this.enabled = enabled;
    }
}
