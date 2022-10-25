package com.umbriel.frugality.util.tags;

import com.umbriel.frugality.Frugality;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

        public static final TagKey<Item> HAMMERS = ItemTag("tools/hammers");
        public static final TagKey<Item> MORTARS = ItemTag("tools/mortars");

        private static TagKey<Item> ItemTag(String path) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Frugality.MODID + ":" + path));
        }
}
