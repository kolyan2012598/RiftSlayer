package vortex.client.vortexclient.module.modules.render;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

import java.util.UUID;

public class FreeCam extends Module {
    public NumberSetting speed = new NumberSetting("Speed", this, 0.5, 0.1, 5.0);
    private OtherClientPlayerEntity freecamPlayer;

    public FreeCam() {
        super("FreeCam", Category.RENDER);
        settings.add(speed);
    }

    @Override
    public void onEnable() {
        if (mc.player == null || mc.world == null) {
            setEnabled(false);
            return;
        }
        
        freecamPlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.randomUUID(), "Freecam"));
        freecamPlayer.copyPositionAndRotation(mc.player);
        freecamPlayer.abilities.flying = true;
        
        mc.setCameraEntity(freecamPlayer);
    }

    @Override
    public void onDisable() {
        if (mc.player == null || freecamPlayer == null) return;
        
        mc.setCameraEntity(mc.player);
        // ПРАВИЛЬНЫЙ СПОСОБ для 1.16.5
        mc.world.removeEntity(freecamPlayer.getEntityId());
        freecamPlayer = null;
    }

    @Override
    public void onTick() {
        if (freecamPlayer == null) return;

        float moveSpeed = (float) speed.value;
        
        freecamPlayer.setVelocity(0, 0, 0);
        
        if (mc.options.keyJump.isPressed()) freecamPlayer.setVelocity(freecamPlayer.getVelocity().add(0, moveSpeed, 0));
        if (mc.options.keySneak.isPressed()) freecamPlayer.setVelocity(freecamPlayer.getVelocity().add(0, -moveSpeed, 0));
        
        freecamPlayer.travel(new net.minecraft.util.math.Vec3d(mc.player.sidewaysSpeed, 0, mc.player.forwardSpeed).multiply(moveSpeed));
    }
}
