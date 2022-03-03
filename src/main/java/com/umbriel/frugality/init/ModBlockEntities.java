package com.umbriel.frugality.init;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.blockentities.BrickBlastFurnaceBlockEntity;
import com.umbriel.frugality.blockentities.BrickFurnaceBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= Frugality.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlockEntities {

    public static BlockEntityType<?> BRICK_FURNACE;
    public static BlockEntityType<?> BRICK_BLAST_FURNACE;

    @SubscribeEvent
    public static void init(RegistryEvent.Register<BlockEntityType<?>> event) {
        BRICK_FURNACE = register(BrickFurnaceBlockEntity::new, "brick_furnace", ModRegistry.BRICK_FURNACE.get(), event);
        BRICK_BLAST_FURNACE = register(BrickBlastFurnaceBlockEntity::new, "brick_blast_furnace", ModRegistry.BRICK_BLAST_FURNACE.get(), event);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(BlockEntityType.BlockEntitySupplier<T> supplier, String registryName, Block block, RegistryEvent.Register<BlockEntityType<?>> registryEvent) {
        BlockEntityType<T> blockEntityType = BlockEntityType.Builder.of(supplier, block).build(null);
        blockEntityType.setRegistryName(registryName);
        registryEvent.getRegistry().register(blockEntityType);
        return blockEntityType;
    }

}