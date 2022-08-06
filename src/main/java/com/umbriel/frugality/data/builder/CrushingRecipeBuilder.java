package com.umbriel.frugality.data.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.umbriel.frugality.init.FrugalRecipes;
import com.umbriel.frugality.item.ChanceItem;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CrushingRecipeBuilder {

    private final Ingredient input;
    private final Ingredient tool;
    private final int hits;
    private final NonNullList<ChanceItem> results;

    private CrushingRecipeBuilder(Ingredient input, Ingredient tool, int hits, NonNullList<ChanceItem> outputs){
        this.input = input;
        this.tool = tool;
        this.hits = hits;
        this.results = outputs;
    }

    public static CrushingRecipeBuilder crushingRecipe(Ingredient input, Ingredient tool, int hits){
        return new CrushingRecipeBuilder(input, tool, hits, NonNullList.create());
    }

    public CrushingRecipeBuilder addDrop(ItemLike item, int count){
        return this.addDrop(item, count, 1);
    }

    public CrushingRecipeBuilder addDrop(ItemLike item, int count, float chance){
        this.results.add(new ChanceItem(new ItemStack(item, count), chance));
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation location){
        consumer.accept(new Result(location, this.input, this.tool, this.hits, this.results));
    }

    public static class Result implements FinishedRecipe{

        private ResourceLocation location;
        private Ingredient input;
        private Ingredient tool;
        private int hits;
        private final NonNullList<ChanceItem> results;

        private Result(ResourceLocation location, Ingredient input, Ingredient tool, int hits, NonNullList<ChanceItem> outputs){
            this.location = location;
            this.input = input;
            this.tool = tool;
            this.hits = hits;
            this.results = outputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", this.input.toJson());

            json.add("tool", this.tool.toJson());
            json.addProperty("hits", this.hits);

            JsonArray results = new JsonArray();
            for (ChanceItem result : this.results) {
                JsonObject jsonobject = new JsonObject();
                jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(result.getStack().getItem()).toString());
                if (result.getStack().getCount() > 1) {
                    jsonobject.addProperty("count", result.getStack().getCount());
                }
                if (result.getChance() < 1) {
                    jsonobject.addProperty("chance", result.getChance());
                }
                results.add(jsonobject);
            }
            json.add("results", results);
        }


        @Override
        public ResourceLocation getId() {
            return this.location;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return FrugalRecipes.CRUSHING.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
