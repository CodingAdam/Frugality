package com.umbriel.frugality.data;

import com.umbriel.frugality.Frugality;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {
    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Frugality.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("silica_stone", modLoc("block/silica_stone"));
        withExistingParent("silica_cobblestone", modLoc("block/silica_cobblestone"));

        ModelFile generated = getExistingFile(mcLoc("item/generated"));
    }

    private ItemModelBuilder simpleItem(ModelFile item, String name){
        return getBuilder(name).parent(item).texture("layer0", "items/" + name);
    }
}
