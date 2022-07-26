package com.umbriel.frugality.util.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeHelper {

    @Nullable
    public static FrugalRecipe findRecipe(Level level, ItemStack itemStack, List<? extends FrugalRecipe> recipes) {
        for (final FrugalRecipe recipe : recipes) {
            if (recipe.doesMatch(itemStack)) {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    public static FrugalRecipe findRecipeById(List<? extends FrugalRecipe> recipes, ResourceLocation recipeID) {
        for (final FrugalRecipe recipe : recipes) {
            if (recipe.getId() == recipeID) {
                return recipe;
            }
        }
        return null;
    }
}
