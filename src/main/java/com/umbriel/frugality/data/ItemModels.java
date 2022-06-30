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
        withExistingParent("silica_cobblestone",modLoc("block/silica_cobblestone"));

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
