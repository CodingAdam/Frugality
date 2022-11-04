package com.umbriel.frugality.data.recipes;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.data.builder.ThermalRecipeBuilder;
import com.umbriel.frugality.data.builder.WashingRecipeBuilder;
import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Consumer;

public class WashingRecipe {

    public static void register(Consumer<FinishedRecipe> consumer){
        recipes(consumer);
    }

    private static void recipes(Consumer<FinishedRecipe> consumer){
        WashingRecipeBuilder.washingRecipe(Ingredient.of(FrugalItems.THERMAL_STONE.get()), 2, 3)
                .addDrop(FrugalItems.CHILLED_STONE.get(), 1)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "chilled_stone"));

        WashingRecipeBuilder.washingRecipe(Ingredient.of(FrugalItems.CHILLED_STONE.get()), 2, 3)
                .addDrop(FrugalItems.THERMAL_STONE.get(), 1)
                .addDrop(Items.SNOWBALL, 3)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "snow_balls"));

        WashingRecipeBuilder.washingRecipe(Ingredient.of(Items.DIRT), 1, 1)
                .addDrop(FrugalItems.SILICA.get(), 1, 0.55f)
                .addDrop(FrugalItems.FIBER.get(), 1, 0.25f)
                .addDrop(Items.WHEAT_SEEDS, 1, 0.15f)
                .addDrop(Items.FLINT, 1, 0.10f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "dirt"));

        WashingRecipeBuilder.washingRecipe(Ingredient.of(Items.WATER_BUCKET), 3, 3)
                .addDrop(Items.BUCKET, 1)
                .addDrop(Items.OBSIDIAN, 1)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "obsidian"));

        WashingRecipeBuilder.washingRecipe(Ingredient.of(Items.GRAVEL), 1, 1)
                .addDrop(Items.FLINT, 1, 0.55f)
                .addDrop(Items.IRON_NUGGET, 1, 0.05f)
                .addDrop(Items.IRON_NUGGET, 2, 0.01f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "gravel"));

        WashingRecipeBuilder.washingRecipe(Ingredient.of(Items.RED_SAND), 1, 1)
                .addDrop(Items.DEAD_BUSH, 1, 0.85f)
                .addDrop(Items.GOLD_NUGGET, 1, 0.05f)
                .addDrop(Items.GOLD_NUGGET, 2, 0.01f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "red_sand"));

        WashingRecipeBuilder.washingRecipe(Ingredient.of(FrugalItems.HEATED_STONE.get()), 1, 1)
                .addDrop(FrugalItems.THERMAL_STONE.get(), 1)
                .addDrop(FrugalItems.SALT.get(), 1)
                .addDrop(FrugalItems.SALT.get(), 2, 0.25f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "salt"));

        WashingRecipeBuilder.washingRecipe(Ingredient.of(Items.SOUL_SAND), 1, 1)
            .addDrop(Items.QUARTZ, 1, 0.55f)
                .addDrop(Items.GOLD_NUGGET, 1, 0.05f)
                .addDrop(Items.GOLD_NUGGET, 2, 0.1f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "soul_sand"));

        WashingRecipeBuilder.washingRecipe(Ingredient.of(Items.SAND), 1, 1)
                .addDrop(Items.BONE_MEAL, 1, 0.85f)
                .addDrop(Items.CLAY_BALL, 1, 0.25f)
                .addDrop(Items.KELP, 1, 0.05f)
                .save(consumer, new ResourceLocation(Frugality.MODID + ":washing/" + "sand"));
    }
}
