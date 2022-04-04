package com.umbriel.frugality.util.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.umbriel.frugality.init.ModRecipes;
import com.umbriel.frugality.item.ChanceItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class CauldronRecipe implements Recipe<Inventory> {

    public static final RecipeSerializer<CauldronRecipe> SERIALIZER = new Serializer();

    private final ResourceLocation identifier;

    private final Ingredient input;
    private final int level;
    //private final int duration;
    private final NonNullList<ChanceItem> outputs;
    private boolean hidden;

    public CauldronRecipe(ResourceLocation identifier, Ingredient input, int level, NonNullList<ChanceItem> outputs, boolean hidden) {
        this.identifier = identifier;
        this.input = input;
        this.level = level;
        //this.duration = duration;
        this.outputs = outputs; //Special Json Element required
        this.hidden = hidden;
    }

    @Override
    public ResourceLocation getId () {

        return this.identifier;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return SERIALIZER;

    }

    @Override
    public RecipeType<?> getType() {

        return ModRecipes.cauldronRecipeType;

    }

    public int getFluidLevel() {

        return this.level;
    }

    public Ingredient getInput() {

        return this.input;
    }

    public NonNullList<ChanceItem> getOutputs() {

        return this.outputs;
    }

    public List<ItemStack> getOutputsAsItems() {
        List<ItemStack> items = new ArrayList<>();
        for (ChanceItem output : outputs){
            ItemStack stack = output.getItem();
            if (!stack.isEmpty())
                items.add(stack);
        }
        return items;
    }

    public List<ItemStack> rollOutputs() {
        List<ItemStack> results = new ArrayList<>();
        NonNullList<ChanceItem> rollableResults = getOutputs();
        for (ChanceItem output : rollableResults) {
            ItemStack stack = output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }
//    public int getDuration() {
//
//        return this.duration;
//    }

    @Override
    public boolean matches (Inventory inv, Level level) {
        return false;
    }

    public boolean doesMatch(ItemStack item, int level) {

        return this.input.test(item) && this.level <= level;
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

    @Override
    public ItemStack assemble(Inventory p_44001_) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
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
            //final int duration = GsonHelper.getAsInt(json, "duration", 1);
            final NonNullList<ChanceItem> results = json.has("results") ? readItems(json.get("results")) : NonNullList.create();
            final boolean hidden = GsonHelper.getAsBoolean(json, "hidden", false);
            return new CauldronRecipe(recipeId, input, fluidLevel, results, hidden);
        }

        @Override
        public CauldronRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {

            final Ingredient input = Ingredient.fromNetwork(buffer);
            final int fluidLevel = buffer.readInt();
            //final int duration = buffer.readInt();
            final NonNullList<ChanceItem> results = readItemStackArray(buffer);
            final boolean hidden = buffer.readBoolean();
            return new CauldronRecipe(recipeId, input, fluidLevel, results, hidden);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CauldronRecipe recipe) {

            recipe.input.toNetwork(buffer);
            buffer.writeInt(recipe.level);
            //buffer.writeInt(recipe.duration);
            writeItemStackArray(buffer, recipe.outputs);
            buffer.writeBoolean(recipe.hidden);
        }
    }


}
