package com.umbriel.frugality.data;

import com.umbriel.frugality.data.recipes.CraftingRecipe;
import com.umbriel.frugality.data.recipes.SmeltingRecipe;
import com.umbriel.frugality.data.recipes.StoneCuttingRecipe;
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
    }
}
