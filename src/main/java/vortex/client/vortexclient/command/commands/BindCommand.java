package vortex.client.vortexclient.command.commands;

import org.lwjgl.glfw.GLFW;
import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "Binds a module to a key.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            send("Usage: .bind <module> <key>");
            return;
        }

        String moduleName = args[0];
        String keyName = args[1].toUpperCase();

        Module module = ModuleManager.INSTANCE.getModules().stream()
                .filter(m -> m.getName().equalsIgnoreCase(moduleName))
                .findFirst()
                .orElse(null);

        if (module == null) {
            send("§cModule not found.");
            return;
        }

        int key = -1;
        try {
            key = GLFW.class.getField("GLFW_KEY_" + keyName).getInt(null);
        } catch (Exception e) {
            send("§cInvalid key.");
            return;
        }

        module.setKey(key);
        send("§aBound " + module.getName() + " to " + keyName);
    }
}
