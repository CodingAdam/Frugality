package com.umbriel.frugality.jei;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModRegistry;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.plugins.vanilla.cooking.AbstractCookingCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.BlastingRecipe;


public class BrickBlastingCategory extends AbstractCookingCategory<BlastingRecipe> {

    public BrickBlastingCategory(IGuiHelper guiHelper) {
        super(guiHelper, ModRegistry.BRICK_BLAST_FURNACE.get(), "gui.jei.category.blasting", (int)(100 * 1.3));
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Frugality.MODID, "blasting");
    }

    @Override
    public Class<? extends BlastingRecipe> getRecipeClass() {
        return BlastingRecipe.class;
    }
}
