package vortex.client.vortexclient.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vortex.client.vortexclient.module.ModuleManager;
import vortex.client.vortexclient.module.modules.player.FakeCreative;
import vortex.client.vortexclient.util.FakeOpManager;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    
    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof ChatMessageC2SPacket) {
            ChatMessageC2SPacket chatPacket = (ChatMessageC2SPacket) packet;
            String message = chatPacket.getChatMessage();

            if (FakeOpManager.isFakingOp() && message.equalsIgnoreCase("/gm 1")) {
                ci.cancel(); // Блокируем отправку команды на сервер
                
                FakeCreative fakeCreative = ModuleManager.INSTANCE.getModule(FakeCreative.class);
                if (fakeCreative != null) {
                    fakeCreative.setEnabled(true);
                }
                
                MinecraftClient.getInstance().player.sendMessage(new LiteralText("Your game mode has been updated to Creative Mode"), false);
            }
        }
    }
}
