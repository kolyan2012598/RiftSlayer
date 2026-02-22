package vortex.client.vortexclient.mixin;

import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.command.Command;

import java.net.InetSocketAddress;

@Mixin(GameMenuScreen.class)
public class PauseScreenMixin extends Screen {
    protected PauseScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void onInit(CallbackInfo ci) {
        ci.cancel();
        super.init();
        int i = -16;

        this.addButton(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 24 + i, 204, 20, new TranslatableText("menu.returnToGame"), (button) -> {
            this.client.openScreen(null);
            this.client.mouse.lockCursor();
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 48 + i, 98, 20, new TranslatableText("gui.advancements"), (button) -> {
            this.client.openScreen(new AdvancementsScreen(this.client.player.networkHandler.getAdvancementHandler()));
        }));
        this.addButton(new ButtonWidget(this.width / 2 + 4, this.height / 4 + 48 + i, 98, 20, new TranslatableText("gui.stats"), (button) -> {
            this.client.openScreen(new StatsScreen(this, this.client.player.getStatHandler()));
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 72 + i, 98, 20, new TranslatableText("menu.options"), (button) -> {
            this.client.openScreen(new OptionsScreen(this, this.client.options));
        }));

        ButtonWidget openToLanButton = this.addButton(new ButtonWidget(this.width / 2 + 4, this.height / 4 + 72 + i, 98, 20, new TranslatableText("menu.shareToLan"), (button) -> {
            this.client.openScreen(new OpenToLanScreen(this));
        }));
        openToLanButton.active = this.client.isIntegratedServerRunning() && !this.client.getServer().isRemote();

        ButtonWidget reconnectButton = this.addButton(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 96 + i, 98, 20, Text.of("Reconnect"), (button) -> {
            ServerInfo serverInfo = this.client.getCurrentServerEntry();
            if (serverInfo != null) {
                this.client.world.disconnect();
                this.client.openScreen(new ConnectScreen(this, this.client, serverInfo));
            }
        }));
        reconnectButton.active = this.client.getCurrentServerEntry() != null;

        ButtonWidget getIpButton = this.addButton(new ButtonWidget(this.width / 2 + 4, this.height / 4 + 96 + i, 98, 20, Text.of("Get IP"), (button) -> {
            ServerInfo serverInfo = this.client.getCurrentServerEntry();
            if (serverInfo != null) {
                new Thread(() -> {
                    try {
                        InetSocketAddress address = new InetSocketAddress(serverInfo.address, 25565);
                        new Command("",""){ @Override public void execute(String[] args) {} }.send("§aServer IP: " + address.getAddress().getHostAddress());
                    } catch (Exception e) {
                        new Command("",""){ @Override public void execute(String[] args) {} }.send("§cCould not resolve IP.");
                    }
                }).start();
            }
        }));
        getIpButton.active = this.client.getCurrentServerEntry() != null;

        // ПРАВИЛЬНАЯ И ГАРАНТИРОВАННО РАБОЧАЯ ЛОГИКА ВЫХОДА
        this.addButton(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 120 + i, 204, 20, new TranslatableText("menu.disconnect"), (button) -> {
            boolean isSinglePlayer = this.client.isIntegratedServerRunning();
            button.active = false;
            this.client.world.disconnect();
            if (isSinglePlayer) {
                this.client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
            } else {
                this.client.disconnect();
            }
            this.client.openScreen(new TitleScreen());
        }));
    }
}
