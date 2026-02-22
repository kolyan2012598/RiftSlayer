package vortex.client.vortexclient.module.modules.misc;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.settings.StringSetting;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class Radio extends Module {
    public StringSetting url = new StringSetting("URL", this, "");
    private Thread radioThread;
    private Player player;

    public Radio() {
        super("Radio", Category.MISC);
        settings.add(url);
    }

    @Override
    public void onEnable() {
        if (url.value.isEmpty()) {
            setEnabled(false);
            return;
        }
        
        radioThread = new Thread(() -> {
            try {
                URL url = new URL(this.url.value);
                InputStream inputStream = new BufferedInputStream(url.openStream());
                player = new Player(inputStream);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        radioThread.start();
    }

    @Override
    public void onDisable() {
        if (radioThread != null && radioThread.isAlive()) {
            radioThread.interrupt();
        }
        if (player != null) {
            player.close();
        }
    }
}
