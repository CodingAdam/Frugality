package com.umbriel.frugality.compact.jei.category;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.plugins.vanilla.cooking.AbstractCookingCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SmeltingRecipe;


public class BrickSmeltingCategory extends AbstractCookingCategory<SmeltingRecipe> {

    public BrickSmeltingCategory(IGuiHelper guiHelper) {
        super(guiHelper, FrugalItems.BRICK_FURNACE.get(), "gui.jei.category.smelting", (int)(100 * 1.3));
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Frugality.MODID, "smelting");
    }

    @Override
    public Class<? extends SmeltingRecipe> getRecipeClass() {
        return SmeltingRecipe.class;
    }
}
