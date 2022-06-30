package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class SmeltingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        recipes(consumer);
    }

    private static void recipes(Consumer<FinishedRecipe> consumer){
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.SILICA_COBBLESTONE.get()),
                ModItems.SILICA_STONE.get(), 0.0F, 200)
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone"));

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.THERMAL_STONE.get()),
                ModItems.HEATED_STONE.get(), 0.0F, 2400)
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.THERMAL_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "heated_stone"));

//        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.COPPER_CUP.get()),
//                new ItemStack(ModItems.COPPER_NUGGET.get(), 6).getItem(), 0.1F, 200)
//                .unlockedBy("has_copper_cup", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_CUP.get()))
//                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/copper_cup_to_nugget_smelting"));
//
//        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.COPPER_CUP.get()),
//                ModItems.COPPER_NUGGET.get(), 0.1F, 100)
//                .unlockedBy("has_copper_cup", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COPPER_CUP.get()))
//                .save(consumer, new ResourceLocation(Frugality.MODID, "copper/copper_cup_to_nugget_blasting"));
      }


}
