package vortex.client.vortexclient.module.settings;

import vortex.client.vortexclient.module.Module;

public class Setting {
    public String name;
    public Module parent;

    public Setting(String name, Module parent) {
        this.name = name;
        this.parent = parent;
    }
}
