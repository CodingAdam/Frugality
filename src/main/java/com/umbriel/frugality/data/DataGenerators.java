package com.umbriel.frugality.data;

import com.umbriel.frugality.Frugality;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import javax.xml.crypto.Data;

@Mod.EventBusSubscriber(modid = Frugality.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        generator.addProvider(new Recipes(generator));
        generator.addProvider(new BlockStates(generator, helper));
        generator.addProvider(new ItemModels(generator, helper));
    }
}
