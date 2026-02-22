package vortex.client.vortexclient.module.modules.visuals;

import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import java.util.ArrayList;
import java.util.List;

public class Breadcrumbs extends Module {
    public List<Vec3d> path = new ArrayList<>();

    public Breadcrumbs() {
        super("Breadcrumbs", Category.VISUALS);
    }

    @Override
    public void onEnable() {
        path.clear();
    }

    @Override
    public void onTick() {
        if (mc.player != null) {
            path.add(mc.player.getPos());
        }
    }
}
