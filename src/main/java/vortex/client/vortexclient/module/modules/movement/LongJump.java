package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class LongJump extends Module {
    public NumberSetting speed = new NumberSetting("Speed", this, 3.0, 0.1, 10.0);
    public NumberSetting height = new NumberSetting("Height", this, 0.8, 0.1, 2.0);

    private boolean jumped = false;

    public LongJump() {
        super("LongJump", Category.MOVEMENT);
        settings.add(speed);
        settings.add(height);
    }

    @Override
    public void onEnable() {
        jumped = false;
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;

        if (mc.player.isOnGround() && !jumped) {
            Vec3d forward = mc.player.getRotationVector().multiply(speed.value);
            mc.player.setVelocity(forward.x, height.value, forward.z);
            jumped = true;
        } else if (!mc.player.isOnGround() && jumped) {
            // После прыжка отключаем модуль, чтобы не мешать полету
            setEnabled(false);
        }
    }
}
