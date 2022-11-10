package com.umbriel.frugality.mixin;

import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FireBlock.class)
public class FireMixin  {

    //Burning Logs
    @Redirect(
            method = "tryCatchFire",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"
            )
    )
    private boolean redirectTryCatchFire(Level level, BlockPos pos, boolean isMoving) {
        if(level.getBlockState(pos).is(BlockTags.LOGS_THAT_BURN )){
            level.setBlock(pos, FrugalItems.CHARRED_LOG.get().defaultBlockState(), 3);
            System.out.println("working");
        }
        return false;
    }
}
