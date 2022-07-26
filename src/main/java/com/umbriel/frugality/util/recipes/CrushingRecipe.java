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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

import static com.umbriel.frugality.init.FrugalRecipes.crushingBlockRecipeType;


public class CrushingRecipe extends FrugalRecipe {

    public static final CrushingRecipe.Serializer SERIALIZER = new CrushingRecipe.Serializer();

    protected CrushingRecipe(ResourceLocation identifier, String group, Ingredient input, Ingredient tool, NonNullList<ChanceItem> itemOutputs, int hits) {
        super(identifier, group, input, tool, null, itemOutputs, 0, hits, 0, 0, false);
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return this.itemOutputs.get(0).getItem().copy();
    }

    @Override
    public ItemStack getResultItem() {
        return this.itemOutputs.get(0).getItem();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.getMaxInputCount();
    }


    public List<ItemStack> getResults() {
        return getItemResult().stream().map(ChanceItem::getItem)
                .collect(Collectors.toList());
    }


    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CrushingRecipe.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return crushingBlockRecipeType;
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrushingRecipe> {

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
        public CrushingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            final Ingredient input = Ingredient.fromJson(json.get("input"));
            final NonNullList<ChanceItem> results = json.has("results") ? readItems(json.get("results")) : NonNullList.create();
            final int hits = GsonHelper.getAsInt(json, "hits", 1);
            final Ingredient tool = Ingredient.fromJson( GsonHelper.getAsJsonObject(json, "tool"));
            return new CrushingRecipe(recipeId, group, input, tool, results, hits);
        }

        @Nullable
        @Override
        public CrushingRecipe fromNetwork(ResourceLocation recipe, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient inputIn = Ingredient.fromNetwork(buffer);
            Ingredient toolIn = Ingredient.fromNetwork(buffer);
            final int hits = buffer.readInt();
            final NonNullList<ChanceItem> resultsIn = readItemStackArray(buffer);
            return new CrushingRecipe(recipe, groupIn, inputIn, toolIn, resultsIn, hits);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrushingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.input.toNetwork(buffer);
            recipe.tool.toNetwork(buffer);
            buffer.writeInt(recipe.hits);
            writeItemStackArray(buffer, recipe.itemOutputs);
        }
    }

}

