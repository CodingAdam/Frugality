package com.umbriel.frugality.block.workstation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.umbriel.frugality.block.entity.CrushingBlockEntity;
import com.umbriel.frugality.init.ModBlockEntities;
import com.umbriel.frugality.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CrushingBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    // Bottom
    private static final Map<Direction, VoxelShape> AABBS_B = Maps.newEnumMap(ImmutableMap.of(
            Direction.WEST, Block.box(0.0D, 0.0D, 3.0D, 16.0D, 2.0D, 13.0D),
            Direction.EAST, Block.box(0.0D, 0.0D, 3.0D, 16.0D, 2.0D, 13.0D),
            Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 2.0D, 16.0D),
            Direction.SOUTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 2.0D, 16.0D)));
    // Middle
    private static final Map<Direction, VoxelShape> AABBS_M = Maps.newEnumMap(ImmutableMap.of(
            Direction.WEST, Block.box(1.0D, 2.0D, 4.0D, 15.0D, 4.0D, 12.0D),
            Direction.EAST, Block.box(1.0D, 2.0D, 4.0D, 15.0D, 4.0D, 12.0D),
            Direction.NORTH, Block.box(4.0D, 2.0D, 1.0D, 12.0D, 4.0D, 15.0D),
            Direction.SOUTH, Block.box(4.0D, 2.0D, 1.0D, 12.0D, 4.0D, 15.0D)));
    // Top
    private static final Map<Direction, VoxelShape> AABBS_T = Maps.newEnumMap(ImmutableMap.of(
            Direction.WEST, Block.box(0.0D, 4.0D, 3.0D, 16.0D, 8.0D, 13.0D),
            Direction.EAST, Block.box(0.0D, 4.0D, 3.0D, 16.0D, 8.0D, 13.0D),
            Direction.NORTH, Block.box(3.0D, 4.0D, 0.0D, 13.0D, 8.0D, 16.0D),
            Direction.SOUTH, Block.box(3.0D, 4.0D, 0.0D, 13.0D, 8.0D, 16.0D)));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CrushingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.CRUSHING_BLOCK.get().create(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof CrushingBlockEntity){
            CrushingBlockEntity crushingBlockEntity = (CrushingBlockEntity) blockEntity;
            ItemStack heldItem = player.getItemInHand(hand);
            ItemStack offHandItem = player.getOffhandItem();

            if(crushingBlockEntity.isEmpty()){
               // if(!offHandItem.isEmpty()){
                //    if(hand.equals(InteractionHand.MAIN_HAND) && Tags)
               // }
                if(heldItem.isEmpty()){
                    return InteractionResult.PASS;
                } else if(crushingBlockEntity.addItem(player.getAbilities().instabuild ? heldItem.copy() : heldItem)){
                    level.playSound(null, pos, SoundEvents.DEEPSLATE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
            } else if(!heldItem.isEmpty()){
                ItemStack crushingItem = crushingBlockEntity.getStoredItem().copy();
                if(crushingBlockEntity.processItem(heldItem, player)){
                    ParticleHelper.spawnItemParticles(level, 1.0D, 0.1F, pos, crushingItem, 2);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;
            } else if(hand.equals(InteractionHand.MAIN_HAND)){
                if(!player.isCreative()){
                    if(!player.getInventory().add(crushingBlockEntity.removeItem())){
                        player.drop(crushingBlockEntity.removeItem(), false);
                    }
                } else{
                    crushingBlockEntity.removeItem();
                }
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof CrushingBlockEntity){
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ((CrushingBlockEntity) blockEntity).getStoredItem());
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
        return getShape(blockState);
    }

    public static VoxelShape getShape(BlockState blockState) {
        return Shapes.or(AABBS_B.get(blockState.getValue(FACING)), AABBS_M.get(blockState.getValue(FACING)), AABBS_T.get(blockState.getValue(FACING)));
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState stater, LevelAccessor accessor, BlockPos pos, BlockPos poser) {
        if (state.getValue(WATERLOGGED)) {
            accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
        }
        return !state.canSurvive(accessor, pos) ? Blocks.AIR.defaultBlockState() : state;
    }

    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        Direction direction = Direction.DOWN;
        BlockPos blockpos = pos.below();
        BlockState blockstate = reader.getBlockState(blockpos);
        return blockstate.isFaceSturdy(reader, blockpos, direction);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getClockWise());
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48814_) {
        p_48814_.add(FACING, WATERLOGGED);
    }


}
