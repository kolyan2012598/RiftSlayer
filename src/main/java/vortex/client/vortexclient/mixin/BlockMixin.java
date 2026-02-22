package vortex.client.vortexclient.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vortex.client.vortexclient.module.modules.render.XRay;

@Mixin(Block.class)
public abstract class BlockMixin {

    /**
     * Делает ненужные блоки прозрачными
     */
    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void onShouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> info) {
        if (XRay.isXrayEnabled()) {
            info.setReturnValue(XRay.blocks.contains(state.getBlock()));
        }
    }

    /**
     * Убирает тени, чтобы руды светились в темноте
     */
    private void onGetAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        if (XRay.isXrayEnabled()) {
            info.setReturnValue(1.0f);
        }
    }

    /**
     * Позволяет видеть сущностей (игроков/мобов) сквозь блоки
     */
    @Inject(method = "isTranslucent", at = @At("HEAD"), cancellable = true)
    private void onIsTranslucent(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (XRay.isXrayEnabled()) {
            info.setReturnValue(true);
        }
    }
}
