package com.umbriel.frugality.util.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ForgeTags {

    public static final TagKey<Item> TOOLS = forgeItemTag("tools");
    public static final TagKey<Item> TOOLS_AXES = forgeItemTag("tools/axes");
    public static final TagKey<Item> TOOLS_KNIVES = forgeItemTag("tools/knives");
    public static final TagKey<Item> TOOLS_PICKAXES = forgeItemTag("tools/pickaxes");
    public static final TagKey<Item> TOOLS_SHOVELS = forgeItemTag("tools/shovels");
    public static final TagKey<Item> TOOLS_HAMMERS = forgeItemTag("tools/hammers");

    public static final TagKey<Item> GLASS = forgeItemTag("glass");
    public static final TagKey<Item> SAND = forgeItemTag("sand");
    public static final TagKey<Item> SALT = forgeItemTag("salt");
    public static final TagKey<Item> BARK = forgeItemTag("bark");
    public static final TagKey<Item> ROSE_GOLD_INGOT = forgeItemTag("ingots/rose_gold");

    public static final TagKey<Item> COPPER_NUGGET = forgeItemTag("nuggets/copper");

    public static final TagKey<Block> ROSE_GOLD_BLOCK = forgeBlockTag("storage_blocks/rose_gold");

    private static TagKey<Item> forgeItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
    }

    private static TagKey<Block> forgeBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge", path));
    }
}
