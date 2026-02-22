package vortex.client.vortexclient.module.modules.player;

import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

import java.util.Random;

public class AutoClicker extends Module {
    public NumberSetting minCps = new NumberSetting("Min CPS", this, 8, 1, 20);
    public NumberSetting maxCps = new NumberSetting("Max CPS", this, 12, 1, 20);

    private final Random random = new Random();
    private long lastClickTime = 0;
    private long nextDelay = 100;

    public AutoClicker() {
        super("AutoClicker", Category.PLAYER);
        settings.add(minCps);
        settings.add(maxCps);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        if (mc.options.keyAttack.isPressed()) {
            if (System.currentTimeMillis() - lastClickTime >= nextDelay) {
                if (mc.crosshairTarget instanceof EntityHitResult) {
                    Entity target = ((EntityHitResult) mc.crosshairTarget).getEntity();
                    if (target != mc.player) {
                        mc.interactionManager.attackEntity(mc.player, target);
                        mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
                
                lastClickTime = System.currentTimeMillis();
                calculateNextDelay();
            }
        }
    }

    private void calculateNextDelay() {
        double min = minCps.value;
        double max = maxCps.value;
        if (min > max) {
            min = max;
        }
        
        double cps = min + random.nextDouble() * (max - min);
        this.nextDelay = (long) (1000 / cps);
    }
}
