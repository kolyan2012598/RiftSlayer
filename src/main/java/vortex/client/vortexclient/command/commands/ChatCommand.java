package vortex.client.vortexclient.command.commands;

import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.command.MacroManager;

import java.util.Arrays;

public class ChatCommand extends Command {
    public ChatCommand() {
        super("chat", "Manages chat macros.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            send("Usage: .chat <add|remove|list> [name] [message]");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length < 3) {
                    send("Usage: .chat add <name> <message>");
                    return;
                }
                String name = args[1];
                String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                MacroManager.INSTANCE.addMacro(name, message);
                send("Macro '" + name + "' added!");
                break;
            case "remove":
                if (args.length != 2) {
                    send("Usage: .chat remove <name>");
                    return;
                }
                MacroManager.INSTANCE.removeMacro(args[1]);
                send("Macro '" + args[1] + "' removed!");
                break;
            case "list":
                if (MacroManager.INSTANCE.getMacros().isEmpty()) {
                    send("No macros found.");
                } else {
                    send("Available macros: " + String.join(", ", MacroManager.INSTANCE.getMacros().keySet()));
                }
                break;
            default:
                send("Usage: .chat <add|remove|list> [name] [message]");
                break;
        }
    }
}
