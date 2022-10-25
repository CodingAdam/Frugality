package com.umbriel.frugality.data;

import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.util.tags.ForgeTags;
import com.umbriel.frugality.util.tags.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(DataGenerator generator, BlockTagsProvider blockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.registerForgeTags();
        this.registerVanillaTags();
        this.registerFrugalTags();
    }

    private void registerVanillaTags(){
        tag(net.minecraft.tags.ItemTags.STONE_CRAFTING_MATERIALS)
                .add(FrugalItems.SILICA_COBBLESTONE.get().asItem());
    }

    private void registerFrugalTags(){
        tag(ModTags.HAMMERS)
                .add(FrugalItems.DIAMOND_HAMMER.get())
                .add(FrugalItems.NETHERITE_HAMMER.get())
                .add(FrugalItems.GOLDEN_HAMMER.get())
                .add(FrugalItems.IRON_HAMMER.get())
                .add(FrugalItems.WOODEN_HAMMER.get())
                .add(FrugalItems.ROSE_GOLD_HAMMER.get())
                .add(FrugalItems.STONE_HAMMER.get());

        tag(ModTags.MORTARS)
                .add(FrugalItems.OBSIDIAN_MORTAR.get())
                .add(FrugalItems.STONE_MORTAR.get())
                .add(FrugalItems.WOODEN_MORTAR.get());
    }

    private void registerForgeTags(){
        tag(ForgeTags.BARK).add(FrugalItems.BARK.get());
        tag(ForgeTags.SALT).add(FrugalItems.SALT.get());
        tag(ForgeTags.COPPER_NUGGET).add(FrugalItems.COPPER_NUGGET.get());
        tag(ForgeTags.ROSE_GOLD_INGOT).add(FrugalItems.ROSE_GOLD_INGOT.get());

        tag(ForgeTags.TOOLS_AXES)
                .add(Items.WOODEN_AXE)
                .add(Items.STONE_AXE)
                .add(Items.IRON_AXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.NETHERITE_AXE)
                .add(FrugalItems.ROSE_GOLD_AXE.get());

        tag(ForgeTags.TOOLS_PICKAXES)
                .add(Items.WOODEN_PICKAXE)
                .add(Items.STONE_PICKAXE)
                .add(Items.IRON_PICKAXE)
                .add(Items.GOLDEN_PICKAXE)
                .add(Items.DIAMOND_PICKAXE)
                .add(Items.NETHERITE_PICKAXE)
                .add(FrugalItems.ROSE_GOLD_PICKAXE.get());

        tag(ForgeTags.TOOLS_SHOVELS)
                .add(Items.WOODEN_SHOVEL)
                .add(Items.STONE_SHOVEL)
                .add(Items.IRON_SHOVEL)
                .add(Items.GOLDEN_SHOVEL)
                .add(Items.DIAMOND_SHOVEL)
                .add(Items.NETHERITE_SHOVEL)
                .add(FrugalItems.ROSE_GOLD_SHOVEL.get());

        tag(ForgeTags.TOOLS_HAMMERS)
                .add(FrugalItems.DIAMOND_HAMMER.get())
                .add(FrugalItems.NETHERITE_HAMMER.get())
                .add(FrugalItems.GOLDEN_HAMMER.get())
                .add(FrugalItems.IRON_HAMMER.get())
                .add(FrugalItems.WOODEN_HAMMER.get())
                .add(FrugalItems.ROSE_GOLD_HAMMER.get())
                .add(FrugalItems.STONE_HAMMER.get());

    }
}
