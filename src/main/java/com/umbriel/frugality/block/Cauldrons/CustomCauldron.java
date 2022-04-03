package com.umbriel.frugality.block.Cauldrons;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import static com.umbriel.frugality.util.CustomCauldronHelper.*;

public class CustomCauldron extends AbstractCustomCauldron {
    private static final float RAIN_FILL_CHANCE = 0.05F;
    private static final float POWDER_SNOW_FILL_CHANCE = 0.1F;

    public CustomCauldron(BlockBehaviour.Properties properties) {
        super(properties, null, CustomCauldronInteraction.EMPTY);
    }

    public boolean isFull(BlockState state) {
        return false;
    }

    public static boolean shouldHandlePrecipitation(Level level, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return level.getRandom().nextFloat() < 0.05F;
        } else if (precipitation == Biome.Precipitation.SNOW) {
            return level.getRandom().nextFloat() < 0.1F;
        } else {
            return false;
        }
    }

    public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        if (shouldHandlePrecipitation(level, precipitation)) {
            if (precipitation == Biome.Precipitation.RAIN) {
                level.setBlockAndUpdate(pos, getWaterCauldron(state));
                level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            } else if (precipitation == Biome.Precipitation.SNOW) {
                level.setBlockAndUpdate(pos, getSnowCauldron(state));
                level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            }

        }
    }

    public boolean canReceiveStalactiteDrip(Fluid fluid) {
        return true;
    }

    protected void receiveStalactiteDrip(BlockState state, Level level, BlockPos pos, Fluid fluid) {
        if (fluid == Fluids.WATER) {
            level.setBlockAndUpdate(pos, getWaterCauldron(state));
            level.levelEvent(1047, pos, 0);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        } else if (fluid == Fluids.LAVA) {
            level.setBlockAndUpdate(pos, getLavaCauldron(state));
            level.levelEvent(1046, pos, 0);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }

    }



}
