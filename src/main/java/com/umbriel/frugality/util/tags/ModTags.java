package com.umbriel.frugality.util.tags;

import com.umbriel.frugality.Frugality;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ModTags {

        public static final Tag.Named<Item> HAMMERS = ItemTag("tools/hammers");

        private static Tag.Named<Item> ItemTag(String path) {
            return ItemTags.bind(Frugality.MODID + ":" + path);
        }
}
