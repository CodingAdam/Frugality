package com.umbriel.frugality.init;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.blockentities.BrickBlastFurnaceBlockEntity;
import com.umbriel.frugality.blockentities.BrickFurnaceBlockEntity;
import com.umbriel.frugality.blockentities.CrushingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Frugality.MODID);

    public static void init() {
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final RegistryObject<BlockEntityType<CrushingBlockEntity>> CRUSHING_BLOCK = TILES.register("crushing_block",
            () -> BlockEntityType.Builder.of(CrushingBlockEntity::new, ModRegistry.CRUSHING_STONE.get(), ModRegistry.CRUSHING_TERRACOTTA.get()).build(null));

    public static final RegistryObject<BlockEntityType<BrickBlastFurnaceBlockEntity>> BRICK_BLAST_FURNACE = TILES.register("brick_blast_furnace",
            () -> BlockEntityType.Builder.of(BrickBlastFurnaceBlockEntity::new, ModRegistry.BRICK_BLAST_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<BrickFurnaceBlockEntity>> BRICK_FURNACE = TILES.register("brick_furnace",
            () -> BlockEntityType.Builder.of(BrickFurnaceBlockEntity::new, ModRegistry.BRICK_FURNACE.get()).build(null));

}