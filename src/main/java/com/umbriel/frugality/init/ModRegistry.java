package com.umbriel.frugality.init;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.block.*;
import com.umbriel.frugality.block.BrickFurnaces.BrickBlastFurnace;
import com.umbriel.frugality.block.BrickFurnaces.BrickFurnace;
import com.umbriel.frugality.block.Cauldrons.*;
import com.umbriel.frugality.item.Armors;
import com.umbriel.frugality.item.FireStarter;
import com.umbriel.frugality.item.FuelItem;
import com.umbriel.frugality.item.ModTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("deprecation")
public class ModRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Frugality.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Frugality.MODID);


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static final RegistryObject<Block> LARGE_COMPOSTER = register("large_composter", () -> new LargerComposter(BlockBehaviour.Properties.of(Material.WOOD).strength(0.6F).sound(SoundType.WOOD)));



    public static final RegistryObject<Block> WOODEN_CAULDRON = register("wood_cauldron",
            () -> new CustomCauldron(BlockBehaviour.Properties.of(Material.WOOD).strength(0.6F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WATER_WOODEN_CAULDRON = BLOCKS.register("water_wood_cauldron",
            () -> new CustomLayeredCauldron(BlockBehaviour.Properties.copy(WOODEN_CAULDRON.get()), CustomLayeredCauldron.RAIN, CustomCauldronInteraction.WATER));
    public static final RegistryObject<Block> LAVA_WOODEN_CAULDRON = BLOCKS.register("lava_wood_cauldron",
            () ->new CustomLavaCauldron(BlockBehaviour.Properties.copy(WOODEN_CAULDRON.get()).lightLevel((p_152690_) -> 15)));
    public static final RegistryObject<Block> SNOW_WOODEN_CAULDRON = BLOCKS.register("snow_wood_cauldron",
            () -> new CustomSnowCauldron(BlockBehaviour.Properties.copy(WOODEN_CAULDRON.get()), CustomSnowCauldron.SNOW, CustomCauldronInteraction.POWDER_SNOW));

    public static final RegistryObject<Block> STONE_CAULDRON = register("stone_cauldron",
            () -> new CustomCauldron(BlockBehaviour.Properties.of(Material.STONE).strength(2.5F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> WATER_STONE_CAULDRON = BLOCKS.register("water_stone_cauldron",
            () -> new CustomLayeredCauldron(BlockBehaviour.Properties.copy(STONE_CAULDRON.get()), CustomLayeredCauldron.RAIN, CustomCauldronInteraction.WATER));
    public static final RegistryObject<Block> LAVA_STONE_CAULDRON = BLOCKS.register("lava_stone_cauldron",
            () ->new CustomLavaCauldron(BlockBehaviour.Properties.copy(STONE_CAULDRON.get()).lightLevel((p_152690_) -> 15)));
    public static final RegistryObject<Block> SNOW_STONE_CAULDRON = BLOCKS.register("snow_stone_cauldron",
            () -> new CustomSnowCauldron(BlockBehaviour.Properties.copy(STONE_CAULDRON.get()), CustomSnowCauldron.SNOW, CustomCauldronInteraction.POWDER_SNOW));

    public static final RegistryObject<Block> CHARRED_LOG = register("charred_log", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.NETHER_WART)));

    public static final RegistryObject<Block> MUD_BLOCK = register("mud_block", () -> new MudBlock(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.NETHER_WART)));

    public static final RegistryObject<Block> SILICA_GLASS_BLOCK = register("silica_glass_block", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.2F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(ModRegistry::never).isRedstoneConductor(ModRegistry::never).isSuffocating(ModRegistry::never).isViewBlocking(ModRegistry::never)));

    public static final RegistryObject<Block> RAW_SILICA_BLOCK = register("raw_silica_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    public static final RegistryObject<Block> BRICK_FURNACE = register("brick_furnace", () -> new BrickFurnace(BlockBehaviour.Properties.of(Material.STONE).
            requiresCorrectToolForDrops().strength(3.0F).lightLevel(litBlockEmission(12))));
    public static final RegistryObject<Block> BRICK_BLAST_FURNACE = register("brick_blast_furnace", () -> new BrickBlastFurnace(BlockBehaviour.Properties.of(Material.STONE).
            requiresCorrectToolForDrops().strength(3.0F).lightLevel(litBlockEmission(12))));

    public static final RegistryObject<Block> CRUSHING_STONE = register("crushing_stone",
            () -> new CrushingBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> CRUSHING_TERRACOTTA = register("crushing_terracotta",
            () -> new CrushingBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_RED).requiresCorrectToolForDrops().strength(1.25F, 4.2F)));

    public static final RegistryObject<Block> SLOW_FIRE = BLOCKS.register("slow_fire", () -> new SlowFire(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_ORANGE).noCollission().instabreak().lightLevel((state) -> 15).sound(SoundType.WOOL)));

    public static final RegistryObject<Item> CHARRED_SHARDS = ITEMS.register("charred_shards", () -> new FuelItem(new Item.Properties().tab(Frugality.TAB)).setBurnTime(400));

    public static final RegistryObject<Item> FIRE_STARTER = ITEMS.register("fire_starter", () -> new FireStarter((new Item.Properties()).durability(15).tab(Frugality.TAB)));

    public static final RegistryObject<Item> COPPER_STARTER = ITEMS.register("copper_starter", () -> new FireStarter((new Item.Properties()).durability(55).tab(Frugality.TAB)));

    public static final RegistryObject<Block> TREE_TAP = register("tree_tap", () -> new TreeTap(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(1.0F, 1.0F)));

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SILICA = ITEMS.register("raw_silica", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SILICA_GLASS = ITEMS.register("silica_glass", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> CLAY_BRICK = ITEMS.register("clay_brick", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> SMALL_RAW_IRON = ITEMS.register("small_raw_iron", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SMALL_RAW_COPPER = ITEMS.register("small_raw_copper", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SMALL_RAW_GOLD = ITEMS.register("small_raw_gold", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> FIBER = ITEMS.register("fiber", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Block>ROSE_GOLD_BLOCK = register("rose_gold_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(4.5F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Item> ROSE_GOLD_INGOT = ITEMS.register("rose_gold_ingot", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("rose_gold_helmet",  ()-> new ArmorItem(Armors.COPPER, EquipmentSlot.HEAD, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("rose_gold_chestplate", ()-> new ArmorItem(Armors.COPPER, EquipmentSlot.CHEST, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("rose_gold_leggings",  ()-> new ArmorItem(Armors.COPPER, EquipmentSlot.LEGS, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("rose_gold_boots",  ()-> new ArmorItem(Armors.COPPER, EquipmentSlot.FEET, (new Item.Properties()).tab(Frugality.TAB)));

    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("rose_gold_sword", ()-> new SwordItem(ModTiers.ROSE_GOLD, 3, -2.4F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("rose_gold_shovel", ()-> new ShovelItem(ModTiers.ROSE_GOLD, 1.5F, -3.0F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("rose_gold_pickaxe", ()->new PickaxeItem(ModTiers.ROSE_GOLD, 1, -2.8F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("rose_gold_axe", ()-> new AxeItem(ModTiers.ROSE_GOLD, 6.5F, -3.1F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("rose_gold_hoe", ()-> new HoeItem(ModTiers.ROSE_GOLD, -2, -1.5F, (new Item.Properties()).tab(Frugality.TAB)));


    private static ToIntFunction<BlockState> litBlockEmission(int value) {
        return (light) -> {
            return light.getValue(BlockStateProperties.LIT) ? value : 0;
        };
    }

    public static RegistryObject<Block> register(String id, Supplier<Block> supplier){
        RegistryObject<Block> block = BLOCKS.register(id, supplier);
        ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties().tab(Frugality.TAB)));
        return block;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

}
