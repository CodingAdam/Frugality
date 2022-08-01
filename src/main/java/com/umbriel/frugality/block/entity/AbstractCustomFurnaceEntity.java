package com.umbriel.frugality.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class AbstractCustomFurnaceEntity extends AbstractFurnaceBlockEntity {
    protected final RecipeType<? extends AbstractCookingRecipe> recipeType;

    public AbstractCustomFurnaceEntity(BlockEntityType<?> blockEntity, BlockPos blockPos, BlockState blockState, double processingTime, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntity, blockPos, blockState, recipeType);
        this.recipeType = recipeType;
        this.processingTime = processingTime;
    }

    /* The CODE BELOW is copied (and partially modified) from "Shadows-of-Fire/FastFurnace" as a template for the block entity */

    public static final int burnTime = 0;
    public static final int cookingTime = 2;
    public static final int totalCookingTime = 3;

    public static final int input = 0;
    public static final int output = 2;
    public static final int fuel = 1;

    protected double processingTime;
    protected AbstractCookingRecipe currentRecipe;
    protected ItemStack nullRecipe = ItemStack.EMPTY;

    private boolean isBurning() {
        return this.dataAccess.get(burnTime) > 0;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AbstractCustomFurnaceEntity entity) {
        boolean isProcessing = entity.isBurning();
        boolean processing = false;
        if (entity.isBurning()) {
            entity.dataAccess.set(burnTime, entity.dataAccess.get(burnTime) - 1);
        }

        if (level != null && !level.isClientSide) {
            ItemStack fuel = entity.items.get(AbstractCustomFurnaceEntity.fuel);
            if (entity.isBurning() || !fuel.isEmpty() && !entity.items.get(input).isEmpty()) {
                AbstractCookingRecipe recipe = entity.getRecipe();
                boolean valid = entity.canBurn(recipe);
                if (!entity.isBurning() && valid) {
                    entity.dataAccess.set(burnTime, entity.getBurnDuration(fuel));
                    if (entity.isBurning()) {
                        processing = true;
                        if (fuel.hasContainerItem()) entity.items.set(1, fuel.getContainerItem());
                        else if (!fuel.isEmpty()) {
                            fuel.shrink(1);
                            if (fuel.isEmpty()) {
                                entity.items.set(1, fuel.getContainerItem());
                            }
                        }
                    }
                }

                if (entity.isBurning() && valid) {
                    entity.dataAccess.set(cookingTime, entity.dataAccess.get(cookingTime) + 1);
                    if (entity.dataAccess.get(cookingTime) == entity.dataAccess.get(totalCookingTime)) {
                        entity.dataAccess.set(cookingTime, 0);
                        entity.dataAccess.set(totalCookingTime, entity.getTotalCookTime());
                        entity.smeltItem(recipe);
                        processing = true;
                    }
                } else {
                    entity.dataAccess.set(cookingTime, 0);
                }
            } else if (!entity.isBurning() && entity.dataAccess.get(cookingTime) > 0) {
                entity.dataAccess.set(cookingTime, Mth.clamp(entity.dataAccess.get(cookingTime) - 2, 0, entity.dataAccess.get(totalCookingTime)));
            }

            if (isProcessing != entity.isBurning()) {
                processing = true;
                level.setBlock(pos, state.setValue(AbstractFurnaceBlock.LIT, entity.isBurning()), 3);
            }
        }

        if (processing) {
            entity.setChanged();
        }

    }

    private boolean canBurn(@Nullable Recipe<?> recipe) {
        if (!this.items.get(0).isEmpty() && recipe != null) {
            ItemStack recipeOutput = recipe.getResultItem();
            if (!recipeOutput.isEmpty()) {
                ItemStack output = this.items.get(AbstractCustomFurnaceEntity.output);
                if (output.isEmpty()) return true;
                else if (!output.sameItem(recipeOutput)) return false;
                else return output.getCount() + recipeOutput.getCount() <= output.getMaxStackSize();
            }
        }
        return false;
    }

    private void smeltItem(@Nullable Recipe<?> recipe) {
        if (recipe != null && this.canBurn(recipe)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = recipe.getResultItem();
            ItemStack itemstack2 = this.items.get(2);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (this.level != null && !this.level.isClientSide) {
                this.setRecipeUsed(recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }

    private int getTotalCookTime() {
        AbstractCookingRecipe rec = getRecipe();
        if (rec == null) {
            return 200;
        } else if (this.recipeType.getClass().isInstance(rec.getType())) {
            return (int)(rec.getCookingTime() * processingTime);
        }
        return (int)((rec.getCookingTime()) * processingTime);
    }

    protected AbstractCookingRecipe getRecipe() {
        ItemStack input = this.getItem(AbstractCustomFurnaceEntity.input);
        if (input.isEmpty() || input == nullRecipe) {
            return null;
        }
        if (this.level != null && currentRecipe != null && currentRecipe.matches(this, level)) {
            return currentRecipe;
        } else {
            AbstractCookingRecipe rec = null;
            if (this.level != null) {
                rec = this.level.getRecipeManager().getRecipeFor(this.recipeType, this, this.level).orElse(null);
            }
            if (rec == null) {
                nullRecipe = input;
            } else {
                nullRecipe = ItemStack.EMPTY;
            }
            return currentRecipe = rec;
        }
    }

}

