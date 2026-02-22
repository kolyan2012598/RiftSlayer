package vortex.client.vortexclient.command.commands;

import vortex.client.vortexclient.command.Command;

import java.util.Arrays;

public class SayCommand extends Command {
    public SayCommand() {
        super("say", "Sends a message as another player using /tellraw.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            send("§cUsage: .say <player> <message>");
            return;
        }
        
        String playerName = args[0];
        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        
        // Формируем JSON для /tellraw
        String json = "[\"\",{\"text\":\"<" + playerName + "> \"},{\"text\":\"" + message + "\"}]";
        
        if (mc.player != null) {
            mc.player.sendChatMessage("/tellraw @a " + json);
        }
    }
}
