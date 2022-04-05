package com.umbriel.frugality.loot.modifiers;

import com.umbriel.frugality.Frugality;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Frugality.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootModifierHandler {

    @SubscribeEvent
    public static void registerModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new AddItemModifier.Serializer().setRegistryName(Frugality.MODID, "add_item"));
        event.getRegistry().register(new AddFortunableItemModifier.Serializer().setRegistryName(Frugality.MODID, "add_fortunable_item"));
    }
}
