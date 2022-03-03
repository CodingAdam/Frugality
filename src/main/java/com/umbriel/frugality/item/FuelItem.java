package com.umbriel.frugality.item;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class FuelItem extends Item {

    protected int burnTime = -1;

    public FuelItem(Properties properties) {
        super(properties);
    }

    public FuelItem setBurnTime(int burnTime) {
        this.burnTime = burnTime;
        return this;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType){
        return burnTime;
    }
}
