package vortex.client.vortexclient.module.modules.misc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.util.TelegramBot;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TelegramNotifier extends Module {
    private String botToken = "7249796838:AAGaRZfSBbBmnl0A2fOhkXQkrF-LLL4Ny1U";
    private String chatId;
    private TelegramBot bot;
    private static final File CHAT_ID_FILE = new File(MinecraftClient.getInstance().runDirectory, "vortexclient/telegram_chat.id");

    public TelegramNotifier() {
        super("TelegramNotifier", Category.MISC);
        loadChatId();
    }

    @Override
    public void onEnable() {
        if (botToken.isEmpty()) {
            return;
        }
        
        bot = new TelegramBot(botToken);

        if (chatId == null || chatId.isEmpty()) {
            new Thread(() -> {
                String id = bot.getChatId();
                if (id != null) {
                    this.chatId = id;
                    saveChatId();
                    bot.sendMessage(chatId, "VortexClient started by " + mc.getSession().getUsername());
                }
            }).start();
        } else {
            bot.sendMessage(chatId, "VortexClient started by " + mc.getSession().getUsername());
        }
    }

    public void sendMessage(String message) {
        if (isEnabled() && bot != null && chatId != null && !chatId.isEmpty()) {
            bot.sendMessage(chatId, message);
        }
    }

    private void saveChatId() {
        try {
            CHAT_ID_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CHAT_ID_FILE)) {
                writer.write(chatId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadChatId() {
        if (!CHAT_ID_FILE.exists()) return;
        try (FileReader reader = new FileReader(CHAT_ID_FILE)) {
            char[] buffer = new char[1024];
            int len = reader.read(buffer);
            this.chatId = new String(buffer, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
