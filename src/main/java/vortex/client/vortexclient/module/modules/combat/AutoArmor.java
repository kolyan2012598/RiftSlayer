package vortex.client.vortexclient.module.modules.combat;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import vortex.client.vortexclient.module.Module;

public class AutoArmor extends Module {
    public AutoArmor() {
        super("AutoArmor", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null) return;

        for (int i = 0; i < 4; i++) {
            equipBestArmor(i);
        }
    }

    private void equipBestArmor(int armorSlot) {
        int bestArmorInInventory = -1;
        int maxProtection = -1;

        for (int i = 9; i <= 35; i++) {
            ItemStack stack = mc.player.inventory.getStack(i);
            if (stack.getItem() instanceof ArmorItem) {
                ArmorItem item = (ArmorItem) stack.getItem();
                if (item.getSlotType().getEntitySlotId() == armorSlot) {
                    if (item.getProtection() > maxProtection) {
                        maxProtection = item.getProtection();
                        bestArmorInInventory = i;
                    }
                }
            }
        }

        ItemStack currentArmor = mc.player.inventory.getArmorStack(armorSlot);
        if (bestArmorInInventory != -1) {
            if (currentArmor.isEmpty() || (currentArmor.getItem() instanceof ArmorItem && ((ArmorItem)currentArmor.getItem()).getProtection() < maxProtection)) {
                int armorSlotId = 5 + armorSlot;
                mc.interactionManager.clickSlot(mc.player.playerScreenHandler.syncId, bestArmorInInventory, 0, SlotActionType.QUICK_MOVE, mc.player);
            }
        }
    }
}
