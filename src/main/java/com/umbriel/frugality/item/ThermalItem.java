package com.umbriel.frugality.item;


import com.umbriel.frugality.init.ModItems;
import com.umbriel.frugality.init.ModRecipes;
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


        ItemStack itemStack = level.getBlockState(pos).getBlock().asItem().getDefaultInstance();
        ThermalRecipe recipe = findThermalRecipe(level, itemStack);



        if(player != null) {
            if (recipe != null) {
                if (getType(player, hand) == recipe.getStoneType()) {
                    System.out.println(recipe.getFluidResult() != null);
                    if (recipe.getFluidResult() != null) {
                        level.setBlock(pos, recipe.getFluidResult().getFluid().defaultFluidState().createLegacyBlock(), 11);
                        player.setItemInHand(hand, new ItemStack(ModItems.THERMAL_STONE.get()));
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
                        player.setItemInHand(hand, new ItemStack(ModItems.THERMAL_STONE.get()));
                        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                        return InteractionResult.sidedSuccess(level.isClientSide());
                    }
                }
            }
        }

        return InteractionResult.FAIL;
    }

    public static ThermalRecipe findThermalRecipe(Level level, ItemStack itemStack) {
        for (final ThermalRecipe recipe : level.getRecipeManager().getAllRecipesFor(ModRecipes.thermalRecipeType)) {
            if (recipe.doesMatch(itemStack)) {
                return recipe;
            }
        }
        return null;
    }

    public int getType(Player player, InteractionHand hand){
        ItemStack stone = player.getItemInHand(hand);
        if(stone.is(ModItems.HEATED_STONE.get())){
            return 1;
        }
        if(stone.is(ModItems.CHILLED_STONE.get())){
            return 2;
        }
        if(stone.is(ModItems.WARPED_STONE.get())){
            return 3;
        }
        return 0;
    }


}
