package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.data.builder.CrushingRecipeBuilder;
import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.util.tags.ForgeTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class CrushingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        recipes(consumer);
    }

    private static void recipes(Consumer<FinishedRecipe> consumer){
        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(Items.DRIPSTONE_BLOCK), Ingredient.of(ForgeTags.TOOLS_HAMMERS), 4)
                .addDrop(Items.POINTED_DRIPSTONE, 1, 0.55f)
                .addDrop(Items.POINTED_DRIPSTONE, 1, 0.35f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":crushing/" + "dripstone"));

        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(Items.SMOOTH_BASALT, Items.BASALT), Ingredient.of(ForgeTags.TOOLS_HAMMERS), 3)
                .addDrop(Items.GRAVEL, 1, 0.55f)
                .addDrop(Items.AMETHYST_SHARD, 1, 0.05f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":crushing/" + "basalt"));

        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.TERRACOTTA), Ingredient.of(ForgeTags.TOOLS_HAMMERS), 2)
                .addDrop(Items.RED_SAND, 1, 0.55f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":crushing/" + "terracotta"));

        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(Items.GRAVEL), Ingredient.of(ForgeTags.TOOLS_HAMMERS), 3)
                .addDrop(Items.SAND, 1, 0.75f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":crushing/" + "sand"));

        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(Items.STONE, Items.COBBLESTONE,
                Items.DEEPSLATE, FrugalItems.SILICA_COBBLESTONE.get(), FrugalItems.SILICA_STONE.get()), Ingredient.of(ForgeTags.TOOLS_HAMMERS), 3)
                .addDrop(Items.GRAVEL, 1, 0.85f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":crushing/" + "stone"));

        CrushingRecipeBuilder.crushingRecipe(Ingredient.of(Items.STONE, Items.COBBLESTONE,
                Items.DEEPSLATE, FrugalItems.SILICA_COBBLESTONE.get(), FrugalItems.SILICA_STONE.get()), Ingredient.of(ForgeTags.TOOLS_PICKAXES),7)
                .addDrop(FrugalItems.THERMAL_STONE.get(), 1, 0.70f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":crushing/" + "thermal_stone"));
    }
}
