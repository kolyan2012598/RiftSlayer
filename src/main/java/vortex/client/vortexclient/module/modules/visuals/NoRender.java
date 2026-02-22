package vortex.client.vortexclient.module.modules.visuals;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.BooleanSetting;

public class NoRender extends Module {
    public BooleanSetting noFire = new BooleanSetting("NoFire", this, true);
    public BooleanSetting noPumpkin = new BooleanSetting("NoPumpkin", this, true);
    public BooleanSetting noNausea = new BooleanSetting("NoNausea", this, true);
    public BooleanSetting noBlindness = new BooleanSetting("NoBlindness", this, true);

    public NoRender() {
        super("NoRender", Category.VISUALS);
        settings.add(noFire);
        settings.add(noPumpkin);
        settings.add(noNausea);
        settings.add(noBlindness);
    }
    
    // Вся логика будет в миксинах
}
