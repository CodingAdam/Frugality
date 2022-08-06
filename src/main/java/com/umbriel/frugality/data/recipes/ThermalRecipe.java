package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.data.builder.CrushingRecipeBuilder;
import com.umbriel.frugality.data.builder.ThermalRecipeBuilder;
import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.util.tags.ForgeTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class ThermalRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        recipes(consumer);
    }

    private static void recipes(Consumer<FinishedRecipe> consumer){
        ThermalRecipeBuilder.crushingRecipe(Ingredient.of(Items.STONE, Items.COBBLESTONE,
                Items.DEEPSLATE, FrugalItems.SILICA_COBBLESTONE.get(), FrugalItems.SILICA_STONE.get()), 1)
                .addFluid(new FluidStack(Fluids.LAVA, 1000))
                .save(consumer, new ResourceLocation(Frugality.MODID + ":thermal/" + "lava"));

        ThermalRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.LOGS_THAT_BURN), 1)
                .addDrop(FrugalItems.CHARRED_LOG.get(), 1)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":thermal/" + "charred_log"));

        ThermalRecipeBuilder.crushingRecipe(Ingredient.of(Items.MAGMA_BLOCK), 2)
                .addDrop(Items.OBSIDIAN, 1)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":thermal/" + "obsidian"));

        ThermalRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.TERRACOTTA), 2)
                .addDrop(Items.DRIPSTONE_BLOCK, 1)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":thermal/" + "dripstone"));

        ThermalRecipeBuilder.crushingRecipe(Ingredient.of(Items.STONE, Items.COBBLESTONE,
                Items.DEEPSLATE, FrugalItems.SILICA_COBBLESTONE.get(), FrugalItems.SILICA_STONE.get()), 3)
                .addDrop(Items.NETHERRACK, 1)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":thermal/" + "netherrack"));

        ThermalRecipeBuilder.crushingRecipe(Ingredient.of(Items.SAND), 3)
                .addDrop(Items.SOUL_SAND, 1)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":thermal/" + "soul_sand"));
    }
}
