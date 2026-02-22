package vortex.client.vortexclient.command.commands;

import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.ui.AdminPanelScreen;

public class AdminCommand extends Command {
    private static final String ADMIN_PASSWORD = "RiftSlayerAdmin_#@!_Secure_2027";

    public AdminCommand() {
        super("admin", "Opens the admin panel.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1 || !args[0].equals(ADMIN_PASSWORD)) {
            send("§cIncorrect password.");
            return;
        }
        mc.openScreen(new AdminPanelScreen());
    }
}
