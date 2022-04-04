package com.umbriel.frugality.block;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.umbriel.frugality.block.Cauldrons.CustomCauldron;
import com.umbriel.frugality.block.Cauldrons.CustomLayeredCauldron;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import static com.umbriel.frugality.util.CustomCauldronHelper.getWaterCauldron;

public class TreeTapBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 16.0D),
            Direction.SOUTH, Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 10.0D),
            Direction.WEST, Block.box(6.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D),
            Direction.EAST, Block.box(0.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D)));
    private static final VoxelShape FAUCET = Block.box(6.0D, 4.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TreeTapBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        return getShape(blockState);
    }

    public static VoxelShape getShape(BlockState blockState) {
        return Shapes.or(AABBS.get(blockState.getValue(FACING)), FAUCET);
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
        if(canTick(state, level, pos) && ran.nextFloat() < 0.12f){
            level.addParticle(ParticleTypes.DRIPPING_DRIPSTONE_WATER, pos.getX() + 0.5, (double) ((float) (pos.getY() + 1) - 0.6875F) - 0.0625D, pos.getZ() + 0.5, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public boolean canTick(BlockState state, Level level, BlockPos pos){
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        return level.getBlockState(blockpos).getBlock() == Blocks.OAK_LOG && (isLayered(getCauldronBlock(level, pos)) || isNormal(getCauldronBlock(level, pos)));
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if(canTick(state, worldIn, pos)) {
            IntegerProperty fluidLevel = LayeredCauldronBlock.LEVEL;
            if (getCauldronBlock(worldIn, pos).getBlock() instanceof CustomLayeredCauldron) {
                fluidLevel = CustomLayeredCauldron.LEVEL;
            }
            float ranValue = random.nextFloat();
            System.out.println(ranValue);
            if (ranValue < 0.90F) {
                if(isLayered(getCauldronBlock(worldIn, pos))){
                    System.out.println(isLayered(getCauldronBlock(worldIn, pos)));
                    if (getCauldronBlock(worldIn, pos).getValue(fluidLevel) < 3) {
                        worldIn.setBlockAndUpdate(pos.below(), getCauldronBlock(worldIn, pos).setValue(fluidLevel, getCauldronBlock(worldIn, pos).getValue(fluidLevel) + 1));
                    }
                }
                else if(isNormal(getCauldronBlock(worldIn, pos))){
                    worldIn.setBlock(pos.below(), getWaterCauldron(getCauldronBlock(worldIn, pos)), 0);
                }
                worldIn.levelEvent(1047, pos.below(), 0);
            }
            if(ranValue < 0.01F){
                Direction direction = state.getValue(FACING);
                BlockPos blockpos = pos.relative(direction.getOpposite());
                worldIn.setBlock(blockpos, Blocks.STRIPPED_OAK_LOG.defaultBlockState(), 0);
            }
        }
    }

    private BlockState getCauldronBlock(Level level, BlockPos pos){
        return level.getBlockState(pos.below());
    }

    private boolean isLayered(BlockState state){
        return state.getBlock() instanceof CustomLayeredCauldron || state.getBlock() instanceof LayeredCauldronBlock;
    }

    private boolean isNormal(BlockState state){
        return state.getBlock() instanceof CauldronBlock || state.getBlock() instanceof CustomCauldron;
    }

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
