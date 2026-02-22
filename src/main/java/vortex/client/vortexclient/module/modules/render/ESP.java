package vortex.client.vortexclient.module.modules.render;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.BooleanSetting;
import vortex.client.vortexclient.module.settings.ColorSetting;

public class ESP extends Module {
    public BooleanSetting players = new BooleanSetting("Players", this, true);
    public ColorSetting color = new ColorSetting("Color", this, 1.0f, 1.0f, 1.0f);

    public ESP() {
        super("ESP", Category.RENDER);
        settings.add(players);
        settings.add(color);
    }
}
