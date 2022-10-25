package com.umbriel.frugality.item;

import com.google.common.collect.Sets;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public class MortarItem extends Item {

    private Ingredient material;

    public MortarItem(Ingredient material, Properties properties) {
        super(properties);
        this.material = material;

    }

    @Override
    public boolean isValidRepairItem(ItemStack item1, ItemStack item2) {
        return this.material.test(item2)|| super.isValidRepairItem(item1, item2);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack mortar = itemStack.copy();
        if(mortar.hurt(1, new Random(), null))
            mortar.setCount(0); // Bug: finishes stack without durability
        return mortar;
    }
}
