package vortex.client.vortexclient.command;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public abstract class Command {
    public String name;
    public String description;
    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void execute(String[] args);

    public void send(String message) {
        if (mc.player != null) {
            mc.player.sendMessage(new LiteralText(message), false);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
