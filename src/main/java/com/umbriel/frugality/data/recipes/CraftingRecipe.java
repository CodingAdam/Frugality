package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

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

        ShapedRecipeBuilder.shaped(Items.PAPER, 1)
                .pattern("CCC")
                .pattern("   ")
                .pattern("   ")
                .define('C', FrugalItems.BARK.get())
                .unlockedBy("has_bark", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.BARK.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "bark_to_paper"));

        hammerRecipe(Items.IRON_INGOT, FrugalItems.IRON_HAMMER.get(), "iron", consumer);
        hammerRecipe(Items.GOLD_INGOT, FrugalItems.GOLDEN_HAMMER.get(), "gold", consumer);
        hammerRecipe(Items.DIAMOND, FrugalItems.DIAMOND_HAMMER.get(), "diamond", consumer);
        hammerRecipe(ItemTags.STONE_TOOL_MATERIALS, FrugalItems.STONE_HAMMER.get(), "stone", consumer);
        hammerRecipe(ItemTags.PLANKS, FrugalItems.WOODEN_HAMMER.get(), "wooden", consumer);

        upgradeRecipe(Items.NETHERITE_INGOT, FrugalItems.DIAMOND_HAMMER.get(), FrugalItems.NETHERITE_HAMMER.get(), "netherite", "hammer", consumer);

        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), FrugalItems.IRON_HAMMER.get(), FrugalItems.ROSE_GOLD_HAMMER.get(), "rose", "hammer", consumer);
        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_AXE, FrugalItems.ROSE_GOLD_AXE.get(), "rose", "axe", consumer);
        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_SHOVEL, FrugalItems.ROSE_GOLD_SHOVEL.get(), "rose", "shovel", consumer);
        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_HOE, FrugalItems.ROSE_GOLD_HOE.get(), "rose", "hoe", consumer);
        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_PICKAXE, FrugalItems.ROSE_GOLD_PICKAXE.get(), "rose", "pickaxe", consumer);
        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_SWORD, FrugalItems.ROSE_GOLD_SWORD.get(), "rose", "sword", consumer);

        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_BOOTS, FrugalItems.ROSE_GOLD_BOOTS.get(), "rose", "boots", consumer);
        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_CHESTPLATE, FrugalItems.ROSE_GOLD_CHESTPLATE.get(), "rose", "chestplate", consumer);
        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_LEGGINGS, FrugalItems.ROSE_GOLD_LEGGINGS.get(), "rose", "leggings", consumer);
        upgradeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), Items.IRON_HELMET, FrugalItems.ROSE_GOLD_HELMET.get(), "rose", "helmet", consumer);
        fullRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), FrugalItems.ROSE_GOLD_BLOCK.get().asItem(), "rose", consumer);
        singleItem(FrugalItems.ROSE_GOLD_BLOCK.get().asItem(), FrugalItems.ROSE_GOLD_INGOT.get(), 9, "rose", consumer);


        fullRecipe(FrugalItems.COPPER_NUGGET.get(), Items.COPPER_INGOT, "copper", consumer);
        singleItem(Items.COPPER_INGOT, FrugalItems.COPPER_NUGGET.get(), 9, "copper", consumer);
        ShapelessRecipeBuilder.shapeless(FrugalItems.COPPER_STARTER.get(), 1)
                .requires(Items.COPPER_INGOT)
                .requires(Items.FLINT)
                .unlockedBy("has_copper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
                .save(consumer, new ResourceLocation(Frugality.MODID, "copper_starter"));

        fullRecipe(FrugalItems.SMALL_RAW_COPPER.get(), Items.RAW_COPPER, "copper", consumer);
        fullRecipe(FrugalItems.SMALL_RAW_IRON.get(), Items.RAW_IRON, "iron", consumer);
        fullRecipe(FrugalItems.SMALL_RAW_GOLD.get(), Items.RAW_GOLD, "gold", consumer);
    }

    private static void hammerRecipe(Item headMaterial, ItemLike hammer, String material, Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(hammer, 1)
                .pattern("AAA")
                .pattern(" A ")
                .pattern(" S ")
                .define('A', headMaterial)
                .define('S', Items.STICK)
                .unlockedBy("has_" + headMaterial.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(headMaterial))
                .save(consumer, new ResourceLocation(Frugality.MODID, "materials/" + material + "/" + material + "_" + "hammer"));
    }

    private static void hammerRecipe(TagKey<Item> headMaterial, ItemLike hammer, String material, Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(hammer, 1)
                .pattern("AAA")
                .pattern(" A ")
                .pattern(" S ")
                .define('A', headMaterial)
                .define('S', Items.STICK)
                .unlockedBy("has_" + headMaterial.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(headMaterial).build()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "materials/" + material + "/" + material + "_" + "hammer"));
    }

    private static void upgradeRecipe(ItemLike upgradeMaterial, ItemLike upgradeItem, Item newItem, String material, String type, Consumer<FinishedRecipe> consumer){
        UpgradeRecipeBuilder.smithing(Ingredient.of(upgradeItem), Ingredient.of(upgradeMaterial), newItem)
                .unlocks("has_" + upgradeMaterial.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(upgradeMaterial))
                .save(consumer, Frugality.MODID + ":materials/" + material + "/" + material + "_" + type + "_from_smithing");
    }

    private static void fullRecipe(ItemLike input, ItemLike result, String material, Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(result, 1)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', input)
                .unlockedBy("has_" + input.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(input))
                .save(consumer, new ResourceLocation(Frugality.MODID, "materials/" + material + "/" + result.toString()));
    }

    private static void singleItem(Item input, ItemLike result, int num, String material, Consumer<FinishedRecipe> consumer){
        ShapelessRecipeBuilder.shapeless(result, num)
                .requires(input)
                .unlockedBy("has_" + input.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(input))
                .save(consumer, Frugality.MODID + ":materials/" + material + "/" + result.toString() + "_from_" + input.toString());
    }

}
