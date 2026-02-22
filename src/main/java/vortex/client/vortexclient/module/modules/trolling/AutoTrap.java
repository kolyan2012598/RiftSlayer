package vortex.client.vortexclient.module.modules.trolling;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

import java.util.Comparator;

public class AutoTrap extends Module {
    public NumberSetting range = new NumberSetting("Range", this, 5, 1, 7);
    private PlayerEntity target = null;

    public AutoTrap() {
        super("AutoTrap", Category.TROLLING);
        settings.add(range);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        // 1️⃣ Находим цель
        target = findTarget();
        if (target == null) return;

        // 2️⃣ Поворачиваем голову плавно к цели
        faceTarget(target);

        // 3️⃣ Ищем слот с блоком
        int blockSlot = findBlockSlot();
        if (blockSlot == -1) return;

        BlockPos targetPos = target.getBlockPos();
        BlockPos[] trapPositions = generateTrapPositions(targetPos);

        // 4️⃣ Сохраняем текущий слот
        int oldSlot = mc.player.inventory.selectedSlot;
        mc.player.inventory.selectedSlot = blockSlot;

        // 5️⃣ Ставим один блок за тик
        for (BlockPos pos : trapPositions) {
            if (mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
                mc.interactionManager.interactBlock(
                        mc.player,
                        mc.world,
                        Hand.MAIN_HAND,
                        new BlockHitResult(Vec3d.ofCenter(pos), Direction.DOWN, pos, false)
                );
                break; // один блок за тик
            }
        }

        mc.player.inventory.selectedSlot = oldSlot;
    }

    // ==============================
    // Найти цель
    // ==============================
    private PlayerEntity findTarget() {
        return mc.world.getPlayers().stream()
                .filter(p -> p != mc.player && p.isAlive() && mc.player.distanceTo(p) <= range.value)
                .min(Comparator.comparingDouble(p -> mc.player.distanceTo(p)))
                .orElse(null);
    }

    // ==============================
    // Найти слот с блоком
    // ==============================
    private int findBlockSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStack(i);
            if (stack.getItem() instanceof BlockItem) {
                return i;
            }
        }
        return -1;
    }

    // ==============================
    // Поворот головы к цели
    // ==============================
    private void faceTarget(PlayerEntity entity) {
        double dx = entity.getX() - mc.player.getX();
        double dz = entity.getZ() - mc.player.getZ();
        double dy = entity.getEyeY() - mc.player.getEyeY();

        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

        // Плавный поворот
        mc.player.yaw = yaw;
        mc.player.pitch = pitch;
    }

    // ==============================
    // Генерация позиций ловушки
    // ==============================
    private BlockPos[] generateTrapPositions(BlockPos center) {
        return new BlockPos[]{
                center.north(), center.south(), center.east(), center.west(),
                center.north().east(), center.north().west(), center.south().east(), center.south().west(),
                center.north().up(), center.south().up(), center.east().up(), center.west().up(),
                center.up(2)
        };
    }
}
