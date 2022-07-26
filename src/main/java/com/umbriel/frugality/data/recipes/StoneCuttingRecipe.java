package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class StoneCuttingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        recipes(consumer);
    }

    private static void recipes(Consumer<FinishedRecipe> consumer){
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_BRICKS.get(), FrugalItems.SILICA_STONE.get()), FrugalItems.SILICA_POLISHED.get())
                .unlockedBy("has_silica_brick", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_polished_from_bricks_stonecutter"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_POLISHED.get(), FrugalItems.SILICA_STONE.get()), FrugalItems.SILICA_BRICKS.get())
                .unlockedBy("has_silica_tile", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_POLISHED.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_from_polished_stonecutter"));

        // Silica Cobblestone
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_COBBLESTONE.get()), FrugalItems.SILICA_COBBLESTONE_WALL.get())
                .unlockedBy("has_silica_cobble", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_wall_stonecutter"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_COBBLESTONE.get()), FrugalItems.SILICA_COBBLESTONE_STAIRS.get())
                .unlockedBy("has_silica_cobble", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_stairs_stonecutter"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_COBBLESTONE.get()), FrugalItems.SILICA_COBBLESTONE_SLAB.get(), 2)
                .unlockedBy("has_silica_cobble", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_slab_stonecutter"));

        // Silica Bricks
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_BRICKS.get()), FrugalItems.SILICA_BRICKS_WALL.get())
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_wall_stonecutter"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_BRICKS.get()), FrugalItems.SILICA_BRICKS_STAIRS.get())
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_stairs_stonecutter"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_BRICKS.get()), FrugalItems.SILICA_BRICKS_SLAB.get(), 2)
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_slab_stonecutter"));

        // Silica Stone
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_STONE.get()), FrugalItems.SILICA_STONE_STAIRS.get())
                .unlockedBy("has_silica_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone_stairs_stonecutter"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_STONE.get()), FrugalItems.SILICA_STONE_SLAB.get(), 2)
                .unlockedBy("has_silica_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone_slab_stonecutter"));


        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FrugalItems.SILICA_POLISHED.get()), FrugalItems.SILICA_POLISHED_SLAB.get(), 2)
                .unlockedBy("has_silica_polished", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_POLISHED.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_polished_slab_stonecutter"));
    }
}
