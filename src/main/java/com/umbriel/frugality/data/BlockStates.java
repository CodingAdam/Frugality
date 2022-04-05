package com.umbriel.frugality.data;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Frugality.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(ModItems.SILICA_COBBLESTONE.get());
        this.simpleBlock(ModItems.SILICA_STONE.get());
    }


}
