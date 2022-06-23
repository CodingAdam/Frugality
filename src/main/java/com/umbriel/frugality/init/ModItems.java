package com.umbriel.frugality.init;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.block.*;
import com.umbriel.frugality.block.BrickFurnaces.BrickBlastFurnace;
import com.umbriel.frugality.block.BrickFurnaces.BrickFurnace;
import com.umbriel.frugality.block.Cauldrons.*;
import com.umbriel.frugality.item.*;
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
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Frugality.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Frugality.MODID);


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    public static final RegistryObject<Block> REINFORCED_COMPOSTER = register("reinforced_composter", () -> new ReinforcedComposterBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.6F).sound(SoundType.WOOD)), false);



    public static final RegistryObject<Block> WOODEN_CAULDRON = register("wood_cauldron",
            () -> new CustomCauldron(BlockBehaviour.Properties.of(Material.WOOD).strength(0.6F).sound(SoundType.WOOD)), false);
    public static final RegistryObject<Block> WATER_WOODEN_CAULDRON = BLOCKS.register("water_wood_cauldron",
            () -> new CustomLayeredCauldron(BlockBehaviour.Properties.copy(WOODEN_CAULDRON.get()), CustomLayeredCauldron.RAIN, CustomCauldronInteraction.WATER));
    public static final RegistryObject<Block> LAVA_WOODEN_CAULDRON = BLOCKS.register("lava_wood_cauldron",
            () ->new CustomLavaCauldron(BlockBehaviour.Properties.copy(WOODEN_CAULDRON.get()).lightLevel((p_152690_) -> 15)));
    public static final RegistryObject<Block> SNOW_WOODEN_CAULDRON = BLOCKS.register("snow_wood_cauldron",
            () -> new CustomSnowCauldron(BlockBehaviour.Properties.copy(WOODEN_CAULDRON.get()), CustomSnowCauldron.SNOW, CustomCauldronInteraction.POWDER_SNOW));

    public static final RegistryObject<Block> STONE_CAULDRON = register("stone_cauldron",
            () -> new CustomCauldron(BlockBehaviour.Properties.of(Material.STONE).strength(2.5F).sound(SoundType.STONE).requiresCorrectToolForDrops()), false);
    public static final RegistryObject<Block> WATER_STONE_CAULDRON = BLOCKS.register("water_stone_cauldron",
            () -> new CustomLayeredCauldron(BlockBehaviour.Properties.copy(STONE_CAULDRON.get()), CustomLayeredCauldron.RAIN, CustomCauldronInteraction.WATER));
    public static final RegistryObject<Block> LAVA_STONE_CAULDRON = BLOCKS.register("lava_stone_cauldron",
            () ->new CustomLavaCauldron(BlockBehaviour.Properties.copy(STONE_CAULDRON.get()).lightLevel((p_152690_) -> 15)));
    public static final RegistryObject<Block> SNOW_STONE_CAULDRON = BLOCKS.register("snow_stone_cauldron",
            () -> new CustomSnowCauldron(BlockBehaviour.Properties.copy(STONE_CAULDRON.get()), CustomSnowCauldron.SNOW, CustomCauldronInteraction.POWDER_SNOW));

    public static final RegistryObject<Block> CHARRED_LOG = register("charred_log", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.NETHER_WART)), false);

    public static final RegistryObject<Block> MUD_BLOCK = register("mud_block", () -> new MudBlock(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.NETHER_WART)), false);

    public static final RegistryObject<Block> SILICA_STONE = register("silica_stone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_COBBLESTONE = register("silica_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    // Figure out if never is required
    public static final RegistryObject<Block> SILICA_GLASS_BLOCK = register("silica_glass_block", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.2F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(ModItems::never).isRedstoneConductor(ModItems::never).isSuffocating(ModItems::never).isViewBlocking(ModItems::never)), false);

    //public static final RegistryObject<Block> RAW_SILICA_BLOCK = register("raw_silica_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    public static final RegistryObject<Block> BRICK_FURNACE = register("brick_furnace", () -> new BrickFurnace(BlockBehaviour.Properties.of(Material.STONE).
            requiresCorrectToolForDrops().strength(3.0F).lightLevel(litBlockEmission(12))), false);
    public static final RegistryObject<Block> BRICK_BLAST_FURNACE = register("brick_blast_furnace", () -> new BrickBlastFurnace(BlockBehaviour.Properties.of(Material.STONE).
            requiresCorrectToolForDrops().strength(3.0F).lightLevel(litBlockEmission(12))), false);

    public static final RegistryObject<Block> CRUSHING_STONE = register("crushing_stone",
            () -> new CrushingBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), false);
    public static final RegistryObject<Block> CRUSHING_TERRACOTTA = register("crushing_terracotta",
            () -> new CrushingBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_RED).requiresCorrectToolForDrops().strength(1.25F, 4.2F)), false);



    public static final RegistryObject<Block> SLOW_FIRE = BLOCKS.register("slow_fire", () -> new SlowFireBlock(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_ORANGE).noCollission().instabreak().lightLevel((state) -> 15).sound(SoundType.WOOL)));

    public static final RegistryObject<Item> CHARRED_SHARDS = ITEMS.register("charred_shards", () -> new FuelItem(new Item.Properties().tab(Frugality.TAB)).setBurnTime(400));

    public static final RegistryObject<Item> BARK = ITEMS.register("bark", () -> new FuelItem(new Item.Properties().tab(Frugality.TAB)).setBurnTime(50));

    public static final RegistryObject<Item> FIRE_STARTER = ITEMS.register("fire_starter", () -> new FireStarterItem((new Item.Properties()).durability(15).tab(Frugality.TAB)));

    public static final RegistryObject<Item> COPPER_STARTER = ITEMS.register("copper_starter", () -> new FireStarterItem((new Item.Properties()).durability(55).tab(Frugality.TAB)));

    public static final RegistryObject<Block> TREE_TAP = register("tree_tap",
            () -> new TreeTapBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(1.0F, 1.0F)), false);

    public static final RegistryObject<Block> MELTER = register("melter",
            () -> new MelterBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(1.0F, 1.0F)), true);

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SILICA = ITEMS.register("raw_silica", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SILICA_GLASS = ITEMS.register("silica_glass", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> CLAY_BRICK = ITEMS.register("clay_brick", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> SMALL_RAW_IRON = ITEMS.register("small_raw_iron", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SMALL_RAW_COPPER = ITEMS.register("small_raw_copper", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SMALL_RAW_GOLD = ITEMS.register("small_raw_gold", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> FIBER = ITEMS.register("fiber", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> COMPOST = ITEMS.register("compost", () -> new Item(new Item.Properties().tab(Frugality.TAB)));


    public static final RegistryObject<Block>ROSE_GOLD_BLOCK = register("rose_gold_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(4.5F, 6.0F).sound(SoundType.METAL)), false);
    public static final RegistryObject<Item> ROSE_GOLD_INGOT = ITEMS.register("rose_gold_ingot", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> ROSE_GOLD_HELMET = ITEMS.register("rose_gold_helmet",  ()-> new ArmorItem(ModArmors.COPPER, EquipmentSlot.HEAD, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_CHESTPLATE = ITEMS.register("rose_gold_chestplate", ()-> new ArmorItem(ModArmors.COPPER, EquipmentSlot.CHEST, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_LEGGINGS = ITEMS.register("rose_gold_leggings",  ()-> new ArmorItem(ModArmors.COPPER, EquipmentSlot.LEGS, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_BOOTS = ITEMS.register("rose_gold_boots",  ()-> new ArmorItem(ModArmors.COPPER, EquipmentSlot.FEET, (new Item.Properties()).tab(Frugality.TAB)));

    public static final RegistryObject<Item> ROSE_GOLD_SWORD = ITEMS.register("rose_gold_sword", ()-> new SwordItem(ModTiers.ROSE_GOLD, 3, -2.4F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_SHOVEL = ITEMS.register("rose_gold_shovel", ()-> new ShovelItem(ModTiers.ROSE_GOLD, 1.5F, -3.0F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_PICKAXE = ITEMS.register("rose_gold_pickaxe", ()->new PickaxeItem(ModTiers.ROSE_GOLD, 1, -2.8F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_AXE = ITEMS.register("rose_gold_axe", ()-> new AxeItem(ModTiers.ROSE_GOLD, 6.5F, -3.1F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_HOE = ITEMS.register("rose_gold_hoe", ()-> new HoeItem(ModTiers.ROSE_GOLD, -2, -1.5F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_HAMMER = ITEMS.register("rose_gold_hammer", ()-> new HammerItem(ModTiers.ROSE_GOLD, -1, -2.5F, (new Item.Properties()).tab(Frugality.TAB)));

    public static final RegistryObject<Item> WOODEN_HAMMER = ITEMS.register("wooden_hammer", ()-> new HammerItem(Tiers.WOOD, 1, -2.6F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer", ()-> new HammerItem(Tiers.STONE, 0, -2.6F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> GOLDEN_HAMMER = ITEMS.register("golden_hammer", ()-> new HammerItem(Tiers.GOLD, 1, -2.4F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", ()-> new HammerItem(Tiers.IRON, -1, -2.4F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", ()-> new HammerItem(Tiers.DIAMOND, -2, -2.3F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", ()-> new HammerItem(Tiers.NETHERITE, -3, -2.4F, (new Item.Properties()).tab(Frugality.TAB).fireResistant()));

    private static ToIntFunction<BlockState> litBlockEmission(int value) {
        return (light) -> {
            return light.getValue(BlockStateProperties.LIT) ? value : 0;
        };
    }

    // Move these to future registration helper
    public static RegistryObject<Block> register(String id, Supplier<Block> supplier, boolean hidden){
        RegistryObject<Block> block = BLOCKS.register(id, supplier);
        if(!hidden){
            ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties().tab(Frugality.TAB)));
        }else{
            ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
        }

        return block;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

}
