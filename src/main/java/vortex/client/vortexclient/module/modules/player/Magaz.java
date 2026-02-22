package vortex.client.vortexclient.module.modules.player;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.util.EntityUtil;

public class Magaz extends Module {
    public Magaz() {
        super("Magaz", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        if (EntityUtil.interactWithShopVillager()) {
            AutoBuy.shouldBuy = true;
        }
        setEnabled(false); // Отключаем модуль сразу после использования
    }
}
