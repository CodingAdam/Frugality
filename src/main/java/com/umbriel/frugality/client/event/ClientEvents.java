package com.umbriel.frugality.client.event;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.client.renderer.CrushingBlockEntityRenderer;
import com.umbriel.frugality.init.ModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Frugality.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.CRUSHING_BLOCK.get(), CrushingBlockEntityRenderer::new);
    }

}
