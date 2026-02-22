package vortex.client.vortexclient.module.modules.player;

import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import vortex.client.vortexclient.module.Module;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class FastBreak extends Module {
    public FastBreak() {
        super("FastBreak", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.player != null) {
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 2, 1));
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.removeStatusEffect(StatusEffects.HASTE);
        }
    }
}
