package com.umbriel.frugality.jei;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.block.MelterBlock;
import com.umbriel.frugality.event.RecipeEvents;
import com.umbriel.frugality.init.ModItems;
import com.umbriel.frugality.init.ModRecipes;
import com.umbriel.frugality.jei.categorys.CauldronCategory;
import com.umbriel.frugality.jei.categorys.CrushingCategory;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import com.umbriel.frugality.util.recipes.CrushingBlockRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

@JeiPlugin
public class FrugalityPlugin implements IModPlugin {

    TranslatableComponent smallOreText = new TranslatableComponent(Frugality.MODID + ".jei.info.small_ores" );
    TranslatableComponent charredShardsText = new TranslatableComponent(Frugality.MODID + ".jei.info.charred_shards" );

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
                new CauldronCategory(guiHelper, Blocks.CAULDRON),
                new CrushingCategory(guiHelper, ModItems.CRUSHING_STONE.get())
        );
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.BRICK_FURNACE.get()),
                VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeCatalyst(new ItemStack(ModItems.BRICK_BLAST_FURNACE.get()),
                VanillaRecipeCategoryUid.BLASTING, VanillaRecipeCategoryUid.FUEL);

        registration.addRecipeCatalyst(new ItemStack(Items.CAULDRON), CauldronCategory.ID);
        registration.addRecipeCatalyst(new ItemStack(ModItems.WOODEN_CAULDRON.get()), CauldronCategory.ID);
        registration.addRecipeCatalyst(new ItemStack(ModItems.STONE_CAULDRON.get()), CauldronCategory.ID);

        registration.addRecipeCatalyst(new ItemStack(ModItems.CRUSHING_STONE.get()), CrushingCategory.ID);
        registration.addRecipeCatalyst(new ItemStack(ModItems.CRUSHING_TERRACOTTA.get()), CrushingCategory.ID);

    }
    @Override
    public void registerRecipes(IRecipeRegistration registration){
        final Collection<CauldronRecipe> cauldronRecipes = RecipeEvents.getRecipes();
        registration.addRecipes(cauldronRecipes, CauldronCategory.ID);

        List<CrushingBlockRecipe> crushingBlockRecipes = Minecraft.getInstance().level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.crushingBlockRecipeType);

        registration.addRecipes(crushingBlockRecipes, CrushingCategory.ID);
        registration.addIngredientInfo(new ItemStack(ModItems.SMALL_RAW_COPPER.get()), VanillaTypes.ITEM, smallOreText);
        registration.addIngredientInfo(new ItemStack(ModItems.SMALL_RAW_GOLD.get()), VanillaTypes.ITEM, smallOreText);
        registration.addIngredientInfo(new ItemStack(ModItems.SMALL_RAW_IRON.get()), VanillaTypes.ITEM, smallOreText);
        registration.addIngredientInfo(new ItemStack(ModItems.CHARRED_LOG.get()), VanillaTypes.ITEM, charredShardsText);
        registration.addIngredientInfo(new ItemStack(ModItems.CHARRED_SHARDS.get()), VanillaTypes.ITEM, charredShardsText);
    }
}
