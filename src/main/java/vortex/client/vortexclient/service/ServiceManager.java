package vortex.client.vortexclient.service;

import vortex.client.vortexclient.module.Module;
import vortex.client.vortexclient.service.services.DiscordRPCService;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    public static final ServiceManager INSTANCE = new ServiceManager();
    private final List<Module> services = new ArrayList<>();

    public ServiceManager() {
        addService(new DiscordRPCService());
    }

    private void addService(Module service) {
        services.add(service);
        service.setEnabled(true);
    }

    public <T extends Module> T getService(Class<T> clazz) {
        return (T) services.stream().filter(s -> s.getClass() == clazz).findFirst().orElse(null);
    }

    public void onTick() {
        for (Module service : services) {
            service.onTick();
        }
    }
}
