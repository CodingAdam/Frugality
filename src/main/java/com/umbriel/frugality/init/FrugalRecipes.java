package com.umbriel.frugality.init;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import com.umbriel.frugality.util.recipes.CrushingRecipe;
import com.umbriel.frugality.util.recipes.ThermalRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrugalRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Frugality.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE.key(), Frugality.MODID);

    public static void init() {
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static RegistryObject<RecipeSerializer<CauldronRecipe>> WASHING = RECIPE_SERIALIZERS.register("washing", ()-> CauldronRecipe.SERIALIZER);
    public static RegistryObject<RecipeSerializer<CrushingRecipe>> CRUSHING = RECIPE_SERIALIZERS.register("crushing", ()-> CrushingRecipe.SERIALIZER);
    public static RegistryObject<RecipeSerializer<ThermalRecipe>> THERMAL_STONE = RECIPE_SERIALIZERS.register("thermal_stone", ()-> ThermalRecipe.SERIALIZER);

    public static RegistryObject<RecipeType<CauldronRecipe>> cauldronRecipeType = RECIPE_TYPES.register("washing", () -> registerType("washing"));
    public static RegistryObject<RecipeType<CrushingRecipe>> crushingBlockRecipeType = RECIPE_TYPES.register("crushing", () -> registerType("crushing"));
    public static RegistryObject<RecipeType<ThermalRecipe>> thermalRecipeType = RECIPE_TYPES.register("thermal_stone",  () -> registerType("thermal_stone"));
    public static RegistryObject<RecipeType<ThermalRecipe>> tapRecipeType = RECIPE_TYPES.register("tap",  () -> registerType("tap"));

    public static <T extends Recipe<?>> RecipeType<T> registerType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return Frugality.MODID + ":" + identifier;
            }
        };
    }
}
