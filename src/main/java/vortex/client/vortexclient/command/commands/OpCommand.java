package vortex.client.vortexclient.command.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.util.FakeOpManager;

public class OpCommand extends Command {
    public OpCommand() {
        super("op", "Visually gives you operator status.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            send("Usage: .op <username>");
            return;
        }
        
        String username = args[0];
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc.player != null && mc.player.getName().getString().equalsIgnoreCase(username)) {
            FakeOpManager.setFakingOp(true);
            mc.player.sendMessage(new LiteralText(Formatting.GRAY + "" + Formatting.ITALIC + "[Server: Made " + username + " a server operator]"), false);
        } else {
            send("§cYou can only op yourself.");
        }
    }
}
