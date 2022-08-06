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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class WashingRecipeBuilder {

    private final Ingredient input;
    private final int fluidType;
    private final int fluidLevel;
    private final NonNullList<ChanceItem> itemOutputs;

    private WashingRecipeBuilder(Ingredient input, int fluidType, int fluidLevel, NonNullList<ChanceItem> outputs){
        this.input = input;
        this.fluidType = fluidType;
        this.fluidLevel = fluidLevel;
        this.itemOutputs = outputs;
    }

    public static WashingRecipeBuilder washingRecipe(Ingredient input, int fluidType, int fluidLevel){
        return new WashingRecipeBuilder(input, fluidType, fluidLevel, NonNullList.create());
    }

    public WashingRecipeBuilder addDrop(ItemLike item, int count){
        return this.addDrop(item, count, 1);
    }

    public WashingRecipeBuilder addDrop(ItemLike item, int count, float chance){
        this.itemOutputs.add(new ChanceItem(new ItemStack(item, count), chance));
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation location){
        consumer.accept(new Result(location, this.input, this.fluidType, this.fluidLevel, this.itemOutputs));
    }

    public static class Result implements FinishedRecipe{

        private ResourceLocation location;
        private final Ingredient input;
        private final int fluidType;
        private final int fluidLevel;
        private final NonNullList<ChanceItem> itemOutputs;


        private Result(ResourceLocation location, Ingredient input, int fluidType, int fluidLevel, NonNullList<ChanceItem> outputs){
            this.location = location;
            this.input = input;
            this.fluidType = fluidType;
            this.fluidLevel = fluidLevel;
            this.itemOutputs = outputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", this.input.toJson());
            json.addProperty("fluidType", this.fluidType);
            json.addProperty("fluidLevel", this.fluidLevel);

            JsonArray results = new JsonArray();
            for (ChanceItem result : this.itemOutputs) {
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
            return FrugalRecipes.WASHING.get();
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
