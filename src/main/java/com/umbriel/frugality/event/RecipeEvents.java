package com.umbriel.frugality.event;


import com.umbriel.frugality.block.Cauldrons.CustomLayeredCauldron;
import com.umbriel.frugality.item.ChanceItem;
import com.umbriel.frugality.util.ParticleHelper;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;

import java.util.List;

import static com.umbriel.frugality.init.ModRecipes.cauldronRecipeType;
import static com.umbriel.frugality.init.ModItems.MUD_BLOCK;
import static com.umbriel.frugality.util.CustomCauldronHelper.getCauldron;

public class RecipeEvents {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void clickDirtwithBottle(PlayerInteractEvent.RightClickBlock event){
        Level world = event.getWorld();
        BlockPos pos = event.getPos();
        ItemStack item = event.getItemStack();
        Player player = event.getPlayer();
        if(player.isCrouching()) {
            return;
        }
        if(!(world.getBlockState(pos) == Blocks.DIRT.defaultBlockState())) {
            return;
        }
        if(item.isEmpty()) {
            return;
        }
        if((PotionUtils.getPotion(item) == Potions.WATER)){
            world.setBlock(pos, MUD_BLOCK.get().defaultBlockState(), 1);
            player.setItemInHand(event.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }




    // Outside Vars for repeated event use

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerClickCauldron(PlayerInteractEvent.RightClickBlock event){
        final Level world = event.getWorld();
        final Player player = event.getPlayer();
        final BlockPos pos = event.getPos();
        final BlockState state = world.getBlockState(pos);
        final ItemStack stack = player.getItemInHand(event.getHand());

        if (state.getBlock() instanceof CustomLayeredCauldron || state.getBlock() instanceof LayeredCauldronBlock) {

            IntegerProperty fluidLevel = LayeredCauldronBlock.LEVEL;

            if (state.getBlock() instanceof CustomLayeredCauldron) {
                fluidLevel = CustomLayeredCauldron.LEVEL;
            }

            final int initialFluidLevel = state.getValue(fluidLevel);

            if (initialFluidLevel > 0) {

                final CauldronRecipe recipe = findRecipe(stack, initialFluidLevel);

                if (recipe != null) {

                    final CauldronRecipeEvent.Crafting craftEvent = new CauldronRecipeEvent.Crafting(player, recipe, world, state, pos, stack, recipe.getOutputs());

                    if (!MinecraftForge.EVENT_BUS.post(craftEvent)) {

                        if (!player.isCreative()) {
                            stack.shrink(1);
                        }

                        if (initialFluidLevel - recipe.getFluidLevel() > 0) {
                            world.setBlockAndUpdate(pos, state.setValue(fluidLevel, initialFluidLevel - recipe.getFluidLevel()));
                        } else {
                            world.setBlockAndUpdate(pos, getCauldron(state.getBlock().defaultBlockState()));
                        }

                        player.swing(player.getUsedItemHand());
                        ParticleHelper.spawnWaterParticles(world, 0.3F, pos, 30);
                        ParticleHelper.spawnCraftingParticles(world, 0.7D, 0.3F, pos, recipe.getInput().getItems()[0], 30);

                        event.setCanceled(true);
                        event.setCancellationResult(InteractionResult.CONSUME);

                        if (player instanceof ServerPlayer) {

                            for (final ChanceItem outputEntry : craftEvent.getOutputs()) {

                                final ItemStack resultDrop = outputEntry.rollOutput();

                                if (!player.getInventory().add(resultDrop)) {
                                    player.drop(resultDrop, false);
                                }

                            }

                            world.playSound(null, pos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        }

                        MinecraftForge.EVENT_BUS.post(new CauldronRecipeEvent.Crafted(player, recipe, world, state, pos, craftEvent.getOutputs()));
                    }
                }
            }
        }
    }



    @Nullable
    public static CauldronRecipe findRecipe (ItemStack item, int currentFluid) {

        for (final CauldronRecipe recipe : getRecipes()) {

            if (recipe.doesMatch(item, currentFluid)) {

                return recipe;
            }
        }

        return null;
    }

    public static List<CauldronRecipe> getRecipes () {

        return getRecipes(null);
    }

    public static List<CauldronRecipe> getRecipes (@Nullable RecipeManager manager) {

        return getManager(manager).getAllRecipesFor(cauldronRecipeType);
    }

    public static RecipeManager getManager (@Nullable RecipeManager manager) {
        if(manager != null){
            return manager;
        }
        else {
            if (EffectiveSide.get().isClient()) {

                if (Minecraft.getInstance().player != null) {
                    return Minecraft.getInstance().player.connection.getRecipeManager();
                }
            }

            else if (EffectiveSide.get().isServer()) {

                return ServerLifecycleHooks.getCurrentServer().getRecipeManager();
            }
        }
        return null;
    }

}
