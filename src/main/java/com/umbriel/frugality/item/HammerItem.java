package com.umbriel.frugality.item;

import com.google.common.collect.Sets;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Set;

public class HammerItem extends PickaxeItem {

    public HammerItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment enchantment) {
        Set<Enchantment> ALLOWED_ENCHANTMENTS = Sets.newHashSet(Enchantments.BLOCK_FORTUNE, Enchantments.BLOCK_EFFICIENCY, Enchantments.SILK_TOUCH);
        if (ALLOWED_ENCHANTMENTS.contains(enchantment)) {
            return true;
        }
        Set<Enchantment> DENIED_ENCHANTMENTS = Sets.newHashSet(Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.MOB_LOOTING);
        if (DENIED_ENCHANTMENTS.contains(enchantment)) {
            return false;
        }
        return enchantment.category.canEnchant(stack.getItem());
    }

}
