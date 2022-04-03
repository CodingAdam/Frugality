package com.umbriel.frugality.util.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.umbriel.frugality.Frugality;
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
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.umbriel.frugality.init.ModRecipes.crushingBlockRecipeType;


public class CrushingBlockRecipe implements Recipe<RecipeWrapper> {

    public static final CrushingBlockRecipe.Serializer SERIALIZER = new CrushingBlockRecipe.Serializer();
    public static final int MAX_RESULTS = 4;

    private final ResourceLocation identifier;
    private final Ingredient input;
    private final String group;
    private final Ingredient tool;
    private final int hits;
    private final NonNullList<ChanceItem> results;

    public CrushingBlockRecipe(ResourceLocation identifier, String group, Ingredient input, Ingredient tool, int hits, NonNullList<ChanceItem> results) {
        this.identifier = identifier;
        this.group = group;
        this.input = input;
        this.tool = tool;
        this.hits = hits;
        this.results = results;
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

    public Ingredient getTool() {
        return this.tool;
    }


    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return input.test(inv.getItem(0));
    }

    public int getHits(){
        return hits;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return this.results.get(0).getItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.getMaxInputCount();
    }

    @Override
    public ItemStack getResultItem() {
        return this.results.get(0).getItem();
    }

    public String getGroup() {
        return this.group;
    }

    public List<ItemStack> getResults() {
        return getRolledResults().stream().map(ChanceItem::getItem)
                .collect(Collectors.toList());
    }

    public NonNullList<ChanceItem> getRolledResults() {
        return this.results;
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

    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CrushingBlockRecipe.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return crushingBlockRecipeType;
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrushingBlockRecipe> {

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
        public CrushingBlockRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            final Ingredient input = Ingredient.fromJson(json.get("input"));
            final NonNullList<ChanceItem> results = json.has("results") ? readItems(json.get("results")) : NonNullList.create();
            final int hits = GsonHelper.getAsInt(json, "hits", 1);
            final Ingredient tool = Ingredient.fromJson( GsonHelper.getAsJsonObject(json, "tool"));
            return new CrushingBlockRecipe(recipeId, group, input, tool, hits, results);
        }

        @Nullable
        @Override
        public CrushingBlockRecipe fromNetwork(ResourceLocation recipe, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient inputIn = Ingredient.fromNetwork(buffer);
            Ingredient toolIn = Ingredient.fromNetwork(buffer);
            final int hits = buffer.readInt();
            final NonNullList<ChanceItem> resultsIn = readItemStackArray(buffer);
            return new CrushingBlockRecipe(recipe, groupIn, inputIn, toolIn, hits, resultsIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrushingBlockRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.input.toNetwork(buffer);
            recipe.tool.toNetwork(buffer);
            buffer.writeInt(recipe.hits);
            //buffer.writeInt(recipe.duration);
            writeItemStackArray(buffer, recipe.results);
        }
    }

}

