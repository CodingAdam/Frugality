package com.umbriel.frugality.util.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ForgeTags {

    public static final Tag.Named<Item> TOOLS = forgeItemTag("tools");
    public static final Tag.Named<Item> TOOLS_AXES = forgeItemTag("tools/axes");
    public static final Tag.Named<Item> TOOLS_KNIVES = forgeItemTag("tools/knives");
    public static final Tag.Named<Item> TOOLS_PICKAXES = forgeItemTag("tools/pickaxes");
    public static final Tag.Named<Item> TOOLS_SHOVELS = forgeItemTag("tools/shovels");
    public static final Tag.Named<Item> TOOLS_HAMMERS = forgeItemTag("tools/hammers");

    public static final Tag.Named<Item> GLASS = forgeItemTag("glass");
    public static final Tag.Named<Item> SAND = forgeItemTag("sand");


    private static Tag.Named<Item> forgeItemTag(String path) {
        return ItemTags.bind(new ResourceLocation("forge", path).toString());
    }

    private static Tag.Named<Block> forgeBlockTag(String path) {
        return BlockTags.bind(new ResourceLocation("forge", path).toString());
    }
}
