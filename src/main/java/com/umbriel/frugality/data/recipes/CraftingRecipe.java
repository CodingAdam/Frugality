package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class CraftingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        blockRecipes(consumer);
    }

    private static void blockRecipes(Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(ModItems.SILICA_COBBLESTONE.get())
                .pattern("bb ")
                .pattern("bb ")
                .pattern("   ")
                .define('b', ModItems.SILICA.get())
                .unlockedBy("has_silica", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone"));
    }


}
