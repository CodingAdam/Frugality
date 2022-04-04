package com.umbriel.frugality.jei.categorys;

import com.mojang.blaze3d.vertex.PoseStack;
import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.item.ChanceItem;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.config.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CauldronCategory implements IRecipeCategory<CauldronRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(Frugality.MODID, "cauldron");
    public static final MutableComponent chanceText = new TranslatableComponent("recipe." + Frugality.MODID + ".chance");
    public static final MutableComponent fluidAmountText = new TranslatableComponent("recipe." + Frugality.MODID + ".bucket_amount");

    protected String name;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic slotDrawable;
    private IDrawable arrow;
    private IDrawable plusSign;


    public CauldronCategory(IGuiHelper guiHelper, Block icon) {
       this.background = guiHelper.createBlankDrawable(150, 36);
       this.slotDrawable = guiHelper.getSlotDrawable();
       this.icon = guiHelper.createDrawableIngredient(new ItemStack(icon));
       this.arrow = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 75, 169, 24, 16);
       this.plusSign = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 26, 170, 14, 14);
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends CauldronRecipe> getRecipeClass() {
        return CauldronRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("recipe." + Frugality.MODID + ".washing");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(CauldronRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getInput()));
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputsAsItems());
    }

    @Override
    public void draw(CauldronRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        this.slotDrawable.draw(poseStack, 0, 0);
        this.slotDrawable.draw(poseStack, 25, 0);
        this.slotDrawable.draw(poseStack, 25, 18);
        this.arrow.draw(poseStack, 48, 10);
        this.plusSign.draw(poseStack, 2, 20);

        int results = recipe.getOutputs().size();
        for (int slotId = 0; slotId < 4 + (4 * (results/5)); slotId++) {
            this.slotDrawable.draw(poseStack, (78 + 18 * (slotId % 4)), 9 - (9 * (results/5)) + 18 * (slotId/4));
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CauldronRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        List<ChanceItem> results = recipe.getOutputs();

        itemStacks.init(0, true, 0, 0);
        itemStacks.set(0, Arrays.asList(recipe.getInput().getItems()));

        fluidStacks.init(1, true, 26, 1);
        fluidStacks.set(1, new FluidStack(Fluids.WATER.getSource(), recipe.getFluidLevel() * 334));

        itemStacks.init(2, true, 25, 18);
        itemStacks.set(2, new ItemStack(Blocks.CAULDRON.asItem()));

        for(int slotId = 0; slotId < results.size(); slotId++){

            itemStacks.init(slotId + 3, false, (78 + 18 * (slotId % 4)), 9 - (9 * (results.size()/5)) + 18 * (slotId/4));
            itemStacks.set(slotId + 3, results.get(slotId).getItem());
        }

        itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if(input)
                return;
            if(slotIndex < 3)
                return;
            ChanceItem output = results.get(slotIndex - 3);
            float chance = output.getChance();
            if(chance != 1){
                if(chance < 0.01)
                    tooltip.add(1, new TextComponent("<1%").append(chanceText).withStyle(ChatFormatting.DARK_GREEN));
                else
                    tooltip.add(1, new TextComponent((int)(chance * 100) + "%").append(chanceText).withStyle(ChatFormatting.DARK_GREEN));
            }
            else{
                tooltip.add(1, new TextComponent("100%").append(chanceText).withStyle(ChatFormatting.DARK_GREEN));
            }
        } );

        fluidStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if(input)
                if(slotIndex == 1)
                    tooltip.add(1, new TextComponent("" + recipe.getFluidLevel()).append(fluidAmountText).withStyle(ChatFormatting.DARK_GREEN));

        });
}
}
