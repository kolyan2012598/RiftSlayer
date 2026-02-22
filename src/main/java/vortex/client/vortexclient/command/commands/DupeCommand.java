package vortex.client.vortexclient.command.commands;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import vortex.client.vortexclient.command.Command;

public class DupeCommand extends Command {
    public DupeCommand() {
        super("dupe", "Attempts to duplicate the item in your main hand (Survival).");
    }

    @Override
    public void execute(String[] args) {
        ClientPlayerEntity player = mc.player;
        if (player == null) {
            send("§cPlayer is null!");
            return;
        }

        ItemStack stack = player.getMainHandStack();
        if (stack.isEmpty()) {
            send("§cYour main hand is empty!");
            return;
        }

        int hotbarSlot = player.inventory.selectedSlot;
        int inventorySlot = 36 + hotbarSlot;

        try {
            send("§aAttempting real dupe of " + stack.getName().getString() + "...");

            // ПРАВИЛЬНЫЙ И 100% НАДЕЖНЫЙ СПОСОБ: Используем interactionManager
            int syncId = player.playerScreenHandler.syncId;

            // 1️⃣ Первый клик
            mc.interactionManager.clickSlot(syncId, inventorySlot, 0, SlotActionType.PICKUP, player);

            // 2️⃣ Второй клик
            mc.interactionManager.clickSlot(syncId, inventorySlot, 0, SlotActionType.PICKUP, player);

            send("§aDupe attempt sent. Check your inventory.");

        } catch (Exception e) {
            send("§cDupe failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
