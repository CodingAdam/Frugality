package com.umbriel.frugality.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Random;

public class MelterBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape MAGNIFIER = Block.box(1.0D, 4.0D, 1.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape MIRROR = Block.box(0.0D, 3.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public MelterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        return getShape(blockState);
    }

    public static VoxelShape getShape(BlockState blockState) {
        return Shapes.or(MAGNIFIER, MIRROR);
    }

    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = reader.getBlockState(blockpos);
        return blockstate.isFaceSturdy(reader, blockpos, direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState blockstate = this.defaultBlockState();
        boolean flag = fluidstate.getType() == Fluids.WATER;
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for(Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (blockstate.canSurvive(levelreader, blockpos)) {
                    return blockstate.setValue(WATERLOGGED, Boolean.valueOf(flag));
                }
            }
        }

        return null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random ran) {

    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public boolean canTick(BlockState state, Level level, BlockPos pos){
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        return false;
    }

//    @Override
//    @SuppressWarnings("NullableProblems")
//    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
//        List<ThermalFluidRecipe> recipeFluid = worldIn.getRecipeManager().getAllRecipesFor(ModRecipes.thermalRecipeType);
//        List<ThermalItemRecipe> recipeItem = worldIn.getRecipeManager().getAllRecipesFor(ModRecipes.thermalItemRecipeType);
//
//        ItemStack itemStack = worldIn.getBlockState(pos.below()).getBlock().asItem().getDefaultInstance();
//        ThermalFluidRecipe realFluidRecipe = findFluidRecipe(recipeFluid, itemStack);
//        ThermalItemRecipe realItemRecipe = findItemRecipe(recipeItem, itemStack);
//
//        System.out.println("Random Tick");
//
//        float ran = random.nextFloat();
//        if(realFluidRecipe != null){
//            System.out.println("Real Fluid");
//            System.out.println(ran);
//            if(ran < 10F / (float)realFluidRecipe.getStoneType()){
//                Fluid fluid = realFluidRecipe.getResult().getFluid();
//                worldIn.setBlock(pos.below(), fluid.defaultFluidState().createLegacyBlock(), 11);
//            }
//        }
//        if(realItemRecipe != null){
//            System.out.println("Real Item");
//            System.out.println(ran);
//            if(ran < 10F / (float)realItemRecipe.getStoneType()) {
//                Block block = Block.byItem(realItemRecipe.getInput().getItems()[0].getItem());
//                if (block != Blocks.AIR) {
//                    worldIn.setBlock(pos.below(), block.defaultBlockState(), 1);
//                } else {
//                    List<ItemStack> items = realItemRecipe.rollOutputs();
//                    for (ItemStack item : items) {
//                        ItemEntity spawnedItem = new ItemEntity(worldIn, (double) pos.below().getX(), (double) pos.below().getX(), (double) pos.below().getX(), item);
//                        worldIn.addFreshEntity(spawnedItem);
//                    }
//                }
//            }
//        }
//    }
//
//    public static ThermalItemRecipe findItemRecipe(List<ThermalItemRecipe> recipes, ItemStack itemStack){
//        for (final ThermalItemRecipe recipe : recipes) {
//            for(int i = 0; i < recipe.getInput().getItems().length; i++){
//                if (recipe.getInput().getItems()[i] == itemStack) {
//                    return recipe;
//                }
//            }
//        }
//        return null;
//    }
//
//    public static ThermalFluidRecipe findFluidRecipe(List<ThermalFluidRecipe> recipes, ItemStack itemStack) {
//        for (final ThermalFluidRecipe recipe : recipes) {
//            for(int i = 0; i < recipe.getInput().getItems().length; i++){
//                if (recipe.getInput().getItems()[i] == itemStack) {
//                    return recipe;
//                }
//            }
//        }
//        return null;
//    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState stater, LevelAccessor accessor, BlockPos pos, BlockPos poser) {
        if (state.getValue(WATERLOGGED)) {
            accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
        }
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(accessor, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    public BlockState rotate(BlockState p_58140_, Rotation p_58141_) {
        return p_58140_.setValue(FACING, p_58141_.rotate(p_58140_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_58137_, Mirror p_58138_) {
        return p_58137_.rotate(p_58138_.getRotation(p_58137_.getValue(FACING)));
    }

    public FluidState getFluidState(BlockState p_152158_) {
        return p_152158_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_152158_);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_58150_) {
        p_58150_.add(FACING, WATERLOGGED);
    }
}
