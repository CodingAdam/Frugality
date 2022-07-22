package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModItems;
import com.umbriel.frugality.util.tags.ForgeTags;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class CraftingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        recipes(consumer);
    }


    private static void recipes(Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(ModItems.SILICA_COBBLESTONE.get())
                .pattern("bb ")
                .pattern("bb ")
                .pattern("   ")
                .define('b', ModItems.SILICA.get())
                .unlockedBy("has_silica", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA.get()))

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
                .define('C', ModItems.COMPOST.get())
                .unlockedBy("has_compost", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COMPOST.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "4x_compost_to_dirt"));

        ShapedRecipeBuilder.shaped(Items.DIRT)
                .pattern("CS ")
                .pattern("FC ")
                .pattern("   ")
                .define('C', ModItems.COMPOST.get())
                .define('S', ItemTags.SAND)
                .define('F', Items.FLINT)
                .unlockedBy("has_compost", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COMPOST.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "2x_compost_to_dirt"));

        ShapedRecipeBuilder.shaped(ModItems.REINFORCED_COMPOSTER.get())
                .pattern("B B")
                .pattern("B B")
                .pattern("PPP")
                .define('B', ItemTags.WOODEN_SLABS)
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_compost", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.COMPOST.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "reinforced_composter"));

        ShapedRecipeBuilder.shaped(ModItems.SILICA_BRICKS.get(), 4)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .define('A', ModItems.SILICA_STONE.get())
                .unlockedBy("has_silica_stone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone_to_bricks"));

        // Silica Cobblestone
        ShapedRecipeBuilder.shaped(ModItems.SILICA_COBBLESTONE_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', ModItems.SILICA_COBBLESTONE.get())
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_stairs"));

        ShapedRecipeBuilder.shaped(ModItems.SILICA_COBBLESTONE_SLAB.get(), 6)
                .pattern("   ")
                .pattern("   ")
                .pattern("AAA")
                .define('A', ModItems.SILICA_COBBLESTONE.get())
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_slabs"));

        ShapedRecipeBuilder.shaped(ModItems.SILICA_COBBLESTONE_WALL.get(), 6)
                .pattern("   ")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.SILICA_COBBLESTONE.get())
                .unlockedBy("has_silica_cobblestone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_COBBLESTONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_cobblestone_wall"));

        // Silica Bricks
        ShapedRecipeBuilder.shaped(ModItems.SILICA_BRICKS_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', ModItems.SILICA_BRICKS.get())
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_stairs"));

        ShapedRecipeBuilder.shaped(ModItems.SILICA_BRICKS_SLAB.get(), 6)
                .pattern("   ")
                .pattern("   ")
                .pattern("AAA")
                .define('A', ModItems.SILICA_BRICKS.get())
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_slabs"));

        ShapedRecipeBuilder.shaped(ModItems.SILICA_BRICKS_WALL.get(), 6)
                .pattern("   ")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.SILICA_BRICKS.get())
                .unlockedBy("has_silica_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_BRICKS.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_bricks_wall"));

        // Silica Stone
        ShapedRecipeBuilder.shaped(ModItems.SILICA_STONE_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', ModItems.SILICA_STONE.get())
                .unlockedBy("has_silica_stone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone_stairs"));

        ShapedRecipeBuilder.shaped(ModItems.SILICA_STONE_SLAB.get(), 6)
                .pattern("   ")
                .pattern("   ")
                .pattern("AAA")
                .define('A', ModItems.SILICA_STONE.get())
                .unlockedBy("has_silica_stone", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILICA_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_stone_slabs"));

    }



}
