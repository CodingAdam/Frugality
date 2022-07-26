package com.umbriel.frugality.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;


import java.util.Random;

import static com.umbriel.frugality.init.FrugalItems.SLOW_FIRE;

@SuppressWarnings("deprecation")
public class MudBlock extends Block {

    public MudBlock(BlockBehaviour.Properties properties) {
        super(Block.Properties.of(Material.DIRT)
                .strength(0.5F)
                .sound(SoundType.NETHER_WART)
        );
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    // Random Chance
    @Override
    @SuppressWarnings("NullableProblems")
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        BlockState belowBlock = worldIn.getBlockState(pos.below());
        if(CampfireBlock.isLitCampfire(belowBlock)  || belowBlock == Blocks.FIRE.defaultBlockState() || belowBlock == Blocks.SOUL_FIRE.defaultBlockState() || belowBlock == SLOW_FIRE.get().defaultBlockState()){
            worldIn.setBlock(pos, Blocks.CLAY.defaultBlockState(), 3);
        }

    }

//            @Override
//            public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
//                BlockState plant = plantable.getPlant(world, pos.relative(facing));
//                net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.relative(facing));
//
//                if (plant.getBlock() == Blocks.SUGAR_CANE && this == Blocks.SUGAR_CANE)
//                    return true;
//
//                if (plantable instanceof BushBlock && ((BushBlock)plantable).isFertile(state, world, pos))
//                    return true;
//                else if (net.minecraftforge.common.PlantType.PLAINS.equals(type)) {
//                    return this.getBlock() == Blocks.GRASS_BLOCK || net.minecraftforge.common.Tags.Blocks.DIRT.contains(this) || this.getBlock() == Blocks.FARMLAND;
//                }  else if (net.minecraftforge.common.PlantType.BEACH.equals(type)) {
//                    boolean hasWater = false;
//                    for (Direction face : Direction.Plane.HORIZONTAL) {
//                        BlockState blockState = world.getBlockState(pos.relative(face));
//                        net.minecraft.fluid.FluidState fluidState = world.getFluidState(pos.relative(face));
//                        hasWater |= blockState.is(Blocks.FROSTED_ICE);
//                        hasWater |= fluidState.is(net.minecraft.tags.FluidTags.WATER);
//                        if (hasWater)
//                            break; //No point continuing.
//                    }
//                    return hasWater;
//                }
//                return false;
//            }
}
