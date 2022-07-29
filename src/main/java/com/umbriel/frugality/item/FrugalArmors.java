package com.umbriel.frugality.item;

import com.umbriel.frugality.Frugality;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@SuppressWarnings("NullableProblems")
public enum FrugalArmors implements ArmorMaterial {
        COPPER("copper", 10, new int[]{2, 4, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
            return Ingredient.of(Items.COPPER_INGOT);
        });


    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private FrugalArmors(String title, int multiplier, int[] slots, int value, SoundEvent sounds, float tough, float knock, Supplier<Ingredient> repair) {
        this.name = title;
        this.durabilityMultiplier = multiplier;
        this.slotProtections = slots;
        this.enchantmentValue = value;
        this.sound = sounds;
        this.toughness = tough;
        this.knockbackResistance = knock;
        this.repairIngredient = new LazyLoadedValue<>(repair);
    }

    public int getDurabilityForSlot(EquipmentSlot type) {
        return HEALTH_PER_SLOT[type.getIndex()] * this.durabilityMultiplier;
    }

    public int getDefenseForSlot(EquipmentSlot type) {
        return this.slotProtections[type.getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return Frugality.MODID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
