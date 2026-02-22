package vortex.client.vortexclient.module.settings;

import vortex.client.vortexclient.module.Module;

public class StringSetting extends Setting {
    public String value;

    // ПРАВИЛЬНЫЙ СПОСОБ: Используем Module вместо Object
    public StringSetting(String name, Module parent, String defaultValue) {
        super(name, parent);
        this.value = defaultValue;
    }
}
