package com.umbriel.frugality.data;

import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.util.tags.ForgeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(DataGenerator generator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.registerMineableTags();
        this.registerForgeTags();
        this.registerVanillaTags();
    }

    private void registerMineableTags(){
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE)
                .add(FrugalItems.BRICK_BLAST_FURNACE.get())
                .add(FrugalItems.BRICK_FURNACE.get())
                .add(FrugalItems.STONE_CAULDRON.get())
                .add(FrugalItems.CRUSHING_TERRACOTTA.get())
                .add(FrugalItems.CRUSHING_STONE.get())
                .add(FrugalItems.SILICA_STONE.get())
                .add(FrugalItems.SILICA_STONE_SLAB.get())
                .add(FrugalItems.SILICA_STONE_STAIRS.get())
                .add(FrugalItems.SILICA_COBBLESTONE_STAIRS.get())
                .add(FrugalItems.SILICA_COBBLESTONE_SLAB.get())
                .add(FrugalItems.SILICA_COBBLESTONE.get())
                .add(FrugalItems.SILICA_COBBLESTONE_WALL.get())
                .add(FrugalItems.SILICA_POLISHED.get())
                .add(FrugalItems.SILICA_POLISHED_SLAB.get())
                .add(FrugalItems.SILICA_BRICKS_STAIRS.get())
                .add(FrugalItems.SILICA_BRICKS_SLAB.get())
                .add(FrugalItems.SILICA_BRICKS.get())
                .add(FrugalItems.SILICA_BRICKS_WALL.get());

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE)
                .add(FrugalItems.TREE_TAP.get())
                .add(FrugalItems.WOODEN_CAULDRON.get())
                .add(FrugalItems.CHARRED_LOG.get())
                .add(FrugalItems.MELTER.get());

        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL)
                .add(FrugalItems.SALT_BLOCK.get())
                .add(FrugalItems.CLAY_BRICKS.get())
                .add(FrugalItems.SALT_ORE.get())
                .add(FrugalItems.MUD_BLOCK.get());

    }

    private void registerVanillaTags(){
        tag(net.minecraft.tags.BlockTags.SLABS)
                .add(FrugalItems.SILICA_POLISHED_SLAB.get())
                .add(FrugalItems.SILICA_COBBLESTONE_SLAB.get())
                .add(FrugalItems.SILICA_BRICKS_SLAB.get())
                .add(FrugalItems.SILICA_STONE_SLAB.get());

        tag(net.minecraft.tags.BlockTags.STAIRS)
                .add(FrugalItems.SILICA_BRICKS_STAIRS.get())
                .add(FrugalItems.SILICA_COBBLESTONE_STAIRS.get())
                .add(FrugalItems.SILICA_STONE_STAIRS.get());

        tag(net.minecraft.tags.BlockTags.WALLS)
                .add(FrugalItems.SILICA_COBBLESTONE_WALL.get())
                .add(FrugalItems.SILICA_BRICKS_WALL.get());

    }

    private void registerForgeTags(){
        tag(ForgeTags.ROSE_GOLD_BLOCK).add(FrugalItems.ROSE_GOLD_BLOCK.get());
    }
}
