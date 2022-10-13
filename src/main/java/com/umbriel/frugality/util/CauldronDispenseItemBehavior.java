package com.umbriel.frugality.util;

import com.umbriel.frugality.block.cauldron.AbstractCustomCauldron;
import com.umbriel.frugality.block.cauldron.CustomCauldron;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.BlockHitResult;
import org.checkerframework.checker.nullness.qual.NonNull;

import static com.umbriel.frugality.util.CustomCauldronHelper.*;

public interface CauldronDispenseItemBehavior {

    static void bootstrap(){
        DispenseItemBehavior dispenseitembehavior = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            public ItemStack execute(BlockSource source, ItemStack stack) {
                DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)stack.getItem();
                BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();
                if (dispensiblecontaineritem.emptyContents((Player)null, level, blockpos, (BlockHitResult)null)) {
                    dispensiblecontaineritem.checkExtraContent((Player)null, level, stack, blockpos);
                    return new ItemStack(Items.BUCKET);
                }
                if(source.getBlockState().getBlock() instanceof CustomCauldron || source.getBlockState().getBlock() instanceof CauldronBlock){
                    if(stack.getItem() == Items.WATER_BUCKET){
                        source.getLevel().setBlock(source.getPos(), getWaterCauldron(source.getBlockState().getBlock().defaultBlockState()), 1);
                    }
                    if(stack.getItem() == Items.LAVA_BUCKET){
                        source.getLevel().setBlock(source.getPos(), getLavaCauldron(source.getBlockState().getBlock().defaultBlockState()), 1);
                    }
                    if(stack.getItem() == Items.POWDER_SNOW_BUCKET){
                        source.getLevel().setBlock(source.getPos(), getSnowCauldron(source.getBlockState().getBlock().defaultBlockState()), 1);
                    }
                    return new ItemStack(Items.BUCKET);
                }
                else {
                    return this.defaultDispenseItemBehavior.dispense(source, stack);
                }
            }
        };
        DispenserBlock.registerBehavior(Items.LAVA_BUCKET, dispenseitembehavior);
        DispenserBlock.registerBehavior(Items.WATER_BUCKET, dispenseitembehavior);
        DispenserBlock.registerBehavior(Items.POWDER_SNOW_BUCKET, dispenseitembehavior);
    }

}
