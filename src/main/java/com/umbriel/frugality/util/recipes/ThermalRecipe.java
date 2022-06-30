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
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.umbriel.frugality.init.ModRecipes.thermalRecipeType;


public class ThermalRecipe implements Recipe<RecipeWrapper> {

    public static final ThermalRecipe.Serializer SERIALIZER = new ThermalRecipe.Serializer();

    private final ResourceLocation identifier;
    private final String group;
    private final Ingredient input;
    private final FluidStack fluidOutput;
    private final NonNullList<ChanceItem> itemOutputs;
    private final int stoneType;

    public ThermalRecipe(ResourceLocation identifier, String group, Ingredient input, FluidStack fluidOutput, NonNullList<ChanceItem> itemOutputs, int stoneType) {
        this.identifier = identifier;
        this.group = group;
        this.input = input;
        this.fluidOutput = fluidOutput;
        this.itemOutputs = itemOutputs;
        this.stoneType = stoneType;
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

    public int getStoneType(){
        return stoneType;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    public String getGroup() {
        return this.group;
    }

    public boolean doesMatch(ItemStack item) {
        return this.input.test(item);
    }

    public List<ItemStack> getOutputsAsItems() {
        List<ItemStack> items = new ArrayList<>();
        for (ChanceItem output : itemOutputs){
            ItemStack stack = output.getItem();
            if (!stack.isEmpty())
                items.add(stack);
        }
        return items;
    }

    public FluidStack getFluidResult() {
        return this.fluidOutput;
    }

    public NonNullList<ChanceItem> getItemResult() {
        return this.itemOutputs;
    }

    public List<ItemStack> rollOutputs() {
        List<ItemStack> results = new ArrayList<>();
        NonNullList<ChanceItem> rollableResults = getItemResult();
        for (ChanceItem output : rollableResults) {
            ItemStack stack = output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ThermalRecipe.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return thermalRecipeType;
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ThermalRecipe> {

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
        public static FluidStack deserializeFluid(JsonObject jsonObject){
            String name = GsonHelper.getAsString(jsonObject, "name");
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(name));
            if(fluid == null){
                return new FluidStack(Fluids.EMPTY, 1000);
            }
            return new FluidStack(fluid, 1000);
        }

        @Override
        public ThermalRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            final Ingredient input = Ingredient.fromJson(json.get("input"));
            final FluidStack fluid = json.has("fluid") ? deserializeFluid(GsonHelper.getAsJsonObject(json, "fluid")) : null;
            final NonNullList<ChanceItem> items = json.has("results") ? readItems(json.get("results")) : NonNullList.create();
            final int type = GsonHelper.getAsInt(json, "stoneType", 1);
            return new ThermalRecipe(recipeId, group, input, fluid, items, type);
        }

        @Nullable
        @Override
        public ThermalRecipe fromNetwork(ResourceLocation recipe, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient inputIn = Ingredient.fromNetwork(buffer);
            FluidStack fluidIn = FluidStack.readFromPacket(buffer);
            final NonNullList<ChanceItem> resultsIn = readItemStackArray(buffer);
            final int type = buffer.readInt();
            return new ThermalRecipe(recipe, groupIn, inputIn, fluidIn, resultsIn, type);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ThermalRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.input.toNetwork(buffer);
            recipe.fluidOutput.writeToPacket(buffer);
            buffer.writeInt(recipe.stoneType);
            writeItemStackArray(buffer, recipe.itemOutputs);
        }
    }

}

