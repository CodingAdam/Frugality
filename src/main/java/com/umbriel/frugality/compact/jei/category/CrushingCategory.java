package com.umbriel.frugality.compact.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModItems;
import com.umbriel.frugality.item.ChanceItem;
import com.umbriel.frugality.util.recipes.CrushingBlockRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.umbriel.frugality.compact.jei.category.CauldronCategory.chanceText;

public class CrushingCategory implements IRecipeCategory<CrushingBlockRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(Frugality.MODID, "crushing");
    public static final MutableComponent hitsAmountText = new TranslatableComponent("recipe." + Frugality.MODID + ".hits");

    protected String name;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic slotDrawable;
    private IDrawable arrow;
    private IDrawable plusSign;


    public CrushingCategory(IGuiHelper guiHelper, Block icon) {
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
    public Class<? extends CrushingBlockRecipe> getRecipeClass() {
        return CrushingBlockRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("recipe." + Frugality.MODID + ".crushing");
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
    public void setIngredients(CrushingBlockRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getInput()));
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getResults());
    }

    @Override
    public void draw(CrushingBlockRecipe recipe, PoseStack poseStack, double mouseX, double mouseY) {
        this.slotDrawable.draw(poseStack, 0, 0);
        this.slotDrawable.draw(poseStack, 25, 0);
        this.slotDrawable.draw(poseStack, 25, 18);
        this.arrow.draw(poseStack, 48, 10);
        this.plusSign.draw(poseStack, 2, 20);

        int results = recipe.getResults().size();
        for (int slotId = 0; slotId < 4 + (4 * (results/5)); slotId++) {
            this.slotDrawable.draw(poseStack, (78 + 18 * (slotId % 4)), 9 - (9 * (results/5)) + 18 * (slotId/4));
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrushingBlockRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        List<ChanceItem> results = recipe.getRolledResults();

        itemStacks.init(0, true, 0, 0);
        itemStacks.set(0, Arrays.asList(recipe.getTool().getItems()));

        itemStacks.init(1, true, 25, 0);
        itemStacks.set(1, Arrays.asList(recipe.getInput().getItems()));

        ItemStack crushingItem = new ItemStack(ModItems.CRUSHING_STONE.get().asItem());
        crushingItem.setCount(recipe.getHits());

        itemStacks.init(2, true, 25, 18);
        itemStacks.set(2, crushingItem);

        for(int slotId = 0; slotId < results.size(); slotId++){

            itemStacks.init(slotId + 3, false, (78 + 18 * (slotId % 4)), 9 - (9 * (results.size()/5)) + 18 * (slotId/4));
            itemStacks.set(slotId + 3, results.get(slotId).getItem());
        }

        itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if(slotIndex == 2)
                tooltip.add(1, new TextComponent("").append(hitsAmountText).append("" + recipe.getHits()).withStyle(ChatFormatting.DARK_RED));;
        } );

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

}
}
