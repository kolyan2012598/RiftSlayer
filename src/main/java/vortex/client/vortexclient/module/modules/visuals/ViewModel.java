package vortex.client.vortexclient.module.modules.visuals;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class ViewModel extends Module {
    public NumberSetting translateX = new NumberSetting("TranslateX", this, 0, -2, 2);
    public NumberSetting translateY = new NumberSetting("TranslateY", this, 0, -2, 2);
    public NumberSetting translateZ = new NumberSetting("TranslateZ", this, 0, -2, 2);
    public NumberSetting scale = new NumberSetting("Scale", this, 1, 0, 2);

    public ViewModel() {
        super("ViewModel", Category.VISUALS);
        settings.add(translateX);
        settings.add(translateY);
        settings.add(translateZ);
        settings.add(scale);
    }
    
    // Вся логика будет в миксине
}
