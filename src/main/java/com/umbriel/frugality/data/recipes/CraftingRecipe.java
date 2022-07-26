package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class CraftingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        recipes(consumer);
    }


    private static void recipes(Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_COBBLESTONE.get())
                .pattern("bb ")
                .pattern("bb ")
                .pattern("   ")
                .define('b', FrugalItems.SILICA.get())
                .unlockedBy("has_silica", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA.get()))

                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone"));


        //        ShapedRecipeBuilder.shaped(ModItems.MELTER.get())
        //                .pattern("PT ")
        //                .pattern("SGS")
        //                .pattern("   ")
        //                .define('P', Items.GLASS_PANE)
        //                .define('G', ForgeTags.GLASS)
        //                .define('S', ItemTags.WOODEN_SLABS)
        //                .define('T', Items.STICK)
        //                .unlockedBy("has_glass", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_GLASS.get(), Items.GLASS))
        //                .save(consumer, new ResourceLocation(Frugality.MODID, "melter"));

        ShapedRecipeBuilder.shaped(Items.DIRT)
                .pattern("CC ")
                .pattern("CC ")
                .pattern("   ")
                .define('C', FrugalItems.COMPOST.get())
                .unlockedBy("has_compost", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.COMPOST.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "4x_compost_to_dirt"));

        ShapedRecipeBuilder.shaped(Items.DIRT)
                .pattern("CS ")
                .pattern("FC ")
                .pattern("   ")
                .define('C', FrugalItems.COMPOST.get())
                .define('S', ItemTags.SAND)
                .define('F', Items.FLINT)
                .unlockedBy("has_compost", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.COMPOST.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "2x_compost_to_dirt"));

        ShapedRecipeBuilder.shaped(FrugalItems.REINFORCED_COMPOSTER.get())
                .pattern("B B")
                .pattern("B B")
                .pattern("PPP")
                .define('B', ItemTags.WOODEN_SLABS)
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_compost", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.COMPOST.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "reinforced_composter"));

        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_BRICKS.get(), 4)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .define('A', FrugalItems.SILICA_STONE.get())
                .unlockedBy("has_silica_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone_to_bricks"));

        // Silica Cobblestone
        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_COBBLESTONE_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', FrugalItems.SILICA_COBBLESTONE.get())
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_stairs"));

        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_COBBLESTONE_SLAB.get(), 6)
                .pattern("   ")
                .pattern("   ")
                .pattern("AAA")
                .define('A', FrugalItems.SILICA_COBBLESTONE.get())
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_slabs"));

        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_COBBLESTONE_WALL.get(), 6)
                .pattern("   ")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', FrugalItems.SILICA_COBBLESTONE.get())
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_wall"));

        // Silica Bricks
        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_BRICKS_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', FrugalItems.SILICA_BRICKS.get())
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_stairs"));

        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_BRICKS_SLAB.get(), 6)
                .pattern("   ")
                .pattern("   ")
                .pattern("AAA")
                .define('A', FrugalItems.SILICA_BRICKS.get())
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_slabs"));

        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_BRICKS_WALL.get(), 6)
                .pattern("   ")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', FrugalItems.SILICA_BRICKS.get())
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_wall"));

        // Silica Stone
        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_STONE_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', FrugalItems.SILICA_STONE.get())
                .unlockedBy("has_silica_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone_stairs"));

        ShapedRecipeBuilder.shaped(FrugalItems.SILICA_STONE_SLAB.get(), 6)
                .pattern("   ")
                .pattern("   ")
                .pattern("AAA")
                .define('A', FrugalItems.SILICA_STONE.get())
                .unlockedBy("has_silica_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone_slabs"));

        ShapedRecipeBuilder.shaped(FrugalItems.SALT_BLOCK.get())
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', FrugalItems.SALT.get())
                .unlockedBy("has_salt", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SALT.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "salt_to_salt_block"));

        ShapedRecipeBuilder.shaped(FrugalItems.SALT.get(), 9)
                .pattern("C ")
                .pattern("  ")
                .pattern("  ")
                .define('C', FrugalItems.SALT_BLOCK.get())
                .unlockedBy("has_salt_block", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SALT_BLOCK.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "salt_block_to_salt"));

        ShapelessRecipeBuilder.shapeless(FrugalItems.SILICA.get(), 4)
                .requires(FrugalItems.SILICA_COBBLESTONE.get())
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica_unpacking"));

        ShapelessRecipeBuilder.shapeless(FrugalItems.HEATED_STONE.get(), 1)
                .requires(FrugalItems.THERMAL_STONE.get())
                .requires(FrugalItems.BLUE_SALT.get())
                .unlockedBy("has_blue_salt", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.BLUE_SALT.get()))
                .unlockedBy("has_thermal_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.THERMAL_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "exothermic_stone"));

        ShapelessRecipeBuilder.shapeless(FrugalItems.CHILLED_STONE.get(), 1)
                .requires(FrugalItems.THERMAL_STONE.get())
                .requires(FrugalItems.RED_SALT.get())
                .unlockedBy("has_blue_salt", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.RED_SALT.get()))
                .unlockedBy("has_thermal_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.THERMAL_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "endothermic_stone"));

        ShapelessRecipeBuilder.shapeless(FrugalItems.RED_SALT.get(), 1)
                .requires(FrugalItems.SALT.get())
                .requires(Items.REDSTONE)
                .requires(Items.FLINT)
                .unlockedBy("has_salt", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SALT.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "red_salt"));

        ShapelessRecipeBuilder.shapeless(FrugalItems.BLUE_SALT.get(), 1)
                .requires(FrugalItems.SALT.get())
                .requires(Items.LAPIS_LAZULI)
                .requires(Items.FLINT)
                .unlockedBy("has_salt", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SALT.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "blue_salt"));

    }



}
