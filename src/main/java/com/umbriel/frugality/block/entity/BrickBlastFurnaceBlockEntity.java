package com.umbriel.frugality.block.entity;

import com.umbriel.frugality.init.FrugalBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class BrickBlastFurnaceBlockEntity extends AbstractCustomFurnaceEntity {


    public BrickBlastFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(FrugalBlockEntities.BRICK_BLAST_FURNACE.get(), pos, state, 1.3, RecipeType.BLASTING);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.brick_blast_furnace");
    }

    @Override
    protected int getBurnDuration(ItemStack p_58852_) {
        return super.getBurnDuration(p_58852_) / 2;
    }
    // Slower speed than Cobble
    @Override
    protected AbstractContainerMenu createMenu(int p_58849_, Inventory p_58850_) {
        return new BlastFurnaceMenu(p_58849_, p_58850_, this, this.dataAccess);
    }


}
