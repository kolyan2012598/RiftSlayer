package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import vortex.client.vortexclient.module.Module;

public class AutoTotem extends Module {

    public AutoTotem() {
        super("AutoTotem", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;

        // Если в оффхенде уже тотем — ничего не делаем
        if (mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) return;

        // Ищем тотем в инвентаре (с 9 по 35 — обычный инвентарь)
        for (int i = 9; i <= 35; i++) {
            if (mc.player.inventory.getStack(i).isEmpty()) continue; // пустой слот — пропускаем
            if (mc.player.inventory.getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {

                // Меняем слот на тотем в оффхенд
                swapToOffhand(i);
                return;
            }
        }
    }

    private void swapToOffhand(int slot) {
        int offhandSlot = 45; // Оффхенд слот в PlayerScreenHandler
        int syncId = mc.player.playerScreenHandler.syncId;

        // Классическая “трёхкликовая” схема для Fabric 1.16.5
        mc.interactionManager.clickSlot(syncId, slot, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, offhandSlot, 0, SlotActionType.PICKUP, mc.player);
        mc.interactionManager.clickSlot(syncId, slot, 0, SlotActionType.PICKUP, mc.player);
    }
}
