package vortex.client.vortexclient.module.modules.movement;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.ModeSetting;

public class SafeWalk extends Module {
    public ModeSetting mode = new ModeSetting("Mode", this, "Shift", "Shift", "Freeze");

    public SafeWalk() {
        super("SafeWalk", Category.MOVEMENT);
        settings.add(mode);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        // Проверяем, находимся ли мы над пропастью
        boolean isOverAir = mc.world.isAir(mc.player.getBlockPos().down());

        if (mode.getMode().equals("Shift")) {
            // Если мы не на земле (например, в прыжке), отпускаем шифт
            if (!mc.player.isOnGround()) {
                mc.options.keySneak.setPressed(false);
                return;
            }
            // Нажимаем шифт только когда мы над пропастью
            mc.options.keySneak.setPressed(isOverAir);
        } 
        else if (mode.getMode().equals("Freeze")) {
            // Если мы на земле и над пропастью, "замораживаем" движение
            if (mc.player.isOnGround() && isOverAir) {
                mc.player.setVelocity(0, mc.player.getVelocity().y, 0);
            }
        }
    }

    @Override
    public void onDisable() {
        // Гарантированно отпускаем шифт при выключении модуля
        if (mc.options != null) {
            mc.options.keySneak.setPressed(false);
        }
    }
}
