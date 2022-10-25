package com.umbriel.frugality.data;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Frugality.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(FrugalItems.SILICA_COBBLESTONE.get());
        this.simpleBlock(FrugalItems.SILICA_STONE.get());
        this.simpleBlock(FrugalItems.SILICA_BRICKS.get());
        this.simpleBlock(FrugalItems.SILICA_POLISHED.get());
        this.simpleBlock(FrugalItems.SALT_BLOCK.get());
        this.simpleBlock(FrugalItems.SALT_ORE.get());
        this.simpleBlock(FrugalItems.SILICA_SAND.get());

        this.stairsBlock((StairBlock) FrugalItems.SILICA_STONE_STAIRS.get(), modLoc("block/silica_stone"));
        this.slabBlock((SlabBlock) FrugalItems.SILICA_STONE_SLAB.get(), modLoc("block/silica_stone"), modLoc("block/silica_stone"));

        this.slabBlock((SlabBlock) FrugalItems.SILICA_POLISHED_SLAB.get(), modLoc("block/silica_polished"), modLoc("block/silica_polished"));

        this.stairsBlock((StairBlock) FrugalItems.SILICA_COBBLESTONE_STAIRS.get(), modLoc("block/silica_cobblestone"));
        this.slabBlock((SlabBlock) FrugalItems.SILICA_COBBLESTONE_SLAB.get(), modLoc("block/silica_cobblestone"), modLoc("block/silica_cobblestone"));
        wallBlockWithInventory((WallBlock) FrugalItems.SILICA_COBBLESTONE_WALL.get(), modLoc("block/silica_cobblestone"));

        this.stairsBlock((StairBlock) FrugalItems.SILICA_BRICKS_STAIRS.get(), modLoc("block/silica_bricks"));
        this.slabBlock((SlabBlock) FrugalItems.SILICA_BRICKS_SLAB.get(), modLoc("block/silica_bricks"), modLoc("block/silica_bricks"));
        wallBlockWithInventory((WallBlock) FrugalItems.SILICA_BRICKS_WALL.get(), modLoc("block/silica_bricks"));
    }

    private void wallBlockWithInventory(WallBlock wallBlock, ResourceLocation texture){
        simpleBlockItem(wallBlock, models().wallInventory(wallBlock.getRegistryName().toString(), texture));
        this.wallBlock(wallBlock, texture);
    }



}
