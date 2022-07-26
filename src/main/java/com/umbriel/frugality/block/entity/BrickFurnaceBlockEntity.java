package com.umbriel.frugality.block.entity;

import com.umbriel.frugality.init.FrugalBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public class BrickFurnaceBlockEntity extends AbstractCustomFurnaceEntity {

    public BrickFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(FrugalBlockEntities.BRICK_FURNACE.get(), pos, state, 1.3, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.brick_furnace");
    }

    @Override
    protected int getBurnDuration(ItemStack p_58852_) {
        return super.getBurnDuration(p_58852_);
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58849_, Inventory p_58850_) {
        return new FurnaceMenu(p_58849_, p_58850_, this, this.dataAccess);
    }

}
