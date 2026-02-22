package vortex.client.vortexclient.module.modules.player;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class Timer extends Module {
    public NumberSetting speed = new NumberSetting("Speed", this, 1.0, 0.1, 10.0);

    public Timer() {
        super("Timer", Category.PLAYER);
        settings.add(speed);
    }
    
    // Вся логика будет в миксине
}
