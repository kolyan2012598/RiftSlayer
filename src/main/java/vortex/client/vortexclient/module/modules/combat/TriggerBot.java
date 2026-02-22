package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

import java.util.Random;

public class TriggerBot extends Module {
    public NumberSetting minCps = new NumberSetting("Min CPS", this, 8, 1, 20);
    public NumberSetting maxCps = new NumberSetting("Max CPS", this, 12, 1, 20);

    private final Random random = new Random();
    private long lastClickTime = 0;
    private long nextDelay = 100;

    public TriggerBot() {
        super("TriggerBot", Category.COMBAT);
        settings.add(minCps);
        settings.add(maxCps);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.crosshairTarget == null) return;

        if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            EntityHitResult hitResult = (EntityHitResult) mc.crosshairTarget;
            Entity target = hitResult.getEntity();

            if (target instanceof PlayerEntity && target.isAlive()) {
                if (System.currentTimeMillis() - lastClickTime >= nextDelay) {
                    if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                        mc.interactionManager.attackEntity(mc.player, target);
                        mc.player.swingHand(Hand.MAIN_HAND);
                        
                        lastClickTime = System.currentTimeMillis();
                        calculateNextDelay();
                    }
                }
            }
        }
    }
    
    private void calculateNextDelay() {
        double min = minCps.value;
        double max = maxCps.value;
        if (min > max) min = max;
        
        double cps = min + random.nextDouble() * (max - min);
        this.nextDelay = (long) (1000 / cps);
    }
}
