package vortex.client.vortexclient.module.modules.movement;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class NoClip extends Module {
    public NumberSetting speed = new NumberSetting("Speed", this, 0.5, 0.1, 5.0);

    public NoClip() {
        super("NoClip", Category.MOVEMENT);
        settings.add(speed);
    }

    @Override
    public void onTick() {
        if (mc.player == null) return;
        
        // Отключаем столкновения
        mc.player.noClip = true;
        
        // Позволяем двигаться свободно
        mc.player.setVelocity(0, 0, 0);
        float moveSpeed = (float) speed.value;

        if (mc.options.keyJump.isPressed()) mc.player.setVelocity(mc.player.getVelocity().add(0, moveSpeed, 0));
        if (mc.options.keySneak.isPressed()) mc.player.setVelocity(mc.player.getVelocity().add(0, -moveSpeed, 0));
        
        mc.player.travel(new net.minecraft.util.math.Vec3d(mc.player.sidewaysSpeed, 0, mc.player.forwardSpeed).multiply(moveSpeed));
    }
}
