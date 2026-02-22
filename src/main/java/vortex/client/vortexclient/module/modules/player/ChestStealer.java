package vortex.client.vortexclient.module.modules.player;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.BooleanSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;

import java.util.Random;

public class ChestStealer extends Module {
    public NumberSetting delay = new NumberSetting("Delay", this, 100, 0, 500);
    public BooleanSetting random = new BooleanSetting("Random", this, true);

    private final Random r = new Random();
    private long lastClickTime = 0;

    public ChestStealer() {
        super("ChestStealer", Category.PLAYER);
        settings.add(delay);
        settings.add(random);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.interactionManager == null || !(mc.currentScreen instanceof GenericContainerScreen)) {
            return;
        }

        GenericContainerScreen screen = (GenericContainerScreen) mc.currentScreen;
        if (System.currentTimeMillis() - lastClickTime < delay.value + (random.enabled ? r.nextInt(50) : 0)) {
            return;
        }

        for (int i = 0; i < screen.getScreenHandler().slots.size() - 36; i++) {
            ItemStack stack = screen.getScreenHandler().getSlot(i).getStack();
            if (!stack.isEmpty()) {
                mc.interactionManager.clickSlot(screen.getScreenHandler().syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
                lastClickTime = System.currentTimeMillis();
                return;
            }
        }
    }
}
