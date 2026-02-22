package vortex.client.vortexclient.module;

import vortex.client.vortexclient.module.modules.bypass.*;
import vortex.client.vortexclient.module.modules.combat.*;
import vortex.client.vortexclient.module.modules.movement.*;
import vortex.client.vortexclient.module.modules.player.*;
import vortex.client.vortexclient.module.modules.render.*;
import vortex.client.vortexclient.module.modules.misc.*;
import vortex.client.vortexclient.module.modules.trolling.*;
import vortex.client.vortexclient.module.modules.visuals.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Combat
        addModule(new AutoArmor());
        addModule(new AutoTotem());
        addModule(new Criticals());
        addModule(new KillAura());
        addModule(new TriggerBot());
        addModule(new Velocity());
        addModule(new Surround());
        addModule(new ProjectileAimbot());
        addModule(new BedNuker());
        addModule(new CrystalAura());
        
        // Movement
        addModule(new AirJump());
        addModule(new AutoSprint());
        addModule(new ElytraFly());
        addModule(new Flight());
        addModule(new InventoryMove());
        addModule(new NoClip());
        addModule(new SafeWalk());
        addModule(new Scaffold());
        addModule(new Speed());
        addModule(new AutoSpamClient());
        addModule(new AirStuck());
        addModule(new Jesus());
        addModule(new Spider());
        addModule(new Step());
        addModule(new TargetStrafe());
        addModule(new NoSlow());
        addModule(new Jetpack());
        addModule(new LongJump());

        // Player
        addModule(new AutoClicker());
        addModule(new FastPlace());
        addModule(new NoFall());
        addModule(new Timer());
        addModule(new AutoTool());
        addModule(new ChestStealer());
        addModule(new Blink());
        addModule(new FastBreak());
        addModule(new AutoFish());
        addModule(new AntiHunger());
        addModule(new XCarry());
        addModule(new FakeCreative());
        addModule(new Magaz());
        addModule(new AutoBuy());
        addModule(new AutoLoot());

        // Render
        addModule(new ESP());
        addModule(new FreeCam());
        addModule(new FullBright());
        addModule(new Glow());
        addModule(new Hat());
        addModule(new Tracers());
        addModule(new XRay());
        addModule(new FOV());
        addModule(new BlockESP());
        addModule(new NoHurtCam());
        addModule(new Trajectories());
        addModule(new ItemESP());

        // Misc
        addModule(new AutoRespawn());
        addModule(new MCF());
        addModule(new AntiAFK());
        addModule(new AutoReconnect());
        addModule(new Radio());
        addModule(new Disabler());
        addModule(new TelegramNotifier());
        
        // Trolling
        addModule(new Spinner());
        addModule(new FakeLag());
        addModule(new Derp());
        addModule(new AutoTrap());
        
        // Visuals
        addModule(new Theme());
        addModule(new HUD());
        addModule(new NoRender());
        addModule(new ViewModel());
        addModule(new Masturbate());
        addModule(new Chams());
        addModule(new Breadcrumbs());
        addModule(new ChinaHat());
        addModule(new TargetHUD());

        // Bypasses
        addModule(new Bypass());
    }

    private void addModule(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getEnabledModules() {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

    public List<Module> getModulesInCategory(Module.Category category) {
        return modules.stream().filter(m -> m.getCategory() == category).collect(Collectors.toList());
    }
    
    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) modules.stream().filter(m -> m.getClass() == clazz).findFirst().orElse(null);
    }
    
    public void onTick() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onTick();
            }
        }
    }
}
