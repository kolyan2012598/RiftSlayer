package vortex.client.vortexclient.command.commands;

import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.settings.BooleanSetting;
import vortex.client.vortexclient.module.settings.ModeSetting;
import vortex.client.vortexclient.module.settings.NumberSetting;
import vortex.client.vortexclient.module.settings.Setting;
import vortex.client.vortexclient.module.settings.StringSetting;

import java.util.Arrays;
import java.util.Optional;

public class SetCommand extends Command {
    public SetCommand() {
        super("set", "Changes a setting of a module.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            send("§cUsage: .set <module> <setting> <value>");
            return;
        }

        Optional<Module> moduleOpt = ModuleManager.INSTANCE.getModules().stream()
                .filter(m -> m.getName().equalsIgnoreCase(args[0])).findFirst();

        if (!moduleOpt.isPresent()) {
            send("§cModule not found.");
            return;
        }

        Optional<Setting> settingOpt = moduleOpt.get().settings.stream()
                .filter(s -> s.name.equalsIgnoreCase(args[1])).findFirst();

        if (!settingOpt.isPresent()) {
            send("§cSetting not found.");
            return;
        }

        Setting setting = settingOpt.get();
        String value = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        try {
            if (setting instanceof BooleanSetting) {
                ((BooleanSetting) setting).enabled = Boolean.parseBoolean(value);
            } else if (setting instanceof NumberSetting) {
                ((NumberSetting) setting).value = Double.parseDouble(value);
            } else if (setting instanceof ModeSetting) {
                ((ModeSetting) setting).setMode(value);
            } else if (setting instanceof StringSetting) {
                ((StringSetting) setting).value = value;
            }
            send("§a" + setting.name + " set to " + value);
        } catch (Exception e) {
            send("§cInvalid value!");
        }
    }
}
