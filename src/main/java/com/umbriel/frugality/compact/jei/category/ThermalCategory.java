package com.umbriel.frugality.compact.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModItems;
import com.umbriel.frugality.item.ChanceItem;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import com.umbriel.frugality.util.recipes.ThermalRecipe;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ThermalCategory implements IRecipeCategory<ThermalRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(Frugality.MODID, "thermal");
    public static final MutableComponent chanceText = new TranslatableComponent("recipe." + Frugality.MODID + ".chance");
    public static final MutableComponent bucketAmountText = new TranslatableComponent("recipe." + Frugality.MODID + ".bucket_amount");
    public static final MutableComponent itemAmountText = new TranslatableComponent("recipe." + Frugality.MODID + ".item_amount");

    protected String name;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic slotDrawable;
    private IDrawable arrow;
    private IDrawable plusSign;


    public ThermalCategory(IGuiHelper guiHelper, Item icon) {
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
    public Class<? extends ThermalRecipe> getRecipeClass() {
        return ThermalRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("recipe." + Frugality.MODID + ".thermal");
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
    public void setIngredients(ThermalRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getInput()));
        if(recipe.getItemResult() != null){
            ingredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputsAsItems());
        }
        if(recipe.getFluidResult() != null){
            ingredients.setOutput(VanillaTypes.FLUID, recipe.getFluidResult());
        }

    }

    @Override
    public void draw(ThermalRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        this.slotDrawable.draw(poseStack, 0, 9);
        this.slotDrawable.draw(poseStack, 48, 9);
        this.arrow.draw(poseStack, 74, 9);
        this.plusSign.draw(poseStack, 25, 9);

        int results = recipe.getItemResult().size();
        if(results > 1){
            for (int slotId = 0; slotId < 2 + (2 * (results/3)); slotId++) {
                this.slotDrawable.draw(poseStack, (108 + 18 * (slotId % 2)), 9 - (9 * (results/3)) + 18 * (slotId/2));
            }
        }
        else {
            this.slotDrawable.draw(poseStack, 108, 9);
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ThermalRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        List<ChanceItem> results = recipe.getItemResult();

        if(recipe.getStoneType() == 1){
            itemStacks.init(0, true, 0, 9);
            itemStacks.set(0, Collections.singletonList(new ItemStack(ModItems.HEATED_STONE.get())));
        }
        if(recipe.getStoneType() == 2){
            itemStacks.init(0, true, 0, 9);
            itemStacks.set(0, Collections.singletonList(new ItemStack(ModItems.CHILLED_STONE.get())));
        }
        if(recipe.getStoneType() == 3){
            itemStacks.init(0, true, 0, 9);
            itemStacks.set(0, Collections.singletonList(new ItemStack(ModItems.WARPED_STONE.get())));
        }

        itemStacks.init(1, true, 48, 9);
        itemStacks.set(1, Arrays.asList(recipe.getInput().getItems()));



        if(recipe.getItemResult() != null) {
            for(int slotId = 0; slotId < results.size(); slotId++){
                itemStacks.init(slotId + 2, false, (108 + 18 * (slotId % 4)), 9 - (9 * (results.size()/5)) + 18 * (slotId/4));
                itemStacks.set(slotId + 2, results.get(slotId).getItem());
            }
            itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
                if(input)
                    return;
                if(slotIndex < 2)
                    return;
                ChanceItem output = results.get(slotIndex - 2);
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
        }
        if(recipe.getFluidResult() != null) {
            fluidStacks.init(2, false, 109, 6);
            fluidStacks.set(2, recipe.getFluidResult());
            fluidStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
                if(!input)
                    if(slotIndex == 2)
                        tooltip.add(1, new TextComponent("1").append(bucketAmountText).withStyle(ChatFormatting.DARK_GREEN));
            });
        }





}
}
