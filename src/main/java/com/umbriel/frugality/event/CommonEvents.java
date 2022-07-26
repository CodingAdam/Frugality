package com.umbriel.frugality.event;


import com.umbriel.frugality.block.cauldron.CustomLavaCauldron;
import com.umbriel.frugality.block.cauldron.CustomLayeredCauldron;
import com.umbriel.frugality.block.cauldron.CustomSnowCauldron;
import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.init.FrugalRecipes;
import com.umbriel.frugality.item.ChanceItem;
import com.umbriel.frugality.util.ParticleHelper;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import com.umbriel.frugality.util.recipes.CrushingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.umbriel.frugality.init.FrugalRecipes.cauldronRecipeType;
import static com.umbriel.frugality.init.FrugalItems.MUD_BLOCK;
import static com.umbriel.frugality.util.CustomCauldronHelper.getCauldron;
import static com.umbriel.frugality.util.recipes.RecipeHelper.findRecipe;
import static net.minecraft.world.item.AxeItem.STRIPPABLES;

public class CommonEvents {

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



    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void stripBark(PlayerInteractEvent.RightClickBlock event){
        Level world = event.getWorld();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();
        ItemStack barkItem = new ItemStack(FrugalItems.BARK.get());

        Item tool = event.getPlayer().getItemInHand(event.getHand()).getItem();
        Block block = STRIPPABLES.get(world.getBlockState(pos).getBlock());
        if(tool instanceof AxeItem){
            if(block != null){
                if(!player.getInventory().add(barkItem)){
                    player.drop(barkItem, false);
                }
            }
        }
    }


    // Event for Warped Stone
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void portalTransformItem(EntityJoinWorldEvent event){
        Entity entity = event.getEntity();
        Level level = event.getWorld();

        if(entity instanceof ItemEntity){
            ItemEntity item = (ItemEntity)entity;
            if(level.dimension() == Level.NETHER && item.isOnPortalCooldown()){
                System.out.println(level.players().size() == 0);
                System.out.println(item);
                if(item.getItem().getItem() == FrugalItems.THERMAL_STONE.get()){
                    item.setItem(new ItemStack(FrugalItems.WARPED_STONE.get(), item.getItem().getCount()));
                }
            }
        }
    }





    // Outside Vars for repeated event use

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void playerClickCauldron(PlayerInteractEvent.RightClickBlock event) {
        final Level world = event.getWorld();
        final Player player = event.getPlayer();
        final BlockPos pos = event.getPos();
        final BlockState state = world.getBlockState(pos);
        final ItemStack stack = player.getItemInHand(event.getHand());

        CauldronRecipe recipe = null;
        IntegerProperty fluidLevel = null;
        SimpleParticleType particles = null;
        SoundEvent sound = null;
        int initialFluidLevel = 0;

        List<CauldronRecipe> recipeList = world.getRecipeManager().getAllRecipesFor(cauldronRecipeType);

        if (state.getBlock() instanceof CustomLayeredCauldron || state.getBlock() instanceof LayeredCauldronBlock) {

            fluidLevel = LayeredCauldronBlock.LEVEL;

            if (state.getBlock() instanceof CustomLayeredCauldron) {
                fluidLevel = CustomLayeredCauldron.LEVEL;
            }

            initialFluidLevel = state.getValue(fluidLevel);
            final int FluidLevel = initialFluidLevel;

            recipeList = recipeList.stream().filter(cuttingRecipe -> cuttingRecipe.getFluidLevel() <= FluidLevel).collect(Collectors.toList());

            if (initialFluidLevel > 0) {
                if(state.getBlock() instanceof CustomSnowCauldron || state.getBlock() instanceof PowderSnowCauldronBlock){
                    recipeList = recipeList.stream().filter(cuttingRecipe -> cuttingRecipe.getFill() == 2).collect(Collectors.toList());
                    recipe = (CauldronRecipe)findRecipe(world, stack, recipeList);
                    particles = ParticleTypes.SNOWFLAKE;
                    sound = SoundEvents.SNOW_BREAK;
                } else {
                    recipeList = recipeList.stream().filter(cuttingRecipe -> cuttingRecipe.getFill() == 1).collect(Collectors.toList());
                    recipe = (CauldronRecipe)findRecipe(world, stack, recipeList);
                    particles = ParticleTypes.SPLASH;
                    sound = SoundEvents.AMBIENT_UNDERWATER_EXIT;
                }
            }
        }

        if(state.getBlock() instanceof CustomLavaCauldron || state.getBlock() instanceof LavaCauldronBlock){
            recipeList = recipeList.stream().filter(cuttingRecipe -> cuttingRecipe.getFluidLevel() == 3).collect(Collectors.toList());
            recipeList = recipeList.stream().filter(cuttingRecipe -> cuttingRecipe.getFill() == 3).collect(Collectors.toList());
            recipe = (CauldronRecipe)findRecipe(world, stack, recipeList);
            particles = ParticleTypes.LAVA;
            sound = SoundEvents.LAVA_POP;
        }

        if (recipe != null) {

            final CauldronRecipeEvent.Crafting craftEvent = new CauldronRecipeEvent.Crafting(player, recipe, world, state, pos, stack, recipe.getItemResult());

            if (!MinecraftForge.EVENT_BUS.post(craftEvent)) {

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                if(fluidLevel != null){
                    if (initialFluidLevel - recipe.getFluidLevel() > 0) {
                        world.setBlockAndUpdate(pos, state.setValue(fluidLevel, initialFluidLevel - recipe.getFluidLevel()));
                    } else {
                        world.setBlockAndUpdate(pos, getCauldron(state.getBlock().defaultBlockState()));
                    }
                }
                else{
                    world.setBlockAndUpdate(pos, getCauldron(state.getBlock().defaultBlockState()));
                }

                player.swing(player.getUsedItemHand());
                ParticleHelper.spawnParticles(particles, world, 0.3F, pos, 30);
                ParticleHelper.spawnItemParticles(world, 0.7D, 0.3F, pos, recipe.getInput().getItems()[0], 30);

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.CONSUME);

                if (player instanceof ServerPlayer) {

                    for (final ChanceItem outputEntry : craftEvent.getOutputs()) {

                        final ItemStack resultDrop = outputEntry.rollOutput();

                        if (!player.getInventory().add(resultDrop)) {
                            player.drop(resultDrop, false);
                        }

                    }

                    world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                MinecraftForge.EVENT_BUS.post(new CauldronRecipeEvent.Crafted(player, recipe, world, state, pos, craftEvent.getOutputs()));
            }
        }
    }
}
