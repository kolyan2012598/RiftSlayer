package vortex.client.vortexclient;

import net.fabricmc.api.ModInitializer;
import vortex.client.vortexclient.command.CommandManager;
import vortex.client.vortexclient.config.ConfigManager;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.service.ServiceManager;

public class Vortexclient implements ModInitializer {
    @Override
    public void onInitialize() {
        // Проверка версии Java удалена, как вы и просили.

        new ModuleManager();
        new CommandManager();
        new ConfigManager();
        new ServiceManager();
    }
}
