package com.umbriel.frugality.data;

import com.umbriel.frugality.Frugality;
import net.minecraft.data.DataGenerator;
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


        simpleItem("bark", generated);
        simpleItem("compost", generated);
        simpleItem("copper_cup", generated);

        simpleItem("thermal_stone", generated);
        simpleItem("heated_stone", generated);
        simpleItem("chilled_stone", generated);
        simpleItem("warped_stone", generated);


    }

    private void simpleItem(String name, ModelFile item){
        getBuilder(name).parent(item).texture("layer0", "item/" + name);
    }
}
