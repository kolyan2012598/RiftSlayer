package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.BooleanSetting;
import vortex.client.vortexclient.module.settings.ModeSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;
import vortex.client.vortexclient.util.RotationUtil;

import java.util.Comparator;

public class KillAura extends Module {
    public ModeSetting priority = new ModeSetting("Priority", this, "Closest", "Closest", "Health", "Smart");
    public NumberSetting range = new NumberSetting("Range", this, 4.5, 1, 6);
    public BooleanSetting autoBlock = new BooleanSetting("AutoBlock", this, true);
    
    public PlayerEntity target = null;

    public KillAura() {
        super("KillAura", Category.COMBAT);
        settings.add(priority);
        settings.add(range);
        settings.add(autoBlock);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        target = findTarget();
        if (target == null) {
            unblock();
            return;
        }

        if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
            float[] rotations = RotationUtil.getRotations(target);
            
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Both(mc.player.getX(), mc.player.getY(), mc.player.getZ(), rotations[0], rotations[1], mc.player.isOnGround()));
            
            unblock();
            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);
            block();
        }
    }
    
    @Override
    public void onDisable() {
        unblock();
    }

    private void block() {
        if (autoBlock.enabled && mc.player.getMainHandStack().getItem() instanceof SwordItem) {
            mc.interactionManager.interactItem(mc.player, mc.world, Hand.MAIN_HAND);
        }
    }

    private void unblock() {
        if (autoBlock.enabled && mc.player.isBlocking()) {
            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
        }
    }

    private PlayerEntity findTarget() {
        Comparator<PlayerEntity> comparator;
        switch (priority.getMode()) {
            case "Health": comparator = Comparator.comparing(PlayerEntity::getHealth); break;
            case "Smart": comparator = Comparator.comparingDouble(this::getThreatLevel).reversed(); break;
            default: comparator = Comparator.comparing(p -> mc.player.distanceTo(p)); break;
        }

        return mc.world.getPlayers().stream()
                .filter(p -> p != mc.player && p.isAlive() && mc.player.distanceTo(p) <= range.value)
                .min(comparator)
                .orElse(null);
    }
    
    private double getThreatLevel(PlayerEntity player) {
        // ... (логика "умного" выбора цели)
        return 0;
    }
}
