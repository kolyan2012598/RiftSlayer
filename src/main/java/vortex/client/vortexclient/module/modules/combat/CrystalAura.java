package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.BooleanSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;
import vortex.client.vortexclient.util.CrystalUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CrystalAura extends Module {
    public NumberSetting targetRange = new NumberSetting("Target Range", this, 10, 1, 15);
    public NumberSetting placeRange = new NumberSetting("Place Range", this, 5, 1, 7);
    public NumberSetting breakRange = new NumberSetting("Break Range", this, 5, 1, 7);
    public NumberSetting minDamage = new NumberSetting("Min Damage", this, 6, 1, 20);
    public BooleanSetting autoSwitch = new BooleanSetting("AutoSwitch", this, true);

    private PlayerEntity target = null;

    public CrystalAura() {
        super("CrystalAura", Category.COMBAT);
        settings.add(targetRange);
        settings.add(placeRange);
        settings.add(breakRange);
        settings.add(minDamage);
        settings.add(autoSwitch);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        // 1. Найти цель
        target = findTarget();
        if (target == null) return;

        // 2. Взорвать кристалл, если он есть
        if (breakCrystal()) {
            return; // Ждем следующего тика
        }

        // 3. Поставить кристалл
        placeCrystal();
    }

    private boolean breakCrystal() {
        List<EndCrystalEntity> crystals = StreamSupport.stream(mc.world.getEntities().spliterator(), false)
                .filter(e -> e instanceof EndCrystalEntity && mc.player.distanceTo(e) <= breakRange.value)
                .map(e -> (EndCrystalEntity) e)
                .collect(Collectors.toList());

        for (EndCrystalEntity crystal : crystals) {
            mc.interactionManager.attackEntity(mc.player, crystal);
            mc.player.swingHand(Hand.MAIN_HAND);
            return true;
        }
        return false;
    }

    private void placeCrystal() {
        BlockPos bestPos = findBestPos();
        if (bestPos == null) return;

        int crystalSlot = findCrystalSlot();
        if (crystalSlot == -1) return;

        int oldSlot = mc.player.inventory.selectedSlot;
        if (autoSwitch.enabled) {
            mc.player.inventory.selectedSlot = crystalSlot;
        }

        BlockHitResult hitResult = new BlockHitResult(Vec3d.ofCenter(bestPos), Direction.DOWN, bestPos, false);
        mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, hitResult);

        if (autoSwitch.enabled) {
            mc.player.inventory.selectedSlot = oldSlot;
        }
    }

    private BlockPos findBestPos() {
        return CrystalUtil.getPlaceableBlocks(placeRange.value).stream()
                .filter(pos -> CrystalUtil.getDamage(pos, target) >= minDamage.value)
                .max(Comparator.comparingDouble(pos -> CrystalUtil.getDamage(pos, target)))
                .orElse(null);
    }

    private PlayerEntity findTarget() {
        return mc.world.getPlayers().stream()
                .filter(p -> p != mc.player && p.isAlive() && mc.player.distanceTo(p) <= targetRange.value)
                .min(Comparator.comparing(p -> mc.player.distanceTo(p)))
                .orElse(null);
    }

    private int findCrystalSlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStack(i).getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }
        return -1;
    }
}
