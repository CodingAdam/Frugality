package com.umbriel.frugality.block.Cauldrons;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.function.Predicate;
import static com.umbriel.frugality.util.CustomCauldronHandler.getWaterCauldron;

public class CustomSnowCauldron extends CustomLayeredCauldron {
    public CustomSnowCauldron(BlockBehaviour.Properties p_154290_, Predicate<Biome.Precipitation> p_154291_, Map<Item, CustomCauldronInteraction> p_154292_) {
        super(p_154290_, p_154291_, p_154292_);
    }

    protected void handleEntityOnFireInside(BlockState state, Level p_154295_, BlockPos p_154296_) {
        lowerFillLevel(getWaterCauldron(state).setValue(LEVEL, state.getValue(LEVEL)), p_154295_, p_154296_);
    }
}
