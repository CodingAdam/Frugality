package com.umbriel.frugality.loot.modifiers;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddFortunableItemModifier extends LootModifier {

    private final Item item;
    private final int count;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected AddFortunableItemModifier(LootItemCondition[] conditionsIn, Item itemIn, int count) {
        super(conditionsIn);
        this.item = itemIn;
        this.count = count;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Random random = new Random();
        ItemStack tool = context.getParam(LootContextParams.TOOL);

        if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) == 0) {
            int fortune_level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, tool);
            for (int i = 0; i < count + random.nextInt(1 + fortune_level); i++) {
                generatedLoot.add(new ItemStack(item));
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AddFortunableItemModifier>
    {
        @Override
        public AddFortunableItemModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] lootcondition) {
            Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation((GsonHelper.getAsString(object, "item"))));
            int count = GsonHelper.getAsInt(object, "count", 1);
            return new AddFortunableItemModifier(lootcondition, addedItem, count);
        }

        @Override
        public JsonObject write(AddFortunableItemModifier instance) {
            return new JsonObject();
        }
    }
}
