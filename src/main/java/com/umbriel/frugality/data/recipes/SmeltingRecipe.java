package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class SmeltingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        recipes(consumer);
    }

    private static void recipes(Consumer<FinishedRecipe> consumer){
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(FrugalItems.SILICA_COBBLESTONE.get()),
                FrugalItems.SILICA_STONE.get(), 0.0F, 200)
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone"));

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(FrugalItems.THERMAL_STONE.get()),
                FrugalItems.HEATED_STONE.get(), 0.0F, 2400)
                .unlockedBy("has_thermal_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.THERMAL_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "heated_stone_from_campfire"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(FrugalItems.SILICA_SAND.get()),
                Items.GLASS, 0.1F, 200)
                .unlockedBy("has_silica_sand", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_SAND.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_glass"));

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(FrugalItems.SILICA_SAND.get()),
                Items.GLASS, 0.1F, 1000)
                .unlockedBy("has_silica_sand", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_SAND.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_glass_from_smelting"));

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(FrugalItems.CLAY_BRICK.get()),
                Items.BRICK, 0.0F, 1000)
                .unlockedBy("has_clay_brick", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.CLAY_BRICK.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "brick/brick_from_campfire"));

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(FrugalItems.CLAY_BRICK.get()),
                Items.BRICK, 0.0F, 200)
                .unlockedBy("has_clay_brick", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.CLAY_BRICK.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "brick/brick_from_smelting"));

        //smeltAndBlast(consumer, FrugalItems.SMALL_RAW_GOLD.get(), Items.GOLD_NUGGET, "gold", 0.7F);
        //smeltAndBlast(consumer, FrugalItems.SMALL_RAW_COPPER.get(), FrugalItems.COPPER_NUGGET.get(), "copper", 0.7F);
        //smeltAndBlast(consumer, FrugalItems.SMALL_RAW_IRON.get(), Items.IRON_NUGGET, "iron", 0.7F);
        smeltAndBlast(consumer, FrugalItems.COPPER_CUP.get(), FrugalItems.COPPER_NUGGET.get(), "copper", 0.7F); // New recipe for multiple nuggets

        blastingMaterial(consumer, FrugalItems.GOLDEN_HAMMER.get(), Items.GOLD_NUGGET, "gold", 0.1F);
        blastingMaterial(consumer, FrugalItems.IRON_HAMMER.get(), Items.IRON_NUGGET, "iron", 0.1F);


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

    public static void smeltAndBlast(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, String folder, float exp){
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), output, exp, 200)
                .unlockedBy("has_" + input.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(input))
                .save(consumer, new ResourceLocation(Frugality.MODID,  "materials/" + folder + "/" + output.toString() + "_from_smelting_" + input.toString()));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(input), output, exp, 100)
                .unlockedBy("has_" + input.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(input))
                .save(consumer, new ResourceLocation(Frugality.MODID, "materials/" + folder + "/" + output.toString() + "_from_blasting_" + input.toString()));
    }

    public static void blastingMaterial(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output, String folder, float exp){
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(input), output, exp, 100)
                .unlockedBy("has_" + input.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(input))
                .save(consumer, new ResourceLocation(Frugality.MODID, "materials/" + folder + "/" + output.toString() + "_from_blasting_" + input.toString()));
    }
}
