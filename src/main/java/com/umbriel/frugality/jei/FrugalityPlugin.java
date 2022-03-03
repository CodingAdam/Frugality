package com.umbriel.frugality.jei;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.event.RecipeEvents;
import com.umbriel.frugality.init.ModRecipes;
import com.umbriel.frugality.init.ModRegistry;
import com.umbriel.frugality.jei.categorys.CauldronCategory;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Collection;

@JeiPlugin
public class FrugalityPlugin implements IModPlugin {

    @Override
    @NotNull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Frugality.MODID, Frugality.MODID + "_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new BrickBlastingCategory(guiHelper),
                new BrickSmeltingCategory(guiHelper),
                new CauldronCategory(guiHelper, Blocks.CAULDRON)
        );
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModRegistry.BRICK_FURNACE.get()),
                VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeCatalyst(new ItemStack(ModRegistry.BRICK_BLAST_FURNACE.get()),
                VanillaRecipeCategoryUid.BLASTING, VanillaRecipeCategoryUid.FUEL);

        registration.addRecipeCatalyst(new ItemStack(Items.CAULDRON), CauldronCategory.ID);
        registration.addRecipeCatalyst(new ItemStack(ModRegistry.WOODEN_CAULDRON.get()), CauldronCategory.ID);
        registration.addRecipeCatalyst(new ItemStack(ModRegistry.STONE_CAULDRON.get()), CauldronCategory.ID);
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration){
        final Collection<CauldronRecipe> recipes = RecipeEvents.getRecipes();
        registration.addRecipes(recipes, CauldronCategory.ID);
    }
}
