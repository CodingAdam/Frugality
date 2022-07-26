package com.umbriel.frugality.init;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import com.umbriel.frugality.util.recipes.CrushingRecipe;
import com.umbriel.frugality.util.recipes.ThermalRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrugalRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Frugality.MODID);

    public static void init() {
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static RegistryObject<RecipeSerializer<CauldronRecipe>> WASHING = RECIPE_SERIALIZERS.register("washing", ()-> CauldronRecipe.SERIALIZER);
    public static RegistryObject<RecipeSerializer<CrushingRecipe>> CRUSHING = RECIPE_SERIALIZERS.register("crushing", ()-> CrushingRecipe.SERIALIZER);
    public static RegistryObject<RecipeSerializer<ThermalRecipe>> THERMAL_STONE = RECIPE_SERIALIZERS.register("thermal_stone", ()-> ThermalRecipe.SERIALIZER);

    public static RecipeType<CauldronRecipe> cauldronRecipeType = RecipeType.register(Frugality.MODID + ":washing");
    public static RecipeType<CrushingRecipe> crushingBlockRecipeType = RecipeType.register(Frugality.MODID + ":crushing");
    public static RecipeType<ThermalRecipe> thermalRecipeType = RecipeType.register(Frugality.MODID + ":thermal_stone");

}
