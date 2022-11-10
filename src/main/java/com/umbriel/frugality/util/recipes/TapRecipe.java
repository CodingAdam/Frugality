package com.umbriel.frugality.util.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.umbriel.frugality.item.ChanceItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import static com.umbriel.frugality.init.FrugalRecipes.tapRecipeType;
import static com.umbriel.frugality.init.FrugalRecipes.thermalRecipeType;


public class TapRecipe extends FrugalRecipe {

    public static final TapRecipe.Serializer SERIALIZER = new TapRecipe.Serializer();

    public TapRecipe(ResourceLocation identifier, String group, Ingredient input, Ingredient output) {
        super(identifier, group, input, output, null, null, 0, 0, 0, 0, false);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TapRecipe.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return tapRecipeType.get();
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<TapRecipe> {

        @Override
        public TapRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            final Ingredient input = Ingredient.fromJson(json.get("input"));
            final Ingredient output = Ingredient.fromJson(json.get("output"));
            return new TapRecipe(recipeId, group, input, output);
        }

        @Nullable
        @Override
        public TapRecipe fromNetwork(ResourceLocation recipe, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient inputIn = Ingredient.fromNetwork(buffer);
            Ingredient inputOut = Ingredient.fromNetwork(buffer);
            return new TapRecipe(recipe, groupIn, inputIn, inputOut);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TapRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.ingredient1.toNetwork(buffer);
            recipe.ingredient2.toNetwork(buffer);
        }
    }

}

