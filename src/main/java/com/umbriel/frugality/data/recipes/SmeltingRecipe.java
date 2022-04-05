package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class SmeltingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        blockRecipes(consumer);
    }

    private static void blockRecipes(Consumer<FinishedRecipe> consumer){
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.SILICA_COBBLESTONE.get()),
                ModItems.SILICA_STONE.get(), 0.0F, 200)
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone"));
    }


}
