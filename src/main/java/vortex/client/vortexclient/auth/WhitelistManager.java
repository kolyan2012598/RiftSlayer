package vortex.client.vortexclient.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WhitelistManager {
    private static final String WHITELIST_URL = "https://pastebin.com/raw/your_paste_id"; // ЗАМЕНИТЕ НА ВАШ URL
    private static final List<String> whitelistedUsers = new ArrayList<>();

    public static void fetchWhitelist() {
        try {
            URL url = new URL(WHITELIST_URL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            whitelistedUsers.clear();
            while ((line = reader.readLine()) != null) {
                whitelistedUsers.add(line.trim());
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isWhitelisted(String username) {
        return whitelistedUsers.contains(username);
    }
    
    public static List<String> getWhitelistedUsers() {
        return whitelistedUsers;
    }
}
