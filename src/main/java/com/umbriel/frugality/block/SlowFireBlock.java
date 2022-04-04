package com.umbriel.frugality.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.umbriel.frugality.init.ModItems.*;


@SuppressWarnings({"NullableProblems", "deprecation"})
public class SlowFireBlock extends AbstractSlowFireBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((p_199776_0_) -> p_199776_0_.getKey() != Direction.DOWN).collect(Util.toMap());
    private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    private final Map<BlockState, VoxelShape> shapesCache;
    private final Object2IntMap<Block> flameOdds = new Object2IntOpenHashMap<>();
    private final Object2IntMap<Block> burnOdds = new Object2IntOpenHashMap<>();

    public SlowFireBlock(BlockBehaviour.Properties behaviour) {
        super(behaviour, 1.0F);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(NORTH, Boolean.FALSE).setValue(EAST,
                Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(UP, Boolean.FALSE));
        this.shapesCache = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().filter((p_242674_0_) -> {
            return p_242674_0_.getValue(AGE) == 0;
        }).collect(Collectors.toMap(Function.identity(), SlowFireBlock::calculateShape)));
    }


    private static VoxelShape calculateShape(BlockState p_242673_0_) {
        VoxelShape voxelshape = Shapes.empty();
        if (p_242673_0_.getValue(UP)) {
            voxelshape = UP_AABB;
        }

        if (p_242673_0_.getValue(NORTH)) {
            voxelshape = Shapes.or(voxelshape, NORTH_AABB);
        }

        if (p_242673_0_.getValue(SOUTH)) {
            voxelshape = Shapes.or(voxelshape, SOUTH_AABB);
        }

        if (p_242673_0_.getValue(EAST)) {
            voxelshape = Shapes.or(voxelshape, EAST_AABB);
        }

        if (p_242673_0_.getValue(WEST)) {
            voxelshape = Shapes.or(voxelshape, WEST_AABB);
        }

        return voxelshape.isEmpty() ? DOWN_AABB : voxelshape;
    }

    public BlockState updateShape(BlockState p_53458_, Direction p_53459_, BlockState p_53460_, LevelAccessor p_53461_, BlockPos p_53462_, BlockPos p_53463_) {
        return this.canSurvive(p_53458_, p_53461_, p_53462_) ? this.getStateWithAge(p_53461_, p_53462_, p_53458_.getValue(AGE)) : Blocks.AIR.defaultBlockState();
    }

    public VoxelShape getShape(BlockState p_53474_, BlockGetter p_53475_, BlockPos p_53476_, CollisionContext p_53477_) {
        return this.shapesCache.get(p_53474_.setValue(AGE, Integer.valueOf(0)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_53427_) {
        return this.getStateForPlacement(p_53427_.getLevel(), p_53427_.getClickedPos());
    }

    public BlockState getStateForPlacement(BlockGetter p_196448_1_, BlockPos p_196448_2_) {
        BlockPos blockpos = p_196448_2_.below();
        BlockState blockstate = p_196448_1_.getBlockState(blockpos);
        if (!this.canCatchFire(p_196448_1_, p_196448_2_, Direction.UP) && !blockstate.isFaceSturdy(p_196448_1_, blockpos, Direction.UP)) {
            BlockState blockstate1 = this.defaultBlockState();

            for(Direction direction : Direction.values()) {
                BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(direction);
                if (booleanproperty != null) {
                    blockstate1 = blockstate1.setValue(booleanproperty, this.canCatchFire(p_196448_1_, p_196448_2_.relative(direction), direction.getOpposite()));
                }
            }

            return blockstate1;
        } else {
            return this.defaultBlockState();
        }
    }

    public boolean canSurvive(BlockState p_53454_, LevelReader p_53455_, BlockPos p_53456_) {
        BlockPos blockpos = p_53456_.below();
        return p_53455_.getBlockState(blockpos).isFaceSturdy(p_53455_, blockpos, Direction.UP) || this.isValidFireLocation(p_53455_, p_53456_);
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random ran) {
        world.scheduleTick(pos, this, getFireTickDelay(world.random));
        if (world.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            if (!state.canSurvive(world, pos)) {
                if(world.getBlockState(pos).is(BlockTags.LOGS_THAT_BURN)){
                    world.setBlock(pos, CHARRED_LOG.get().defaultBlockState(), 3);
                }
                else{
                    world.removeBlock(pos, false);
                }
            }

            BlockState blockstate = world.getBlockState(pos.below());
            boolean flag = blockstate.isFireSource(world, pos, Direction.UP);
            int i = state.getValue(AGE);
            if (!flag && world.isRaining() && this.isNearRain(world, pos) && ran.nextFloat() < 0.2F + (float)i * 0.03F) {
                if(world.getBlockState(pos).is((BlockTags.LOGS_THAT_BURN))){
                    world.setBlock(pos, CHARRED_LOG.get().defaultBlockState(), 3);
                }
                else{
                    world.removeBlock(pos, false);
                }
            } else {
                int j = Math.min(15, i + ran.nextInt(3) / 2);
                if (i != j) {
                    state = state.setValue(AGE, Integer.valueOf(j));
                    world.setBlock(pos, state, 4);
                }

                if (!flag) {
                    if (!this.isValidFireLocation(world, pos)) {
                        BlockPos blockpos = pos.below();
                        if (!world.getBlockState(blockpos).isFaceSturdy(world, blockpos, Direction.UP) || i > 3) {
                            if(world.getBlockState(pos).is(BlockTags.LOGS_THAT_BURN)){
                                world.setBlock(pos, CHARRED_LOG.get().defaultBlockState(), 3);
                            }
                            else{
                                world.removeBlock(pos, false);
                            }
                        }

                        return;
                    }

                    if (i == 15 && ran.nextInt(4) == 0 && !this.canCatchFire(world, pos.below(), Direction.UP)) {
                        if(world.getBlockState(pos).is(BlockTags.LOGS_THAT_BURN)){
                            world.setBlock(pos, CHARRED_LOG.get().defaultBlockState(), 3);
                        }
                        else{
                            world.removeBlock(pos, false);
                        }
                        return;
                    }
                }

                boolean flag1 = world.isHumidAt(pos);
                int k = flag1 ? -50 : 0;
                this.tryCatchFire(world, pos.east(), 300 + k, ran, i, Direction.WEST);
                this.tryCatchFire(world, pos.west(), 300 + k, ran, i, Direction.EAST);
                this.tryCatchFire(world, pos.below(), 250 + k, ran, i, Direction.UP);
                this.tryCatchFire(world, pos.above(), 250 + k, ran, i, Direction.DOWN);
                this.tryCatchFire(world, pos.north(), 300 + k, ran, i, Direction.SOUTH);
                this.tryCatchFire(world, pos.south(), 300 + k, ran, i, Direction.NORTH);
                BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

                for(int l = -1; l <= 1; ++l) {
                    for(int i1 = -1; i1 <= 1; ++i1) {
                        for(int j1 = -1; j1 <= 4; ++j1) {
                            if (l != 0 || j1 != 0 || i1 != 0) {
                                int k1 = 100;
                                if (j1 > 1) {
                                    k1 += (j1 - 1) * 100;
                                }

                                blockpos$mutable.setWithOffset(pos, l, j1, i1);
                                int l1 = this.getFireOdds(world, blockpos$mutable);
                                if (l1 > 0) {
                                    int i2 = (l1 + 40 + world.getDifficulty().getId() * 7) / (i + 30);
                                    if (flag1) {
                                        i2 /= 2;
                                    }

                                    if (i2 > 0 && ran.nextInt(k1) <= i2 && (!world.isRaining() || !this.isNearRain(world, blockpos$mutable))) {
                                        int j2 = Math.min(15, i + ran.nextInt(5) / 4);
                                        world.setBlock(blockpos$mutable, this.getStateWithAge(world, blockpos$mutable, j2), 3);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    protected boolean isNearRain(Level p_53429_, BlockPos p_53430_) {
        return p_53429_.isRainingAt(p_53430_) || p_53429_.isRainingAt(p_53430_.west()) || p_53429_.isRainingAt(p_53430_.east()) || p_53429_.isRainingAt(p_53430_.north()) || p_53429_.isRainingAt(p_53430_.south());
    }

    @Deprecated //Forge: Use IForgeBlockState.getFlammability, Public for default implementation only.
    public int getBurnOdd(BlockState p_220274_1_) {
        return p_220274_1_.hasProperty(BlockStateProperties.WATERLOGGED) && p_220274_1_.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.burnOdds.getInt(p_220274_1_.getBlock());
    }

    @Deprecated //Forge: Use IForgeBlockState.getFireSpreadSpeed
    public int getFlameOdds(BlockState p_220275_1_) {
        return p_220275_1_.hasProperty(BlockStateProperties.WATERLOGGED) && p_220275_1_.getValue(BlockStateProperties.WATERLOGGED) ? 0 : this.flameOdds.getInt(p_220275_1_.getBlock());
    }

    private void tryCatchFire(Level world, BlockPos pos, int p_176536_3_, Random p_176536_4_, int p_176536_5_, Direction face) {
        int i = world.getBlockState(pos).getFlammability(world, pos, face);
        if (p_176536_4_.nextInt(p_176536_3_) < i) {
            BlockState blockstate = world.getBlockState(pos);
            if (p_176536_4_.nextInt(p_176536_5_ + 10) < 5 && !world.isRainingAt(pos)) {
                int j = Math.min(p_176536_5_ + p_176536_4_.nextInt(5) / 4, 15);
                world.setBlock(pos, this.getStateWithAge(world, pos, j), 3);
            } else {
                if(world.getBlockState(pos).is(BlockTags.LOGS_THAT_BURN)){
                    world.setBlock(pos, CHARRED_LOG.get().defaultBlockState(), 3);
                }
                else{
                    world.removeBlock(pos, false);
                }

            }

            blockstate.onCaughtFire(world, pos, face, null);
        }

    }

    private BlockState getStateWithAge(LevelAccessor p_235494_1_, BlockPos p_235494_2_, int p_235494_3_) {
        BlockState blockstate = getState(p_235494_1_,p_235494_2_);
        return blockstate.is(SLOW_FIRE.get()) ? blockstate.setValue(AGE, p_235494_3_) : blockstate;
    }


    private boolean isValidFireLocation(BlockGetter p_196447_1_, BlockPos p_196447_2_) {
        for(Direction direction : Direction.values()) {
            if (this.canCatchFire(p_196447_1_, p_196447_2_.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }

    private int getFireOdds(LevelReader p_176538_1_, BlockPos p_176538_2_) {
        if (!p_176538_1_.isEmptyBlock(p_176538_2_)) {
            return 0;
        } else {
            int i = 0;

            for(Direction direction : Direction.values()) {
                BlockState blockstate = p_176538_1_.getBlockState(p_176538_2_.relative(direction));
                i = Math.max(blockstate.getFireSpreadSpeed(p_176538_1_, p_176538_2_.relative(direction), direction.getOpposite()), i);
            }

            return i;
        }
    }

    @Deprecated //Forge: Use canCatchFire with more context
    protected boolean canBurn(BlockState p_196446_1_) {
        return this.getFlameOdds(p_196446_1_) > 0;
    }

    public void onPlace(BlockState p_53479_, Level p_53480_, BlockPos p_53481_, BlockState p_53482_, boolean p_53483_) {
        super.onPlace(p_53479_, p_53480_, p_53481_, p_53482_, p_53483_);
        p_53480_.scheduleTick(p_53481_, this, getFireTickDelay(p_53480_.random));
    }

    private static int getFireTickDelay(Random p_235495_0_) {
        return 30 + p_235495_0_.nextInt(10);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_53465_) {
        p_53465_.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
    }

    private void setFlammable(Block p_180686_1_, int p_180686_2_, int p_180686_3_) {
        if (p_180686_1_ == Blocks.AIR) throw new IllegalArgumentException("Tried to set air on fire... This is bad.");
        this.flameOdds.put(p_180686_1_, p_180686_2_);
        this.burnOdds.put(p_180686_1_, p_180686_3_);
    }

    /**
     * Side sensitive version that calls the block function.
     *
     * @param world The current world
     * @param pos Block position
     * @param face The side the fire is coming from
     * @return True if the face can catch fire.
     */
    public boolean canCatchFire(BlockGetter world, BlockPos pos, Direction face) {
        return world.getBlockState(pos).isFlammable(world, pos, face);
    }

    public static void bootStrap() {
        SlowFireBlock fireblock = (SlowFireBlock)SLOW_FIRE.get();
        fireblock.setFlammable(Blocks.OAK_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_PLANKS, 5, 20);
        fireblock.setFlammable(Blocks.OAK_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_SLAB, 5, 20);
        fireblock.setFlammable(Blocks.OAK_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_FENCE_GATE, 5, 20);
        fireblock.setFlammable(Blocks.OAK_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_FENCE, 5, 20);
        fireblock.setFlammable(Blocks.OAK_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.BIRCH_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.SPRUCE_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.JUNGLE_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.ACACIA_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.DARK_OAK_STAIRS, 5, 20);
        fireblock.setFlammable(Blocks.OAK_LOG, 5, 5);
        fireblock.setFlammable(Blocks.SPRUCE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.BIRCH_LOG, 5, 5);
        fireblock.setFlammable(Blocks.JUNGLE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.ACACIA_LOG, 5, 5);
        fireblock.setFlammable(Blocks.DARK_OAK_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_OAK_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_OAK_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.OAK_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.SPRUCE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.BIRCH_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.JUNGLE_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.ACACIA_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.DARK_OAK_WOOD, 5, 5);
        fireblock.setFlammable(Blocks.OAK_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.SPRUCE_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.BIRCH_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.JUNGLE_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.ACACIA_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.DARK_OAK_LEAVES, 30, 60);
        fireblock.setFlammable(Blocks.BOOKSHELF, 30, 20);
        fireblock.setFlammable(Blocks.TNT, 15, 100);
        fireblock.setFlammable(Blocks.GRASS, 60, 100);
        fireblock.setFlammable(Blocks.FERN, 60, 100);
        fireblock.setFlammable(Blocks.DEAD_BUSH, 60, 100);
        fireblock.setFlammable(Blocks.SUNFLOWER, 60, 100);
        fireblock.setFlammable(Blocks.LILAC, 60, 100);
        fireblock.setFlammable(Blocks.ROSE_BUSH, 60, 100);
        fireblock.setFlammable(Blocks.PEONY, 60, 100);
        fireblock.setFlammable(Blocks.TALL_GRASS, 60, 100);
        fireblock.setFlammable(Blocks.LARGE_FERN, 60, 100);
        fireblock.setFlammable(Blocks.DANDELION, 60, 100);
        fireblock.setFlammable(Blocks.POPPY, 60, 100);
        fireblock.setFlammable(Blocks.BLUE_ORCHID, 60, 100);
        fireblock.setFlammable(Blocks.ALLIUM, 60, 100);
        fireblock.setFlammable(Blocks.AZURE_BLUET, 60, 100);
        fireblock.setFlammable(Blocks.RED_TULIP, 60, 100);
        fireblock.setFlammable(Blocks.ORANGE_TULIP, 60, 100);
        fireblock.setFlammable(Blocks.WHITE_TULIP, 60, 100);
        fireblock.setFlammable(Blocks.PINK_TULIP, 60, 100);
        fireblock.setFlammable(Blocks.OXEYE_DAISY, 60, 100);
        fireblock.setFlammable(Blocks.CORNFLOWER, 60, 100);
        fireblock.setFlammable(Blocks.LILY_OF_THE_VALLEY, 60, 100);
        fireblock.setFlammable(Blocks.WITHER_ROSE, 60, 100);
        fireblock.setFlammable(Blocks.WHITE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.ORANGE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.MAGENTA_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.LIGHT_BLUE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.YELLOW_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.LIME_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.PINK_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.GRAY_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.LIGHT_GRAY_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.CYAN_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.PURPLE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.BLUE_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.BROWN_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.GREEN_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.RED_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.BLACK_WOOL, 30, 60);
        fireblock.setFlammable(Blocks.VINE, 15, 100);
        fireblock.setFlammable(Blocks.COAL_BLOCK, 5, 5);
        fireblock.setFlammable(Blocks.HAY_BLOCK, 60, 20);
        fireblock.setFlammable(Blocks.TARGET, 15, 20);
        fireblock.setFlammable(Blocks.WHITE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.ORANGE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.MAGENTA_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.LIGHT_BLUE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.YELLOW_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.LIME_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.PINK_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.GRAY_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.LIGHT_GRAY_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.CYAN_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.PURPLE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.BLUE_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.BROWN_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.GREEN_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.RED_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.BLACK_CARPET, 60, 20);
        fireblock.setFlammable(Blocks.DRIED_KELP_BLOCK, 30, 60);
        fireblock.setFlammable(Blocks.BAMBOO, 60, 60);
        fireblock.setFlammable(Blocks.SCAFFOLDING, 60, 60);
        fireblock.setFlammable(Blocks.LECTERN, 30, 20);
        fireblock.setFlammable(Blocks.COMPOSTER, 5, 20);
        fireblock.setFlammable(Blocks.SWEET_BERRY_BUSH, 60, 100);
        fireblock.setFlammable(Blocks.BEEHIVE, 5, 20);
        fireblock.setFlammable(Blocks.BEE_NEST, 30, 20);
        fireblock.setFlammable(WOODEN_CAULDRON.get(), 5, 20);
        fireblock.setFlammable(LAVA_WOODEN_CAULDRON.get(), 5, 20);
        fireblock.setFlammable(WOODEN_CAULDRON.get(), 5, 20);
        fireblock.setFlammable(WOODEN_CAULDRON.get(), 5, 20);
        //fireblock.setFlammable(CHARRED_LOG.get(), 10, 10);
    }
}
