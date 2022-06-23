package com.umbriel.frugality.init;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import com.umbriel.frugality.util.recipes.CrushingBlockRecipe;
import com.umbriel.frugality.util.recipes.MelterBlockFluidRecipe;
import com.umbriel.frugality.util.recipes.MelterBlockItemRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Frugality.MODID);

    public static void init() {
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static RegistryObject<RecipeSerializer<CauldronRecipe>> WASHING = RECIPE_SERIALIZERS.register("washing", ()-> CauldronRecipe.SERIALIZER);
    public static RegistryObject<RecipeSerializer<CrushingBlockRecipe>> CRUSHING = RECIPE_SERIALIZERS.register("crushing", ()-> CrushingBlockRecipe.SERIALIZER);

    public static RecipeType<CauldronRecipe> cauldronRecipeType = RecipeType.register(Frugality.MODID + ":washing");
    public static RecipeType<CrushingBlockRecipe> crushingBlockRecipeType = RecipeType.register(Frugality.MODID + ":crushing");
    public static RecipeType<MelterBlockItemRecipe> melterBlockItemRecipeType = RecipeType.register(Frugality.MODID + ":melting_item");
    public static RecipeType<MelterBlockFluidRecipe> melterBlockFluidRecipeType = RecipeType.register(Frugality.MODID + ":melting_fluid");

}
