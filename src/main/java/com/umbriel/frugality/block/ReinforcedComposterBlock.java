package com.umbriel.frugality.block;


import com.umbriel.frugality.item.ChanceItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

import static com.umbriel.frugality.init.FrugalItems.COMPOST;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class ReinforcedComposterBlock extends ComposterBlock implements WorldlyContainerHolder {
    static float buffer;
    static ItemStack drop;

    public ReinforcedComposterBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
        drop = new ItemStack(Items.AIR, 0);
        COMPOSTABLES.put(Items.ROTTEN_FLESH, 0.3F);
    }

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        int i = state.getValue(LEVEL);
        ItemStack itemstack = player.getItemInHand(hand);
        if (i < 8 && COMPOSTABLES.containsKey(itemstack.getItem())) {
            if (i < 7 && !world.isClientSide) {
                BlockState blockstate = addItem(state, world, pos, itemstack);
                world.levelEvent(1500, pos, state != blockstate ? 1 : 0);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(world.isClientSide);
        } else if (i == 8) {
            extractProduce(state, world, pos);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    private static ItemStack compostDrop(){
        ChanceItem compost = new ChanceItem(new ItemStack(COMPOST.get(), 3), 0.55F);
        ItemStack stack = compost.rollOutput();
        stack.setCount(stack.getCount() + 2); // At least two
        return stack;
    }

    public static BlockState extractProduce(BlockState state, Level world, BlockPos pos) {
        if (!world.isClientSide) {
            float f = 0.7F;
            double d0 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
            double d1 = (double)(world.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
            double d2 = (double)(world.random.nextFloat() * 0.7F) + (double)0.15F;
            ItemEntity itementity = new ItemEntity(world, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, compostDrop());
            itementity.setDefaultPickUpDelay();
            world.addFreshEntity(itementity);
        }

        BlockState blockstate = empty(state, world, pos);
        world.playSound(null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        return blockstate;
    }

    private static BlockState empty(BlockState state, LevelAccessor world, BlockPos pos) {
        BlockState blockstate = state.setValue(LEVEL, 0);
        world.setBlock(pos, blockstate, 3);
        return blockstate;
    }


    private static BlockState addItem(BlockState state, LevelAccessor world, BlockPos pos, ItemStack itemStack) {
        int i = state.getValue(LEVEL);
        float f = COMPOSTABLES.getFloat(itemStack.getItem());
        buffer += f/3; // More stuff for your compost
        int j;
        if(buffer < 1){
            j = i;
        }
        else {
            j = i + 1;
            buffer -= 1;
        }
        BlockState blockstate = state.setValue(LEVEL, j);
        world.setBlock(pos, blockstate, 3);
        if (j == 7) {
            world.scheduleTick(pos, state.getBlock(), 20);
            buffer = 0;
        }
        return blockstate;
    }


    public WorldlyContainer getContainer(BlockState state, LevelAccessor world, BlockPos pos) {
        int i = state.getValue(LEVEL);
        if (i == 8) {
            if(drop.isEmpty()) drop = compostDrop();
            return new ReinforcedComposterBlock.OutputContainer(state, world, pos, drop);
        } else {
            return i < 7 ? new InputContainer(state, world, pos) : new EmptyContainer();
        }
    }

    static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
        public EmptyContainer() {
            super(0);
        }

        public int[] getSlotsForFace(Direction p_52012_) {
            return new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52008_, ItemStack p_52009_, @Nullable Direction p_52010_) {
            return false;
        }

        public boolean canTakeItemThroughFace(int p_52014_, ItemStack p_52015_, Direction p_52016_) {
            return false;
        }
    }

    static class InputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public InputContainer(BlockState p_52022_, LevelAccessor p_52023_, BlockPos p_52024_) {
            super(1);
            this.state = p_52022_;
            this.level = p_52023_;
            this.pos = p_52024_;
        }

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction p_52032_) {
            return p_52032_ == Direction.UP ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52028_, ItemStack p_52029_, @Nullable Direction p_52030_) {
            return !this.changed && p_52030_ == Direction.UP && ComposterBlock.COMPOSTABLES.containsKey(p_52029_.getItem());
        }

        public boolean canTakeItemThroughFace(int p_52034_, ItemStack p_52035_, Direction p_52036_) {
            return false;
        }

        public void setChanged() {
            ItemStack itemstack = this.getItem(0);
            if (!itemstack.isEmpty()) {
                this.changed = true;
                BlockState blockstate = ReinforcedComposterBlock.addItem(this.state, this.level, this.pos, itemstack);
                this.level.levelEvent(1500, this.pos, blockstate != this.state ? 1 : 0);
                this.removeItemNoUpdate(0);
            }

        }
    }

    static class OutputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public OutputContainer(BlockState p_52042_, LevelAccessor p_52043_, BlockPos p_52044_, ItemStack p_52045_) {
            super(p_52045_);
            this.state = p_52042_;
            this.level = p_52043_;
            this.pos = p_52044_;
        }

        public int getMaxStackSize() {
            return 5;
        }

        public int[] getSlotsForFace(Direction p_52053_) {
            return p_52053_ == Direction.DOWN ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52049_, ItemStack p_52050_, @Nullable Direction p_52051_) {
            return false;
        }

        public boolean canTakeItemThroughFace(int p_52055_, ItemStack p_52056_, Direction p_52057_) {
            return !this.changed && p_52057_ == Direction.DOWN && p_52056_.is(COMPOST.get());
        }

        public void setChanged() {
            ItemStack itemstack = this.getItem(0);
            if (itemstack.isEmpty()) {
                ReinforcedComposterBlock.empty(this.state, this.level, this.pos);
                this.changed = true;
            }
        }
    }
}
