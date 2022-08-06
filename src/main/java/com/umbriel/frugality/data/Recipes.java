package com.umbriel.frugality.data;

import com.umbriel.frugality.data.recipes.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        CraftingRecipe.register(consumer);
        SmeltingRecipe.register(consumer);
        StoneCuttingRecipe.register(consumer);
        CrushingRecipe.register(consumer);
        ThermalRecipe.register(consumer);
        WashingRecipe.register(consumer);
    }
}
