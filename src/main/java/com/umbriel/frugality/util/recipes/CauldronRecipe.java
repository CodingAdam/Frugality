package com.umbriel.frugality.util.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.umbriel.frugality.init.FrugalRecipes;
import com.umbriel.frugality.item.ChanceItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class CauldronRecipe extends FrugalRecipe {

    public static final RecipeSerializer<CauldronRecipe> SERIALIZER = new Serializer();

    protected CauldronRecipe(ResourceLocation identifier, Ingredient input, NonNullList<ChanceItem> itemOutputs, int type, int level, boolean hidden) {
        super(identifier, null, input, null, null, itemOutputs, 0, 0, type, level, hidden);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return FrugalRecipes.cauldronRecipeType.get();
    }

    public boolean doesMatch(ItemStack item, int level, int type) {
        if(type == 3){
            return this.input.test(item) && this.type == type;
        }
        return this.input.test(item) && this.level <= level && this.type == type;
    }

    public boolean isHidden() {

        return this.hidden;
    }

    public void setHidden (boolean isHidden) {

        this.hidden = isHidden;
    }

    @Override
    public boolean isSpecial() {

        return true;
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CauldronRecipe>  {

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
        public CauldronRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final Ingredient input = Ingredient.fromJson(json.get("input"));
            final int fluidLevel = GsonHelper.getAsInt(json, "fluidLevel", 1);
            final int type = GsonHelper.getAsInt(json, "fillType", 1);
            final NonNullList<ChanceItem> results = json.has("results") ? readItems(json.get("results")) : NonNullList.create();
            final boolean hidden = GsonHelper.getAsBoolean(json, "hidden", false);
            return new CauldronRecipe(recipeId, input, results, type, fluidLevel, hidden);
        }

        @Override
        public CauldronRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            final Ingredient input = Ingredient.fromNetwork(buffer);
            final int fluidLevel = buffer.readInt();
            final int type = buffer.readInt();
            final NonNullList<ChanceItem> results = readItemStackArray(buffer);
            final boolean hidden = buffer.readBoolean();
            return new CauldronRecipe(recipeId, input, results, type, fluidLevel, hidden);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CauldronRecipe recipe) {

            recipe.input.toNetwork(buffer);
            buffer.writeInt(recipe.level);
            buffer.writeInt(recipe.type);
            writeItemStackArray(buffer, recipe.itemOutputs);
            buffer.writeBoolean(recipe.hidden);
        }
    }


}
