package vortex.client.vortexclient.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TelegramBot {
    private final String botToken;

    public TelegramBot(String botToken) {
        this.botToken = botToken;
    }

    // ПРАВИЛЬНЫЙ И НАДЕЖНЫЙ СПОСОБ: Полностью читаем ответ от сервера
    public void sendMessage(String chatId, String text) {
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                String urlString = "https://api.telegram.org/bot" + botToken + "/sendMessage?chat_id=" + chatId + "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setUseCaches(false);
                
                // Читаем ответ, чтобы убедиться, что запрос обработан
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    while (in.readLine() != null) {
                        // Просто читаем до конца
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }).start();
    }

    public String getChatId() {
        HttpURLConnection conn = null;
        try {
            String urlString = "https://api.telegram.org/bot" + botToken + "/getUpdates?limit=1&offset=-1";
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                
                JsonObject json = new JsonParser().parse(content.toString()).getAsJsonObject();
                if (json.get("ok").getAsBoolean()) {
                    JsonArray results = json.getAsJsonArray("result");
                    if (results.size() > 0) {
                        JsonObject lastUpdate = results.get(0).getAsJsonObject();
                        if (lastUpdate.has("message")) {
                            return lastUpdate.getAsJsonObject("message").getAsJsonObject("chat").get("id").getAsString();
                        } else if (lastUpdate.has("my_chat_member")) {
                            return lastUpdate.getAsJsonObject("my_chat_member").getAsJsonObject("chat").get("id").getAsString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}
