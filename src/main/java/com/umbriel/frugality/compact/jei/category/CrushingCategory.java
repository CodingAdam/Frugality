package com.umbriel.frugality.compact.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.compact.jei.FrugalityPlugin;
import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.item.ChanceItem;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import com.umbriel.frugality.util.recipes.CrushingRecipe;
import mezz.jei.api.constants.VanillaTypes;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.List;

import static com.umbriel.frugality.compact.jei.category.CauldronCategory.chanceText;
import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;
import static mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT;

public class CrushingCategory implements IRecipeCategory<CrushingRecipe> {
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
       this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(icon));
       this.arrow = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 75, 169, 24, 16);
       this.plusSign = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 26, 170, 14, 14);
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public Class<? extends CrushingRecipe> getRecipeClass() {
        return CrushingRecipe.class;
    }

    @Override
    public RecipeType<CrushingRecipe> getRecipeType() {
        return FrugalityPlugin.CRUSHING;
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
    public void draw(CrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
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
    public void setRecipe(IRecipeLayoutBuilder builder, CrushingRecipe recipe, IFocusGroup focuses) {
        List<ChanceItem> results = recipe.getItemResult();

        builder.addSlot(INPUT, 1, -1)
                .addIngredients(Ingredient.of(recipe.getTool().getItems()));

        builder.addSlot(INPUT, 26, 1)
                .addIngredients(Ingredient.of(recipe.getInput().getItems()));

        ItemStack crushingItem = new ItemStack(FrugalItems.CRUSHING_STONE.get().asItem());
        crushingItem.setCount(recipe.getHits());

        builder.addSlot(INPUT, 26, 19)
                .addItemStack(crushingItem)
                .addTooltipCallback((recipeSlotView, tooltip) -> {
                    tooltip.add(1, new TextComponent("").append(hitsAmountText).append("" + recipe.getHits()).withStyle(ChatFormatting.DARK_RED));
                });

        for(int slotId = 0; slotId < results.size(); slotId++){
            int slotNum = slotId;
            builder.addSlot(OUTPUT, (79 + 18 * (slotId % 4)), 10 - (9 * (results.size()/5)) + 18 * (slotId/4))
                    .addItemStack(results.get(slotId).getItem())
                    .addTooltipCallback((recipeSlotView, tooltip) -> {
                        ChanceItem output = results.get(slotNum);
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
                    });
        }
    }
}
