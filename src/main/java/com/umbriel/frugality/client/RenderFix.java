package com.umbriel.frugality.client;

import com.umbriel.frugality.init.ModItems;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class RenderFix {
    public static void init(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModItems.STONE_CAULDRON.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.WOODEN_CAULDRON.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.TREE_TAP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.CRUSHING_TERRACOTTA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModItems.CRUSHING_STONE.get(), RenderType.cutout());
    }
    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        colors.register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
            return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageWaterColor(p_92622_, p_92623_) : -1;
        }, ModItems.WOODEN_CAULDRON.get());
        colors.register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
            return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageWaterColor(p_92622_, p_92623_) : -1;
        }, ModItems.WATER_WOODEN_CAULDRON.get());
        colors.register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
            return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageWaterColor(p_92622_, p_92623_) : -1;
        }, ModItems.STONE_CAULDRON.get());
        colors.register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
            return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageWaterColor(p_92622_, p_92623_) : -1;
        }, ModItems.WATER_STONE_CAULDRON.get());

    }
}

