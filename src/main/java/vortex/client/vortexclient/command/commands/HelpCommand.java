package vortex.client.vortexclient.command.commands;

import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.command.CommandManager;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Shows a list of all commands.");
    }

    @Override
    public void execute(String[] args) {
        send("§bVortexClient Commands:");
        for (Command command : CommandManager.INSTANCE.getCommands()) {
            send("§a" + CommandManager.PREFIX + command.getName() + "§7 - " + command.getDescription());
        }
    }
}
