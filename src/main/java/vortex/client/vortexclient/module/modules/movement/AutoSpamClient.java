package vortex.client.vortexclient.module.modules.movement;

import vortex.client.vortexclient.module.Module;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AutoSpamClient extends Module {
    private int timer = 0;
    private final Random random = new Random();
    private final List<String> messages = Arrays.asList(
        "Хочешь такой же чит? @RiftSlayeroffical",
        "Лучший клиент для BedWars! @RiftSlayeroffical",
        "Разношу с RiftSlayeroffical! @RiftSlayeroffical",
        "RiftSlayeroffical - выбор профессионалов. @RiftSlayeroffical",
        "Нагибаю с RiftSlayeroffical! ТГ: @RiftSlayeroffical",
        "Этот сервер под защитой RiftSlayeroffical. @RiftSlayeroffical",
        "Хочешь доминировать? Тебе нужен RiftSlayeroffical! @RiftSlayeroffical",
        "RiftSlayeroffical > All. @RiftSlayeroffical",
        "Мой скилл - это RiftSlayeroffical. @RiftSlayeroffical",
        "ебу и нагибаю с : @RiftSlayeroffical"
    );

    public AutoSpamClient() {
        super("AutoSpamClient", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) {
            return;
        }

        timer++;
        // 20 тиков = 1 секунда. 120 тиков = 6 секунд.
        if (timer >= 120) {
            String message = messages.get(random.nextInt(messages.size()));
            // Обход анти-спама: добавляем невидимый, случайный набор символов в конец
            String bypass = " | " + Integer.toHexString(random.nextInt());
            
            mc.player.sendChatMessage(message + bypass);
            timer = 0;
        }
    }
}
