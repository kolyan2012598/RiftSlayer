package vortex.client.vortexclient.command.commands;

import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.config.ConfigManager;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config", "Manages client configs.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            send("§cUsage: .config <save|load|list|remove> [name]");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "save":
                if (args.length != 2) {
                    send("§cUsage: .config save <name>");
                    return;
                }
                ConfigManager.INSTANCE.saveConfig(args[1]);
                send("§aConfig '" + args[1] + "' saved!");
                break;
            case "load":
                if (args.length != 2) {
                    send("§cUsage: .config load <name>");
                    return;
                }
                ConfigManager.INSTANCE.loadConfig(args[1]);
                send("§aConfig '" + args[1] + "' loaded!");
                break;
            case "list":
                File[] files = ConfigManager.INSTANCE.getConfigFiles();
                if (files == null || files.length == 0) {
                    send("§cNo configs found.");
                } else {
                    String fileNames = Arrays.stream(files)
                            .map(file -> file.getName().replace(".json", ""))
                            .collect(Collectors.joining(", "));
                    send("§aAvailable configs: " + fileNames);
                }
                break;
            case "remove":
                if (args.length != 2) {
                    send("§cUsage: .config remove <name>");
                    return;
                }
                ConfigManager.INSTANCE.removeConfig(args[1]);
                send("§aConfig '" + args[1] + "' removed!");
                break;
            default:
                send("§cUsage: .config <save|load|list|remove> [name]");
                break;
        }
    }
}
