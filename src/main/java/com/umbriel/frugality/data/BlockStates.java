package com.umbriel.frugality.data;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Frugality.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(ModItems.SILICA_COBBLESTONE.get());
        this.simpleBlock(ModItems.SILICA_STONE.get());
        this.simpleBlock(ModItems.SILICA_BRICKS.get());
        this.simpleBlock(ModItems.SILICA_POLISHED.get());

        this.stairsBlock((StairBlock)ModItems.SILICA_STONE_STAIRS.get(), modLoc("block/silica_stone"));
        this.slabBlock((SlabBlock)ModItems.SILICA_STONE_SLAB.get(), modLoc("block/silica_stone"), modLoc("block/silica_stone"));

        this.slabBlock((SlabBlock)ModItems.SILICA_POLISHED_SLAB.get(), modLoc("block/silica_polished"), modLoc("block/silica_polished"));

        this.stairsBlock((StairBlock)ModItems.SILICA_COBBLESTONE_STAIRS.get(), modLoc("block/silica_cobblestone"));
        this.slabBlock((SlabBlock)ModItems.SILICA_COBBLESTONE_SLAB.get(), modLoc("block/silica_cobblestone"), modLoc("block/silica_cobblestone"));
        wallBlockWithInventory((WallBlock)ModItems.SILICA_COBBLESTONE_WALL.get(), modLoc("block/silica_cobblestone"));

        this.stairsBlock((StairBlock)ModItems.SILICA_BRICKS_STAIRS.get(), modLoc("block/silica_bricks"));
        this.slabBlock((SlabBlock)ModItems.SILICA_BRICKS_SLAB.get(), modLoc("block/silica_bricks"), modLoc("block/silica_bricks"));
        wallBlockWithInventory((WallBlock)ModItems.SILICA_BRICKS_WALL.get(), modLoc("block/silica_bricks"));
    }

    private void wallBlockWithInventory(WallBlock wallBlock, ResourceLocation texture){
        simpleBlockItem(wallBlock, models().wallInventory(wallBlock.getRegistryName().toString(), texture));
        this.wallBlock(wallBlock, texture);
    }



}
