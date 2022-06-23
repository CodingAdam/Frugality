package com.umbriel.frugality.util.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.umbriel.frugality.item.ChanceItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.umbriel.frugality.init.ModRecipes.melterBlockItemRecipeType;


public class MelterBlockItemRecipe implements Recipe<RecipeWrapper> {

    public static final MelterBlockItemRecipe.Serializer SERIALIZER = new MelterBlockItemRecipe.Serializer();

    private final ResourceLocation identifier;
    private final String group;
    private final Ingredient input;
    private final NonNullList<ChanceItem> results;
    private final int time;

    public MelterBlockItemRecipe(ResourceLocation identifier, String group, Ingredient input, NonNullList<ChanceItem> results, int time) {
        this.identifier = identifier;
        this.group = group;
        this.input = input;
        this.results = results;
        this.time = time;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.identifier;
    }

    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return input.test(inv.getItem(0));
    }

    public int getTime(){
        return time;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    public List<ItemStack> rollOutputs() {
        List<ItemStack> results = new ArrayList<>();
        NonNullList<ChanceItem> rollableResults = getRolledResults();
        for (ChanceItem output : rollableResults) {
            ItemStack stack = output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }
    @Override
    public ItemStack getResultItem() {
        return null;
    }

    public String getGroup() {
        return this.group;
    }

    public NonNullList<ChanceItem> getRolledResults() {
        return this.results;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MelterBlockItemRecipe.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return melterBlockItemRecipeType;
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MelterBlockItemRecipe> {

        public static NonNullList<ChanceItem> readItems (JsonElement element) {
            final NonNullList<ChanceItem> items = NonNullList.create();
            if (element.isJsonArray()) {
                for (final JsonElement subElement : element.getAsJsonArray()) {
                    items.add(ChanceItem.fromJson(subElement));
                }
            }
            else {
                items.add(ChanceItem.fromJson(element));
            }
            return items;
        }

        public static NonNullList<ChanceItem> readItemStackArray(FriendlyByteBuf buffer) {
            final NonNullList<ChanceItem> items = NonNullList.create();
            int size = buffer.readVarInt();
            for (int i = 0; i < size; i++) {
                items.add(ChanceItem.read(buffer));
            }
            return items;
        }

        public static void writeItemStackArray(FriendlyByteBuf buffer, NonNullList<ChanceItem> items) {
            buffer.writeInt(items.size());
            for (final ChanceItem stack : items) {
                stack.write(buffer);
            }
        }

        @Override
        public MelterBlockItemRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            final Ingredient input = Ingredient.fromJson(json.get("input"));
            final NonNullList<ChanceItem> results = json.has("results") ? readItems(json.get("results")) : NonNullList.create();
            final int time = GsonHelper.getAsInt(json, "time", 1);
            return new MelterBlockItemRecipe(recipeId, group, input, results, time);
        }

        @Nullable
        @Override
        public MelterBlockItemRecipe fromNetwork(ResourceLocation recipe, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient inputIn = Ingredient.fromNetwork(buffer);
            final NonNullList<ChanceItem> resultsIn = readItemStackArray(buffer);
            final int time = buffer.readInt();
            return new MelterBlockItemRecipe(recipe, groupIn, inputIn, resultsIn, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MelterBlockItemRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.input.toNetwork(buffer);
            buffer.writeInt(recipe.time);
            writeItemStackArray(buffer, recipe.results);
        }
    }

}

