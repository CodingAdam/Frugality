package com.umbriel.frugality.event;

import com.umbriel.frugality.item.ChanceItem;
import com.umbriel.frugality.util.recipes.CauldronRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CauldronRecipeEvent extends PlayerEvent {

    private final CauldronRecipe recipe;
    private final Level level;
    private final BlockState cauldron;
    private final BlockPos pos;

    private CauldronRecipeEvent(Player player, CauldronRecipe recipe, Level level, BlockState cauldron, BlockPos pos) {
        super(player);
        this.recipe = recipe;
        this.level = level;
        this.cauldron = cauldron;
        this.pos = pos;
    }

    public CauldronRecipe getRecipe() {
        return this.recipe;
    }

    public Level getLevel() {
        return this.level;
    }

    public BlockState getCauldron() {
        return this.cauldron;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Cancelable
    public static class Crafting extends CauldronRecipeEvent {

        private final ItemStack input;
        private final List<ChanceItem> initialOutput;
        private final NonNullList<ChanceItem> output;

        public Crafting(Player player, CauldronRecipe recipe, Level level, BlockState cauldron, BlockPos pos, ItemStack input, NonNullList<ChanceItem> output) {

            super(player, recipe, level, cauldron, pos);
            this.input = input;
            this.output = output;
            this.initialOutput = Collections.unmodifiableList(new ArrayList<>(this.output));
        }

        public ItemStack getInput () {
            return this.input;
        }

        public List<ChanceItem> getInitialOutputs () {
            return this.initialOutput;
        }

        public List<ChanceItem> getOutputs () {
            return this.output;
        }
    }

    public static class Crafted extends CauldronRecipeEvent {

        private final List<ChanceItem> outputs;

        public Crafted(Player player, CauldronRecipe recipe, Level level, BlockState cauldron, BlockPos pos, List<ChanceItem> outputs) {

            super(player, recipe, level, cauldron, pos);
            this.outputs = Collections.unmodifiableList(outputs);
        }

        public List<ChanceItem> getOutputs () {
            return this.outputs;
        }
    }

}
