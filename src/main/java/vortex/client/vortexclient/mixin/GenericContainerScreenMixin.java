package vortex.client.vortexclient.mixin;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.Item;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.module.modules.player.AutoBuy;
import vortex.client.vortexclient.util.TeamUtil;

@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin extends HandledScreen<GenericContainerScreenHandler> {

    public GenericContainerScreenMixin(GenericContainerScreenHandler handler, net.minecraft.entity.player.PlayerInventory inventory, net.minecraft.text.Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        if (AutoBuy.shouldBuy) {
            Item teamWool = TeamUtil.getTeamWool();
            
            for (Slot slot : this.handler.slots) {
                Item itemInSlot = slot.getStack().getItem();
                if (AutoBuy.itemsToBuy.contains(itemInSlot) || (AutoBuy.buyTeamWool && itemInSlot == teamWool)) {
                    this.onMouseClick(slot, slot.id, 0, SlotActionType.QUICK_MOVE);
                }
            }
            AutoBuy.shouldBuy = false;
            this.client.player.closeScreen();
        }
    }
}
