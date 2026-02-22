package vortex.client.vortexclient.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import vortex.client.vortexclient.module.modules.player.AutoBuy;
import vortex.client.vortexclient.ui.ThemeButton;
import vortex.client.vortexclient.util.TeamUtil;

import java.util.Arrays;
import java.util.List;

public class AutoBuyScreen extends Screen {
    // УЛЬТИМАТИВНЫЙ СПИСОК ПРЕДМЕТОВ ДЛЯ BEDWARS
    private final List<Item> availableItems = Arrays.asList(
            // Melee & Tools
            Items.STONE_SWORD, Items.IRON_SWORD, Items.DIAMOND_SWORD,
            Items.SHEARS, Items.WOODEN_PICKAXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE,
            // Consumables & Utility
            Items.GOLDEN_APPLE, Items.ENDER_PEARL, Items.WATER_BUCKET,
            // Ranged & Special
            Items.BOW, Items.ARROW, Items.TNT, Items.FIRE_CHARGE,
            // Blocks & Movement
            Items.LADDER, Items.OBSIDIAN, Items.ELYTRA
    );

    public AutoBuyScreen() {
        super(Text.of("AutoBuy Settings"));
    }

    @Override
    protected void init() {
        super.init();
        int startX = this.width / 2 - ((availableItems.size() + 1) * 22) / 2;
        int y = this.height / 2 - 20;

        // Кнопка для шерсти команды
        this.addButton(new ButtonWidget(startX, y, 20, 20, Text.of(""), (button) -> {
            AutoBuy.buyTeamWool = !AutoBuy.buyTeamWool;
        }));

        startX += 22;

        for (int i = 0; i < availableItems.size(); i++) {
            Item item = availableItems.get(i);
            this.addButton(new ButtonWidget(startX + i * 22, y, 20, 20, Text.of(""), (button) -> {
                if (AutoBuy.itemsToBuy.contains(item)) AutoBuy.itemsToBuy.remove(item);
                else AutoBuy.itemsToBuy.add(item);
            }));
        }

        this.addButton(new ThemeButton(this.width / 2 - 100, this.height - 30, 98, 20, Text.of("Сохранить"), (button) -> {
            this.client.openScreen(null);
        }));
        this.addButton(new ThemeButton(this.width / 2 + 2, this.height - 30, 98, 20, Text.of("Сбросить"), (button) -> {
            AutoBuy.itemsToBuy.clear();
            AutoBuy.buyTeamWool = false;
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        
        int startX = this.width / 2 - ((availableItems.size() + 1) * 22) / 2;
        int y = this.height / 2 - 20;

        // Рендерим иконку шерсти команды
        this.client.getItemRenderer().renderGuiItemIcon(TeamUtil.getTeamWool().getDefaultStack(), startX + 2, y + 2);
        if (AutoBuy.buyTeamWool) {
            fill(matrices, startX, y, startX + 20, y + 20, 0x8000FF00);
        }
        
        startX += 22;

        for (int i = 0; i < availableItems.size(); i++) {
            Item item = availableItems.get(i);
            this.client.getItemRenderer().renderGuiItemIcon(item.getDefaultStack(), startX + i * 22 + 2, y + 2);
            if (AutoBuy.itemsToBuy.contains(item)) {
                fill(matrices, startX + i * 22, y, startX + i * 22 + 20, y + 20, 0x8000FF00);
            }
        }
    }
}
