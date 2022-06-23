package com.umbriel.frugality.util.recipes;

import com.google.gson.JsonObject;
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

import static com.umbriel.frugality.init.ModRecipes.melterBlockFluidRecipeType;


public class MelterBlockFluidRecipe implements Recipe<RecipeWrapper> {

    public static final MelterBlockFluidRecipe.Serializer SERIALIZER = new MelterBlockFluidRecipe.Serializer();

    private final ResourceLocation identifier;
    private final String group;
    private final Ingredient input;
    private final FluidStack output;
    private final int time;

    public MelterBlockFluidRecipe(ResourceLocation identifier, String group, Ingredient input, FluidStack output, int time) {
        this.identifier = identifier;
        this.group = group;
        this.input = input;
        this.output = output;
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

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    public String getGroup() {
        return this.group;
    }

    public FluidStack getResult() {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MelterBlockFluidRecipe.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return melterBlockFluidRecipeType;
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MelterBlockFluidRecipe> {

        public static FluidStack deserializeFluid(JsonObject jsonObject){
            String name = GsonHelper.getAsString(jsonObject, "fluid");
            return new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(name)), 1);
        }

        @Override
        public MelterBlockFluidRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String group = GsonHelper.getAsString(json, "group", "");
            final Ingredient input = Ingredient.fromJson(json.get("input"));
            final FluidStack result = deserializeFluid(GsonHelper.getAsJsonObject(json, "result"));
            final int time = GsonHelper.getAsInt(json, "time", 1);
            return new MelterBlockFluidRecipe(recipeId, group, input, result, time);
        }

        @Nullable
        @Override
        public MelterBlockFluidRecipe fromNetwork(ResourceLocation recipe, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient inputIn = Ingredient.fromNetwork(buffer);
            FluidStack resultIn = FluidStack.readFromPacket(buffer);
            final int time = buffer.readInt();
            return new MelterBlockFluidRecipe(recipe, groupIn, inputIn, resultIn, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MelterBlockFluidRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.input.toNetwork(buffer);
            recipe.output.writeToPacket(buffer);
            buffer.writeInt(recipe.time);
        }
    }

}

