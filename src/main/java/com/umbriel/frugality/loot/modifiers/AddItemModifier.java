package com.umbriel.frugality.loot.modifiers;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class AddItemModifier extends LootModifier {

    private final Item item;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected AddItemModifier(LootItemCondition[] conditionsIn, Item itemIn) {
        super(conditionsIn);
        this.item = itemIn;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(item));
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AddItemModifier>
    {
        @Override
        public AddItemModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] lootcondition) {
            Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation((GsonHelper.getAsString(object, "item"))));
            return new AddItemModifier(lootcondition, addedItem);
        }

        @Override
        public JsonObject write(AddItemModifier instance) {
            return new JsonObject();
        }
    }
}
