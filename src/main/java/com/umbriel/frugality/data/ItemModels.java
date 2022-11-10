package com.umbriel.frugality.data;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {
    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Frugality.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile generated = getExistingFile(mcLoc("item/generated"));

        withExistingParent("silica_stone",modLoc("block/silica_stone"));
        withExistingParent("silica_stone_slab", modLoc("block/silica_stone_slab"));
        withExistingParent("silica_stone_stairs", modLoc("block/silica_stone_stairs"));
        withExistingParent("silica_cobblestone", modLoc("block/silica_cobblestone"));
        withExistingParent("silica_cobblestone_wall", modLoc("block/silica_cobblestone_wall"));
        withExistingParent("silica_cobblestone_slab", modLoc("block/silica_cobblestone_slab"));
        withExistingParent("silica_cobblestone_stairs", modLoc("block/silica_cobblestone_stairs"));

        withExistingParent("silica_polished",modLoc("block/silica_polished"));
        withExistingParent("silica_polished_slab", modLoc("block/silica_polished_slab"));
        withExistingParent("silica_bricks",modLoc("block/silica_bricks"));
        withExistingParent("silica_bricks_wall", modLoc("block/silica_bricks_wall"));
        withExistingParent("silica_bricks_slab", modLoc("block/silica_bricks_slab"));
        withExistingParent("silica_bricks_stairs", modLoc("block/silica_bricks_stairs"));

        withExistingParent("salt_ore",modLoc("block/salt_ore"));
        withExistingParent("salt_block",modLoc("block/salt_block"));
        withExistingParent("silica_sand",modLoc("block/silica_sand"));
        withExistingParent("clay_bricks",modLoc("block/clay_bricks"));
        withExistingParent("rose_gold_block",modLoc("block/rose_gold_block"));
        withExistingParent("charred_log",modLoc("block/charred_log"));

        simpleItem("salt", generated);
        simpleItem("blue_salt", generated);
        simpleItem("red_salt", generated);
        simpleItem("bark", generated);
        simpleItem("compost", generated);
        simpleItem("copper_cup", generated);

        simpleItem("thermal_stone", generated);
        simpleItem("heated_stone", generated);
        simpleItem("chilled_stone", generated);
        simpleItem("warped_stone", generated);

        simpleItem("wooden_shears", generated);

        simpleItem("wooden_mortar", generated);
        simpleItem("stone_mortar", generated);
        simpleItem("obsidian_mortar", generated);

        simpleItem("silica_dust", generated);

    }

    private void simpleItem(String name, ModelFile item){
        getBuilder(name).parent(item).texture("layer0", "item/" + name);
    }
}
