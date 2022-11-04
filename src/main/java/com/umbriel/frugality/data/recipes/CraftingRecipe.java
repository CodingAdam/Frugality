package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.util.tags.ForgeTags;
import com.umbriel.frugality.util.tags.ModTags;
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
                .unlockedBy("has_planks", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ItemTags.PLANKS).build()))
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
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/silica_unpacking"));

        ShapelessRecipeBuilder.shapeless(FrugalItems.HEATED_STONE.get(), 1)
                .requires(FrugalItems.THERMAL_STONE.get())
                .requires(FrugalItems.SALT_BLOCK.get().asItem())
                .requires(Items.REDSTONE, 5)
                .unlockedBy("has_salt_block", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SALT_BLOCK.get().asItem()))
                .unlockedBy("has_thermal_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.THERMAL_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "exothermic_stone"));

        ShapelessRecipeBuilder.shapeless(FrugalItems.CHILLED_STONE.get(), 1)
                .requires(FrugalItems.THERMAL_STONE.get())
                .requires(FrugalItems.SALT_BLOCK.get().asItem())
                .requires(Items.LAPIS_LAZULI, 5)
                .unlockedBy("has_salt_block", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SALT_BLOCK.get().asItem()))
                .unlockedBy("has_thermal_stone", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.THERMAL_STONE.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "endothermic_stone"));

        ShapedRecipeBuilder.shaped(Items.PAPER, 1)
                .pattern("CCC")
                .pattern("   ")
                .pattern("   ")
                .define('C', FrugalItems.BARK.get())
                .unlockedBy("has_bark", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.BARK.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "bark_to_paper"));

        ShapedRecipeBuilder.shaped(FrugalItems.CLAY_BRICK.get(), 1)
                .pattern("CA ")
                .pattern("AC ")
                .pattern("   ")
                .define('C', Items.CLAY_BALL)
                .define('A', Items.BONE_MEAL)
                .unlockedBy("has_clay", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
                .unlockedBy("has_bone_meal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BONE_MEAL))
                .save(consumer, new ResourceLocation(Frugality.MODID, "clay_brick_from_bones"));

        ShapedRecipeBuilder.shaped(FrugalItems.CLAY_BRICK.get(), 1)
                .pattern("CA ")
                .pattern("   ")
                .pattern("   ")
                .define('C', Items.CLAY_BALL)
                .define('A', Items.SAND)
                .unlockedBy("has_clay", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
                .unlockedBy("has_sand", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SAND))
                .save(consumer, new ResourceLocation(Frugality.MODID, "clay_brick_from_sand"));

        ShapedRecipeBuilder.shaped(FrugalItems.CLAY_BRICK.get(), 2)
                .pattern("CA ")
                .pattern("S  ")
                .pattern("   ")
                .define('C', Items.CLAY_BALL)
                .define('A', Items.SAND)
                .define('S', FrugalItems.SILICA_DUST.get())
                .unlockedBy("has_clay", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CLAY_BALL))
                .unlockedBy("has_silica_dust", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA_DUST.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "clay_brick_from_silica"));

        ShapedRecipeBuilder.shaped(Items.SANDSTONE, 1)
                .pattern("CA ")
                .pattern("AC ")
                .pattern("   ")
                .define('C', FrugalItems.SILICA.get())
                .define('A', Items.SAND)
                .unlockedBy("has_silica", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.SILICA.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "silica/sandstone_with_silica"));

        ShapedRecipeBuilder.shaped(FrugalItems.CLAY_BRICKS.get().asItem(), 1)
                .pattern("CC ")
                .pattern("CC ")
                .pattern("   ")
                .define('C', FrugalItems.CLAY_BRICK.get())
                .unlockedBy("has_clay_brick", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.CLAY_BRICK.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "clay_bricks"));

        ShapedRecipeBuilder.shaped(FrugalItems.BRICK_BLAST_FURNACE.get(), 1)
                .pattern("III")
                .pattern("IXI")
                .pattern("###")
                .define('I', Items.COPPER_INGOT)
                .define('X', FrugalItems.BRICK_FURNACE.get())
                .define('#', Items.BRICK)
                .unlockedBy("has_brick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BRICK))
                .unlockedBy("has_copper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
                .save(consumer, new ResourceLocation(Frugality.MODID, "brick_blast_furnace"));

        ShapedRecipeBuilder.shaped(FrugalItems.BRICK_FURNACE.get(), 1)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', Items.BRICK)
                .unlockedBy("has_brick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BRICK))
                .save(consumer, new ResourceLocation(Frugality.MODID, "brick_furnace"));


        ShapedRecipeBuilder.shaped(FrugalItems.CRUSHING_STONE.get(), 1)
                .pattern("   ")
                .pattern("###")
                .pattern("SBS")
                .define('S', Items.SMOOTH_STONE_SLAB)
                .define('B', Items.SMOOTH_STONE)
                .define('#', Items.STONE)
                .unlockedBy("has_smooth_stone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SMOOTH_STONE))
                .save(consumer, new ResourceLocation(Frugality.MODID, "crushing_stone"));

        ShapedRecipeBuilder.shaped(FrugalItems.CRUSHING_TERRACOTTA.get(), 1)
                .pattern("   ")
                .pattern("###")
                .pattern("SBS")
                .define('S', Items.BRICK_SLAB)
                .define('B', Items.BRICKS)
                .define('#', Items.TERRACOTTA)
                .unlockedBy("has_brick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BRICKS))
                .save(consumer, new ResourceLocation(Frugality.MODID, "crushing_terracotta"));

        ShapedRecipeBuilder.shaped(FrugalItems.FIRE_STARTER.get(), 1)
                .pattern("C  ")
                .pattern(" A ")
                .pattern("   ")
                .define('C', Items.STICK)
                .define('A', Items.BOW)
                .unlockedBy("has_bow", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BOW))
                .save(consumer, new ResourceLocation(Frugality.MODID, "fire_starter"));

        ShapedRecipeBuilder.shaped(FrugalItems.STONE_CAULDRON.get(), 1)
                .pattern("C C")
                .pattern("C C")
                .pattern("CCC")
                .define('C', Items.STONE)
                .unlockedBy("has_stone", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE))
                .save(consumer, new ResourceLocation(Frugality.MODID, "stone_cauldron"));

        ShapedRecipeBuilder.shaped(FrugalItems.WOODEN_CAULDRON.get(), 1)
                .pattern("C C")
                .pattern("C C")
                .pattern("CCC")
                .define('C', ItemTags.PLANKS)
                .unlockedBy("has_planks", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ItemTags.PLANKS).build()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "wooden_cauldron"));

        ShapedRecipeBuilder.shaped(FrugalItems.TREE_TAP.get(), 1)
                .pattern("SB ")
                .pattern(" P ")
                .pattern("   ")
                .define('S', Items.STICK)
                .define('B', ItemTags.WOODEN_SLABS)
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_planks", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ItemTags.PLANKS).build()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "tree_tap"));

        ShapelessRecipeBuilder.shapeless(Items.STRING, 1)
                .requires(FrugalItems.FIBER.get())
                .requires(FrugalItems.FIBER.get())
                .unlockedBy("has_fiber", InventoryChangeTrigger.TriggerInstance.hasItems(FrugalItems.FIBER.get()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "string_from_fiber"));

        ShapedRecipeBuilder.shaped(FrugalItems.WOODEN_SHEARS.get(), 1)
                .pattern(" S ")
                .pattern("S  ")
                .pattern("   ")
                .define('S', ItemTags.PLANKS)
                .unlockedBy("has_planks", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ItemTags.PLANKS).build()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "wooden_shears"));

        ShapedRecipeBuilder.shaped(FrugalItems.ROSE_GOLD_INGOT.get(), 1)
                .pattern("CCC")
                .pattern("CGG")
                .pattern("GG ")
                .define('G', Items.GOLD_INGOT)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
                .unlockedBy("has_copper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
                .save(consumer, new ResourceLocation(Frugality.MODID, "wooden_shears"));

        twoByTwoRecipe(FrugalItems.CHARRED_SHARDS.get(), Items.CHARCOAL, "", consumer);
        twoByTwoRecipe(FrugalItems.SILICA_DUST.get(), FrugalItems.SILICA_SAND.get().asItem(), "", consumer);

        hammerRecipe(Items.IRON_INGOT, FrugalItems.IRON_HAMMER.get(), "iron", consumer);
        hammerRecipe(Items.GOLD_INGOT, FrugalItems.GOLDEN_HAMMER.get(), "gold", consumer);
        hammerRecipe(Items.DIAMOND, FrugalItems.DIAMOND_HAMMER.get(), "diamond", consumer);
        hammerRecipe(ItemTags.STONE_TOOL_MATERIALS, FrugalItems.STONE_HAMMER.get(), "stone", consumer);
        hammerRecipe(ItemTags.PLANKS, FrugalItems.WOODEN_HAMMER.get(), "wooden", consumer);

        upgradeRecipe(Items.NETHERITE_INGOT, FrugalItems.DIAMOND_HAMMER.get(), FrugalItems.NETHERITE_HAMMER.get(), "netherite", "hammer", consumer);

        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, FrugalItems.IRON_HAMMER.get(), FrugalItems.ROSE_GOLD_HAMMER.get(), "rose", "hammer", consumer);
        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_AXE, FrugalItems.ROSE_GOLD_AXE.get(), "rose", "axe", consumer);
        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_SHOVEL, FrugalItems.ROSE_GOLD_SHOVEL.get(), "rose", "shovel", consumer);
        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_HOE, FrugalItems.ROSE_GOLD_HOE.get(), "rose", "hoe", consumer);
        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_PICKAXE, FrugalItems.ROSE_GOLD_PICKAXE.get(), "rose", "pickaxe", consumer);
        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_SWORD, FrugalItems.ROSE_GOLD_SWORD.get(), "rose", "sword", consumer);

        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_BOOTS, FrugalItems.ROSE_GOLD_BOOTS.get(), "rose", "boots", consumer);
        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_CHESTPLATE, FrugalItems.ROSE_GOLD_CHESTPLATE.get(), "rose", "chestplate", consumer);
        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_LEGGINGS, FrugalItems.ROSE_GOLD_LEGGINGS.get(), "rose", "leggings", consumer);
        upgradeRecipe(ForgeTags.ROSE_GOLD_INGOT, Items.IRON_HELMET, FrugalItems.ROSE_GOLD_HELMET.get(), "rose", "helmet", consumer);
        threeByThreeRecipe(FrugalItems.ROSE_GOLD_INGOT.get(), FrugalItems.ROSE_GOLD_BLOCK.get().asItem(), "rose", consumer);
        singleItem(FrugalItems.ROSE_GOLD_BLOCK.get().asItem(), FrugalItems.ROSE_GOLD_INGOT.get(), 9, "materials/rose/", consumer);

        TwoItem(ModTags.MORTARS, Ingredient.of(Items.QUARTZ, Items.ANDESITE, Items.DIORITE, FrugalItems.SILICA_STONE.get().asItem(), FrugalItems.SILICA_COBBLESTONE.get().asItem()), FrugalItems.SILICA_DUST.get(), 1, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.GRANITE), FrugalItems.SILICA_DUST.get(), 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.QUARTZ_BLOCK), FrugalItems.SILICA_DUST.get(), 4, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.GRAVEL), Items.FLINT, 1, "mortaring/", "mortar", consumer);

        TwoItem(ModTags.MORTARS, Ingredient.of(Items.BONE), Items.BONE_MEAL, 4, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.GLOW_INK_SAC), Items.GLOWSTONE_DUST, 1, "mortaring/", "mortar", consumer);

        TwoItem(ModTags.MORTARS, Ingredient.of(Items.INK_SAC, Items.WITHER_ROSE), Items.BLACK_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.COAL, Items.CHARCOAL), Items.BLACK_DYE, 1, "mortaring/", "mortar", consumer);

        TwoItem(ModTags.MORTARS, Ingredient.of(Items.LAPIS_LAZULI, Items.CORNFLOWER), Items.BLUE_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.LILY_OF_THE_VALLEY), Items.WHITE_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.ORANGE_TULIP), Items.ORANGE_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.ALLIUM), Items.MAGENTA_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.LILAC), Items.MAGENTA_DYE, 4, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.BLUE_ORCHID), Items.LIGHT_BLUE_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.DANDELION), Items.YELLOW_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.SUNFLOWER), Items.YELLOW_DYE, 4, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.PINK_TULIP), Items.PINK_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.PEONY), Items.PINK_DYE, 4, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.OXEYE_DAISY, Items.AZURE_BLUET, Items.WHITE_TULIP), Items.LIGHT_GRAY_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.COCOA_BEANS), Items.BROWN_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.RED_TULIP, Items.BEETROOT, Items.POPPY), Items.RED_DYE, 2, "mortaring/", "mortar", consumer);
        TwoItem(ModTags.MORTARS, Ingredient.of(Items.ROSE_BUSH), Items.RED_DYE, 4, "mortaring/", "mortar", consumer);

        threeByThreeRecipe(FrugalItems.COPPER_NUGGET.get(), Items.COPPER_INGOT, "copper", consumer);
        singleItem(Items.COPPER_INGOT, FrugalItems.COPPER_NUGGET.get(), 9, "materials/copper/", consumer);
        singleItem(Items.GRASS, FrugalItems.FIBER.get(), 1, "", consumer);
        singleItem(Items.TALL_GRASS, FrugalItems.FIBER.get(), 2, "", consumer);
        ShapelessRecipeBuilder.shapeless(FrugalItems.COPPER_STARTER.get(), 1)
                .requires(Items.COPPER_INGOT)
                .requires(Items.FLINT)
                .unlockedBy("has_copper", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
                .save(consumer, new ResourceLocation(Frugality.MODID, "copper_starter"));

        ShapedRecipeBuilder.shaped(FrugalItems.WOODEN_MORTAR.get(), 1)
                .pattern("  S")
                .pattern("BFB")
                .pattern(" B ")
                .define('S', Items.STICK)
                .define('F', Items.FLINT)
                .define('B', ItemTags.PLANKS)
                .unlockedBy("has_planks", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ItemTags.PLANKS).build()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "wooden_mortar"));

        ShapedRecipeBuilder.shaped(FrugalItems.STONE_MORTAR.get(), 1)
                .pattern("  S")
                .pattern("BFB")
                .pattern(" B ")
                .define('S', Items.STICK)
                .define('F', Items.FLINT)
                .define('B', ItemTags.STONE_CRAFTING_MATERIALS)
                .unlockedBy("has_stone", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ItemTags.STONE_CRAFTING_MATERIALS).build()))
                .save(consumer, new ResourceLocation(Frugality.MODID, "stone_mortar"));

        ShapedRecipeBuilder.shaped(FrugalItems.OBSIDIAN_MORTAR.get(), 1)
                .pattern("  S")
                .pattern("BFB")
                .pattern(" B ")
                .define('S', Items.STICK)
                .define('F', Items.FLINT)
                .define('B', Items.OBSIDIAN)
                .unlockedBy("has_obsidian", InventoryChangeTrigger.TriggerInstance.hasItems(Items.OBSIDIAN))
                .save(consumer, new ResourceLocation(Frugality.MODID, "obsidian_mortar"));

        //threeByThreeRecipe(FrugalItems.SMALL_RAW_COPPER.get(), Items.RAW_COPPER, "copper", consumer);
        //threeByThreeRecipe(FrugalItems.SMALL_RAW_IRON.get(), Items.RAW_IRON, "iron", consumer);
        //threeByThreeRecipe(FrugalItems.SMALL_RAW_GOLD.get(), Items.RAW_GOLD, "gold", consumer);
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

    private static void upgradeRecipe(TagKey<Item> upgradeMaterial, ItemLike upgradeItem, Item newItem, String material, String type, Consumer<FinishedRecipe> consumer){
        UpgradeRecipeBuilder.smithing(Ingredient.of(upgradeItem), Ingredient.of(upgradeMaterial), newItem)
                .unlocks("has_" + upgradeMaterial.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(upgradeMaterial).build()))
                .save(consumer, Frugality.MODID + ":materials/" + material + "/" + material + "_" + type + "_from_smithing");
    }

    private static void threeByThreeRecipe(ItemLike input, ItemLike result, String material, Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(result, 1)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', input)
                .unlockedBy("has_" + input.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(input))
                .save(consumer, new ResourceLocation(Frugality.MODID, "materials/" + material + "/" + result.toString()));
    }

    private static void twoByTwoRecipe(ItemLike input, ItemLike result, String folder, Consumer<FinishedRecipe> consumer){
        ShapedRecipeBuilder.shaped(result, 1)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .define('A', input)
                .unlockedBy("has_" + input.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(input))
                .save(consumer, new ResourceLocation(Frugality.MODID, folder + result.toString()));
    }

    private static void singleItem(Item input, ItemLike result, int num, String material, Consumer<FinishedRecipe> consumer){
        ShapelessRecipeBuilder.shapeless(result, num)
                .requires(input)
                .unlockedBy("has_" + input.toString(), InventoryChangeTrigger.TriggerInstance.hasItems(input))
                .save(consumer, new ResourceLocation(Frugality.MODID, material + result.toString() + "_from_" + input.toString()));
    }

    private static void TwoItem(Item input1, Ingredient input2, ItemLike result, int num, String folder, String material, Consumer<FinishedRecipe> consumer){
        ShapelessRecipeBuilder.shapeless(result, num)
                .requires(input1)
                .requires(input2)
                .unlockedBy("has_" + material, InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(input1).build()))
                .save(consumer, new ResourceLocation(Frugality.MODID, folder + result.toString()));
    }

    private static void TwoItem(TagKey<Item> input1, Ingredient input2, ItemLike result, int num, String folder, String material, Consumer<FinishedRecipe> consumer){
        ShapelessRecipeBuilder.shapeless(result, num)
                .requires(input1)
                .requires(input2)
                .unlockedBy("has_" + material, InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(input1).build()))
                .save(consumer, new ResourceLocation(Frugality.MODID, folder + result.toString() + "_" + num + "x"));
    }


}
