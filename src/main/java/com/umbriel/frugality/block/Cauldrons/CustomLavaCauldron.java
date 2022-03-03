package com.umbriel.frugality.block.Cauldrons;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class CustomLavaCauldron extends AbstractCustomCauldron {
    public CustomLavaCauldron(BlockBehaviour.Properties properties) {
        super(properties, null, CustomCauldronInteraction.LAVA);
    }

    protected double getContentHeight(BlockState state) {
        return 0.9375D;
    }

    public boolean isFull(BlockState state) {
        return true;
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (this.isEntityInsideContent(state, pos, entity)) {
            entity.lavaHurt();
        }

    }
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return 3;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getMaterial().equals(Material.WOOD);
    }

    // Random Chance for Melting
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
    }
}
