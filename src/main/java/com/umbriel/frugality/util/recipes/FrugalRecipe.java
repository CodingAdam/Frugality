package com.umbriel.frugality.util.recipes;

import com.umbriel.frugality.item.ChanceItem;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class FrugalRecipe implements Recipe<RecipeWrapper> {

    protected final ResourceLocation identifier;
    protected final String group;
    protected final Ingredient input;
    protected final Ingredient tool;
    protected final FluidStack fluidOutput;
    protected final NonNullList<ChanceItem> itemOutputs;
    protected final int stoneType;
    protected final int hits;
    protected final int type;
    protected final int level;
    protected boolean hidden;

    protected FrugalRecipe(ResourceLocation identifier, String group, Ingredient input, Ingredient tool, FluidStack fluidOutput, NonNullList<ChanceItem> itemOutputs, int stoneType, int hits, int type, int level, boolean hidden) {
        this.identifier = identifier;
        this.group = group;
        this.input = input;
        this.tool = tool;
        this.fluidOutput = fluidOutput;
        this.itemOutputs = itemOutputs;
        this.stoneType = stoneType;
        this.hits = hits;
        this.type = type;
        this.level = level;
        this.hidden = hidden;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.identifier;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public Ingredient getTool() {
        return this.tool;
    }

    public int getStoneType(){
        return stoneType;
    }

    public String getGroup() {
        return this.group;
    }

    public boolean isHidden() {

        return this.hidden;
    }

    public void setHidden (boolean isHidden) {
        this.hidden = isHidden;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return input.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    public boolean doesMatch(ItemStack item) {
        return this.input.test(item);
    }

    public List<ItemStack> getOutputsAsItems() {
        List<ItemStack> items = new ArrayList<>();
        for (ChanceItem output : itemOutputs){
            ItemStack stack = output.getItem();
            if (!stack.isEmpty())
                items.add(stack);
        }
        return items;
    }

    public FluidStack getFluidResult() {
        return this.fluidOutput;
    }

    public NonNullList<ChanceItem> getItemResult() {
        return this.itemOutputs;
    }

    public int getHits(){
        return this.hits;
    }

    public int getFill(){
        return this.type;
    }

    public int getFluidLevel() {
        return this.level;
    }

    public List<ItemStack> rollOutputs() {
        List<ItemStack> results = new ArrayList<>();
        NonNullList<ChanceItem> rollableResults = getItemResult();
        for (ChanceItem output : rollableResults) {
            ItemStack stack = output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }
}
