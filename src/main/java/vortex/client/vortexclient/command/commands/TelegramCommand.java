package vortex.client.vortexclient.command.commands;

import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.misc.TelegramNotifier;

public class TelegramCommand extends Command {
    public TelegramCommand() {
        super("telegram", "Sends a message to the configured Telegram chat.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            send("§cUsage: .telegram <message>");
            return;
        }
        
        TelegramNotifier notifier = (TelegramNotifier) ModuleManager.INSTANCE.getModules().stream()
                .filter(m -> m instanceof TelegramNotifier)
                .findFirst().orElse(null);
        
        if (notifier != null && notifier.isEnabled()) {
            String message = String.join(" ", args);
            notifier.sendMessage(message);
            send("§aMessage sent to Telegram!");
        } else {
            send("§cTelegramNotifier is not enabled or configured.");
        }
    }
}
