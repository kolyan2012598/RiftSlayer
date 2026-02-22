package vortex.client.vortexclient.command;

import vortex.client.vortexclient.command.commands.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    public static final CommandManager INSTANCE = new CommandManager();
    public static final String PREFIX = ".";
    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new OpCommand());
        addCommand(new HelpCommand());
        addCommand(new ConfigCommand());
        addCommand(new BindCommand());
        addCommand(new AdminCommand());
    }

    private void addCommand(Command command) {
        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void handleMessage(String message) {
        if (!message.startsWith(PREFIX)) return;
        
        String[] args = message.substring(PREFIX.length()).split(" ");
        String commandName = args[0];
        
        for (Command command : commands) {
            if (command.name.equalsIgnoreCase(commandName)) {
                command.execute(Arrays.copyOfRange(args, 1, args.length));
                return;
            }
        }
    }
}
