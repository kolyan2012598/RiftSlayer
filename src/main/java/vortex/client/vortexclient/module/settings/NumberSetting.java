package vortex.client.vortexclient.module.settings;

import vortex.client.vortexclient.module.Module;

public class NumberSetting extends Setting {
    public double value, min, max;

    public NumberSetting(String name, Module parent, double value, double min, double max) {
        super(name, parent);
        this.value = value;
        this.min = min;
        this.max = max;
    }
}
