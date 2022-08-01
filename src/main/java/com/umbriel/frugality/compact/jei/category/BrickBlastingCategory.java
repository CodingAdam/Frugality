package com.umbriel.frugality.compact.jei.category;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.plugins.vanilla.cooking.AbstractCookingCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.BlastingRecipe;


public class BrickBlastingCategory extends AbstractCookingCategory<BlastingRecipe> {

    public BrickBlastingCategory(IGuiHelper guiHelper) {
        super(guiHelper, FrugalItems.BRICK_BLAST_FURNACE.get(), "gui.jei.category.blasting", (int)(100 * 1.3));
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Frugality.MODID, "blasting");
    }

    @Override
    public Class<? extends BlastingRecipe> getRecipeClass() {
        return BlastingRecipe.class;
    }

    @Override
    public RecipeType<BlastingRecipe> getRecipeType() {
        return RecipeTypes.BLASTING;
    }
}
