package com.umbriel.frugality.compact.jei;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.compact.jei.category.*;
import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.init.FrugalRecipes;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import com.umbriel.frugality.util.recipes.CrushingRecipe;
import com.umbriel.frugality.util.recipes.ThermalRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

@JeiPlugin
public class FrugalityPlugin implements IModPlugin {

    TranslatableComponent smallOreText = new TranslatableComponent(Frugality.MODID + ".jei.info.small_ores" );
    TranslatableComponent charredShardsText = new TranslatableComponent(Frugality.MODID + ".jei.info.charred_shards" );


    public static final RecipeType<CauldronRecipe> CAULDRON =
            RecipeType.create(Frugality.MODID, "washing", CauldronRecipe.class);

    public static final RecipeType<ThermalRecipe> THERMAL =
            RecipeType.create(Frugality.MODID, "thermal_stone", ThermalRecipe.class);

    public static final RecipeType<CrushingRecipe> CRUSHING =
            RecipeType.create(Frugality.MODID, "crushing", CrushingRecipe.class);



    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Frugality.MODID, Frugality.MODID + "_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new CauldronCategory(guiHelper, Blocks.CAULDRON),
                new CrushingCategory(guiHelper, FrugalItems.CRUSHING_STONE.get()),
                new ThermalCategory(guiHelper, FrugalItems.THERMAL_STONE.get())
        );
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(FrugalItems.BRICK_FURNACE.get()),
                RecipeTypes.SMELTING, RecipeTypes.FUELING);
        registration.addRecipeCatalyst(new ItemStack(FrugalItems.BRICK_BLAST_FURNACE.get()),
                RecipeTypes.BLASTING, RecipeTypes.FUELING);

        registration.addRecipeCatalyst(new ItemStack(Items.CAULDRON), CAULDRON);
        registration.addRecipeCatalyst(new ItemStack(FrugalItems.WOODEN_CAULDRON.get()), CAULDRON);
        registration.addRecipeCatalyst(new ItemStack(FrugalItems.STONE_CAULDRON.get()), CAULDRON);

        registration.addRecipeCatalyst(new ItemStack(FrugalItems.CRUSHING_STONE.get()), CRUSHING);
        registration.addRecipeCatalyst(new ItemStack(FrugalItems.CRUSHING_TERRACOTTA.get()), CRUSHING);

        registration.addRecipeCatalyst(new ItemStack(FrugalItems.THERMAL_STONE.get()), THERMAL);
        registration.addRecipeCatalyst(new ItemStack(FrugalItems.HEATED_STONE.get()), THERMAL);
        registration.addRecipeCatalyst(new ItemStack(FrugalItems.CHILLED_STONE.get()), THERMAL);
        registration.addRecipeCatalyst(new ItemStack(FrugalItems.WARPED_STONE.get()), THERMAL);

    }
    @Override
    public void registerRecipes(IRecipeRegistration registration){
        final List<CauldronRecipe> cauldronRecipes = Minecraft.getInstance().level.getRecipeManager()
                .getAllRecipesFor(FrugalRecipes.cauldronRecipeType.get());
        registration.addRecipes(CAULDRON, cauldronRecipes);
        final List<CrushingRecipe> crushingRecipes = Minecraft.getInstance().level.getRecipeManager()
                .getAllRecipesFor(FrugalRecipes.crushingBlockRecipeType.get());
        registration.addRecipes(CRUSHING, crushingRecipes);

        //registration.addIngredientInfo(new ItemStack(FrugalItems.SMALL_RAW_COPPER.get()), VanillaTypes.ITEM_STACK, smallOreText);
        //registration.addIngredientInfo(new ItemStack(FrugalItems.SMALL_RAW_GOLD.get()), VanillaTypes.ITEM_STACK, smallOreText);
        //registration.addIngredientInfo(new ItemStack(FrugalItems.SMALL_RAW_IRON.get()), VanillaTypes.ITEM_STACK, smallOreText);
        registration.addIngredientInfo(new ItemStack(FrugalItems.CHARRED_LOG.get()), VanillaTypes.ITEM_STACK, charredShardsText);
        registration.addIngredientInfo(new ItemStack(FrugalItems.CHARRED_SHARDS.get()), VanillaTypes.ITEM_STACK, charredShardsText);

        List<ThermalRecipe> thermalRecipes = Minecraft.getInstance().level.getRecipeManager()
                .getAllRecipesFor(FrugalRecipes.thermalRecipeType.get());
        registration.addRecipes(THERMAL, thermalRecipes);
    }
}
