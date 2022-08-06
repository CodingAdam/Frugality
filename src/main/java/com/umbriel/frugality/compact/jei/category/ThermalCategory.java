package com.umbriel.frugality.compact.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.compact.jei.FrugalityPlugin;
import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.item.ChanceItem;
import com.umbriel.frugality.util.recipes.ThermalRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
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
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;
import static mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT;

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
       this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(icon));
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
    public RecipeType<ThermalRecipe> getRecipeType() {
        return FrugalityPlugin.THERMAL;
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
    public void draw(ThermalRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
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
    public void setRecipe(IRecipeLayoutBuilder builder, ThermalRecipe recipe, IFocusGroup focuses) {
        List<ChanceItem> results = recipe.getItemResult();

        if(recipe.getStoneType() == 1){
            builder.addSlot(INPUT, 1, 9)
                    .addItemStack(new ItemStack(FrugalItems.HEATED_STONE.get()));
        }
        if(recipe.getStoneType() == 2){
            builder.addSlot(INPUT, 1, 9)
                    .addItemStack(new ItemStack(FrugalItems.CHILLED_STONE.get()));
        }
        if(recipe.getStoneType() == 3){
            builder.addSlot(INPUT, 1, 9)
                    .addItemStack(new ItemStack(FrugalItems.WARPED_STONE.get()));
        }

        builder.addSlot(INPUT, 49, 10)
                .addIngredients(Ingredient.of(recipe.getInput().getItems()));


        if(recipe.getItemResult() != null) {
            for(int slotId = 0; slotId < results.size(); slotId++) {
                int slotNum = slotId;
                builder.addSlot(OUTPUT, (109 + 18 * (slotId % 4)), 10 - (9 * (results.size() / 5)) + 18 * (slotId / 4))
                        .addItemStack(results.get(slotId).getStack())
                        .addTooltipCallback((recipeSlotView, tooltip) -> {
                            ChanceItem output = results.get(slotNum);
                            float chance = output.getChance();
                            if (chance != 1) {
                                if (chance < 0.01)
                                    tooltip.add(1, new TextComponent("<1%").append(chanceText).withStyle(ChatFormatting.DARK_GREEN));
                                else
                                    tooltip.add(1, new TextComponent((int) (chance * 100) + "%").append(chanceText).withStyle(ChatFormatting.DARK_GREEN));
                            } else {
                                tooltip.add(1, new TextComponent("100%").append(chanceText).withStyle(ChatFormatting.DARK_GREEN));
                            }
                        });
            }
        }
        if(recipe.getFluidResult() != null) {
            builder.addSlot(OUTPUT, 109, 10)
                    .addIngredient(ForgeTypes.FLUID_STACK, recipe.getFluidResult())
                    .addTooltipCallback((recipeSlotView, tooltip) -> {
                        tooltip.add(1, new TextComponent("1").append(bucketAmountText).withStyle(ChatFormatting.DARK_GREEN));
                    });
        }


    }



}
