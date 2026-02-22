package vortex.client.vortexclient.module.modules.movement;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.NumberSetting;

public class FastBridge extends Module {
    public NumberSetting delay = new NumberSetting("Delay", this, 50, 0, 500);
    private long lastPlaceTime;

    public FastBridge() {
        super("FastBridge", Category.MOVEMENT);
        settings.add(delay);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        // Таймер для контроля скорости
        if (System.currentTimeMillis() - lastPlaceTime < delay.value) return;

        // Блок под игроком
        BlockPos targetPos = mc.player.getBlockPos().down();

        // Проверка, что блок можно поставить
        if (!mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) return;

        // Находим слот с блоком
        int blockSlot = findBlockSlot();
        if (blockSlot == -1) return;

        // Сохраняем старый слот
        int oldSlot = mc.player.inventory.selectedSlot;
        mc.player.inventory.selectedSlot = blockSlot;

        // Создаём корректный BlockHitResult
        Vec3d hitVec = new Vec3d(
                targetPos.getX() + 0.5,
                targetPos.getY() + 0.5,
                targetPos.getZ() + 0.5
        );
        BlockHitResult hitResult = new BlockHitResult(hitVec, Direction.UP, targetPos, false);

        // Ставим блок
        mc.interactionManager.interactBlock(mc.player, mc.world, Hand.MAIN_HAND, hitResult);

        // Возвращаем старый слот
        mc.player.inventory.selectedSlot = oldSlot;

        // Обновляем таймер
        lastPlaceTime = System.currentTimeMillis();
    }

    // Поиск первого слота с обычным блоком (только BlockItem)
    private int findBlockSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStack(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem) {
                return i;
            }
        }
        return -1;
    }
}
