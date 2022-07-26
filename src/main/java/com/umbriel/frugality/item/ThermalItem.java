package com.umbriel.frugality.item;


import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.init.FrugalRecipes;
import com.umbriel.frugality.util.recipes.ThermalRecipe;
import net.minecraft.core.BlockPos;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.stream.Collectors;

import static com.umbriel.frugality.init.FrugalRecipes.cauldronRecipeType;
import static com.umbriel.frugality.init.FrugalRecipes.thermalRecipeType;
import static com.umbriel.frugality.util.recipes.RecipeHelper.findRecipe;


@SuppressWarnings("deprecation")
public class ThermalItem extends Item {

    public ThermalItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();

        List<ThermalRecipe> recipeList = level.getRecipeManager().getAllRecipesFor(thermalRecipeType);

        ItemStack itemStack = level.getBlockState(pos).getBlock().asItem().getDefaultInstance();
        ThermalRecipe recipe = (ThermalRecipe)findRecipe(level, itemStack, recipeList);

        if(player != null) {
            if (recipe != null) {
                if (getType(player, hand) == recipe.getStoneType()) {
                    System.out.println(recipe.getFluidResult() != null);
                    if (recipe.getFluidResult() != null) {
                        level.setBlock(pos, recipe.getFluidResult().getFluid().defaultFluidState().createLegacyBlock(), 11);
                        if (!player.isCreative()) {
                            player.getItemInHand(hand).shrink(1);
                        }
                        player.getInventory().add(new ItemStack(FrugalItems.THERMAL_STONE.get()));
                        player.getCooldowns().addCooldown(this, 20);
                        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                        return InteractionResult.sidedSuccess(level.isClientSide());
                    }
                    if(recipe.getItemResult() != null){
                        Block block = Block.byItem(recipe.rollOutputs().get(0).getItem());
                        if (block != Blocks.AIR) {
                            level.setBlock(pos, block.defaultBlockState(), 1);
                        }
                        else {
                            List<ItemStack> items = recipe.rollOutputs();
                            for (ItemStack item : items) {
                                ItemEntity spawnedItem = new ItemEntity(level, pos.getX(), (double) pos.getX(), (double) pos.getX(), item);
                                level.addFreshEntity(spawnedItem);
                            }
                        }
                        if (!player.isCreative()) {
                            player.getItemInHand(hand).shrink(1);
                        }
                        player.getInventory().add(new ItemStack(FrugalItems.THERMAL_STONE.get()));
                        player.getCooldowns().addCooldown(this, 20);
                        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                        return InteractionResult.sidedSuccess(level.isClientSide());
                    }
                }
            }
        }

        return InteractionResult.FAIL;
    }

    public int getType(Player player, InteractionHand hand){
        ItemStack stone = player.getItemInHand(hand);
        if(stone.is(FrugalItems.HEATED_STONE.get())){
            return 1;
        }
        if(stone.is(FrugalItems.CHILLED_STONE.get())){
            return 2;
        }
        if(stone.is(FrugalItems.WARPED_STONE.get())){
            return 3;
        }
        return 0;
    }


}
