package com.umbriel.frugality.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import static com.umbriel.frugality.init.FrugalItems.*;

public class CustomCauldronHelper {
    public static BlockState getWaterCauldron(BlockState state){
        if(compareNormalBlocks(SNOW_WOODEN_CAULDRON, state)
                || compareNormalBlocks(WOODEN_CAULDRON, state)
                || compareNormalBlocks(WATER_WOODEN_CAULDRON, state)){
            return getBlockState(WATER_WOODEN_CAULDRON);
        }
        if(compareNormalBlocks(SNOW_STONE_CAULDRON, state)
                || compareNormalBlocks(STONE_CAULDRON, state)
                || compareNormalBlocks(WATER_STONE_CAULDRON, state)){
            return getBlockState(WATER_STONE_CAULDRON);
        }
        return Blocks.WATER_CAULDRON.defaultBlockState();
    }
    public static BlockState getSnowCauldron(BlockState state){
        if(state.equals(getBlockState(WOODEN_CAULDRON))
                || compareNormalBlocks(SNOW_WOODEN_CAULDRON, state)){
            return getBlockState(SNOW_WOODEN_CAULDRON);
        }
        if(state.equals(getBlockState(STONE_CAULDRON))
                || compareNormalBlocks(SNOW_STONE_CAULDRON, state)){
            return getBlockState(SNOW_STONE_CAULDRON);
        }
        return Blocks.POWDER_SNOW_CAULDRON.defaultBlockState();
    }
    public static BlockState getCauldron(BlockState state){
        if(compareNormalBlocks(WATER_WOODEN_CAULDRON, state)
                || compareNormalBlocks(SNOW_WOODEN_CAULDRON, state)
                || compareNormalBlocks(LAVA_WOODEN_CAULDRON, state)){
            return getBlockState(WOODEN_CAULDRON);
        }
        if(compareNormalBlocks(WATER_STONE_CAULDRON, state)
                || compareNormalBlocks(SNOW_STONE_CAULDRON, state)
                || compareNormalBlocks(LAVA_STONE_CAULDRON, state)){
            return getBlockState(STONE_CAULDRON);
        }
        return Blocks.CAULDRON.defaultBlockState();
    }
    public static BlockState getLavaCauldron(BlockState state){
        if(state.equals(getBlockState(WOODEN_CAULDRON))
                || compareNormalBlocks(LAVA_WOODEN_CAULDRON, state)){
            return getBlockState(LAVA_WOODEN_CAULDRON);
        }
        if(state.equals(getBlockState(STONE_CAULDRON))
                || compareNormalBlocks(LAVA_STONE_CAULDRON, state)){
            return getBlockState(LAVA_STONE_CAULDRON);
        }
        return Blocks.LAVA_CAULDRON.defaultBlockState();
    }

    public static BlockState getBlockState(RegistryObject<Block> Block){
        return Block.get().defaultBlockState();
    }

    public static boolean compareNormalBlocks(RegistryObject<Block> Block, BlockState state){
        return state.getBlock().equals(getBlockState(Block).getBlock());
    }
}
