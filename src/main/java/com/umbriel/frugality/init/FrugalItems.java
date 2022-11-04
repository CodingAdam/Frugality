package com.umbriel.frugality.init;

import com.umbriel.frugality.Frugality;
import com.umbriel.frugality.block.*;
import com.umbriel.frugality.block.workstation.BrickBlastFurnace;
import com.umbriel.frugality.block.workstation.BrickFurnace;
import com.umbriel.frugality.block.cauldron.*;
import com.umbriel.frugality.block.workstation.CrushingBlock;
import com.umbriel.frugality.item.*;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
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
public class FrugalItems {
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

    public static final RegistryObject<Block> SALT_BLOCK = register("salt_block", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(2.0F, 3.0F).sound(SoundType.GRAVEL)), false);
    public static final RegistryObject<Block> SALT_ORE = register("salt_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.SAND).strength(2.0F, 3.0F).sound(SoundType.GRAVEL), UniformInt.of(1, 3)), false);
    public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    // public static final RegistryObject<Item> BLUE_SALT = ITEMS.register("blue_salt", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    // public static final RegistryObject<Item> RED_SALT = ITEMS.register("red_salt", () -> new Item(new Item.Properties().tab(Frugality.TAB)));



    public static final RegistryObject<Block> SILICA_STONE = register("silica_stone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_STONE_SLAB = register("silica_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_STONE_STAIRS = register("silica_stone_stairs",
            () -> new StairBlock(SILICA_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);

    // Silica Cobble Blocks
    public static final RegistryObject<Block> SILICA_COBBLESTONE = register("silica_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_COBBLESTONE_SLAB = register("silica_cobblestone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_COBBLESTONE_STAIRS = register("silica_cobblestone_stairs",
            () -> new StairBlock(SILICA_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_COBBLESTONE_WALL = register("silica_cobblestone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);

    public static final RegistryObject<Block> SILICA_BRICKS = register("silica_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_BRICKS_SLAB = register("silica_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_BRICKS_STAIRS = register("silica_bricks_stairs",
            () -> new StairBlock(SILICA_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_BRICKS_WALL = register("silica_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);


    public static final RegistryObject<Block> SILICA_POLISHED = register("silica_polished",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);
    public static final RegistryObject<Block> SILICA_POLISHED_SLAB = register("silica_polished_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.3F, 5.5F).sound(SoundType.STONE)), false);

    // Figure out if never is required
    //public static final RegistryObject<Block> SILICA_GLASS_BLOCK = register("silica_glass_block", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.2F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(FrugalItems::never).isRedstoneConductor(FrugalItems::never).isSuffocating(FrugalItems::never).isViewBlocking(FrugalItems::never)), false);

    //public static final RegistryObject<Block> RAW_SILICA_BLOCK = register("raw_silica_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

    public static final RegistryObject<Block> SILICA_SAND = register("silica_sand", () -> new SandBlock(16116468, BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_PURPLE).strength(0.5F).sound(SoundType.SAND)), false);

    public static final RegistryObject<Block> BRICK_FURNACE = register("brick_furnace", () -> new BrickFurnace(BlockBehaviour.Properties.of(Material.STONE).
            requiresCorrectToolForDrops().strength(3.0F).lightLevel(litBlockEmission(12))), false);
    public static final RegistryObject<Block> BRICK_BLAST_FURNACE = register("brick_blast_furnace", () -> new BrickBlastFurnace(BlockBehaviour.Properties.of(Material.STONE).
            requiresCorrectToolForDrops().strength(3.0F).lightLevel(litBlockEmission(12))), false);

    public static final RegistryObject<Block> CLAY_BRICKS = register("clay_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.CLAY, MaterialColor.COLOR_GRAY).strength(1.0F, 3.5F).sound(SoundType.GRAVEL)) ,false);

    public static final RegistryObject<Block> CRUSHING_STONE = register("crushing_stone",
            () -> new CrushingBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), false);
    public static final RegistryObject<Block> CRUSHING_TERRACOTTA = register("crushing_terracotta",
            () -> new CrushingBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_RED).requiresCorrectToolForDrops().strength(1.25F, 4.2F)), false);



    public static final RegistryObject<Block> SLOW_FIRE = BLOCKS.register("slow_fire", () -> new SlowFireBlock(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_ORANGE).noCollission().instabreak().lightLevel((state) -> 15).sound(SoundType.WOOL)));

    public static final RegistryObject<Item> CHARRED_SHARDS = ITEMS.register("charred_shards", () -> new FuelItem(new Item.Properties().tab(Frugality.TAB)).setBurnTime(400));

    public static final RegistryObject<Item> BARK = ITEMS.register("bark", () -> new FuelItem(new Item.Properties().tab(Frugality.TAB)).setBurnTime(50));

    public static final RegistryObject<Item> FIRE_STARTER = ITEMS.register("fire_starter", () -> new StarterItem((new Item.Properties()).durability(15).tab(Frugality.TAB)));

    public static final RegistryObject<Item> WOODEN_SHEARS = ITEMS.register("wooden_shears", () -> new ShearsItem((new Item.Properties()).durability(56).tab(Frugality.TAB)));

    public static final RegistryObject<Item> COPPER_STARTER = ITEMS.register("copper_starter", () -> new StarterItem((new Item.Properties()).durability(55).tab(Frugality.TAB)));

    public static final RegistryObject<Item> COPPER_CUP = ITEMS.register("copper_cup", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Block> TREE_TAP = register("tree_tap",
            () -> new TapBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(1.0F, 1.0F)), false);

    public static final RegistryObject<Block> MELTER = register("melter",
            () -> new MelterBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(1.0F, 1.0F)), true);

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SILICA = ITEMS.register("raw_silica", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> SILICA_DUST = ITEMS.register("silica_dust", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    //public static final RegistryObject<Item> SILICA_GLASS = ITEMS.register("silica_glass", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    public static final RegistryObject<Item> CLAY_BRICK = ITEMS.register("clay_brick", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    //public static final RegistryObject<Item> SMALL_RAW_IRON = ITEMS.register("small_raw_iron", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    //public static final RegistryObject<Item> SMALL_RAW_COPPER = ITEMS.register("small_raw_copper", () -> new Item(new Item.Properties().tab(Frugality.TAB)));
    //public static final RegistryObject<Item> SMALL_RAW_GOLD = ITEMS.register("small_raw_gold", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> FIBER = ITEMS.register("fiber", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> COMPOST = ITEMS.register("compost", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> THERMAL_STONE = ITEMS.register("thermal_stone", () -> new ThermalItem(new Item.Properties().tab(Frugality.TAB).stacksTo(16)));
    public static final RegistryObject<Item> HEATED_STONE = ITEMS.register("heated_stone", () -> new ThermalItem(new Item.Properties().tab(Frugality.TAB).stacksTo(16)));
    public static final RegistryObject<Item> CHILLED_STONE = ITEMS.register("chilled_stone", () -> new ThermalItem(new Item.Properties().tab(Frugality.TAB).stacksTo(16)));
    public static final RegistryObject<Item> WARPED_STONE = ITEMS.register("warped_stone", () -> new ThermalItem(new Item.Properties().tab(Frugality.TAB).stacksTo(16)));

    public static final RegistryObject<Block>ROSE_GOLD_BLOCK = register("rose_gold_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(4.5F, 6.0F).sound(SoundType.METAL)), false);
    public static final RegistryObject<Item> ROSE_GOLD_INGOT = ITEMS.register("rose_gold_ingot", () -> new Item(new Item.Properties().tab(Frugality.TAB)));

    public static final RegistryObject<Item> ROSE_GOLD_HELMET = ITEMS.register("rose_gold_helmet",  ()-> new ArmorItem(FrugalArmors.COPPER, EquipmentSlot.HEAD, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_CHESTPLATE = ITEMS.register("rose_gold_chestplate", ()-> new ArmorItem(FrugalArmors.COPPER, EquipmentSlot.CHEST, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_LEGGINGS = ITEMS.register("rose_gold_leggings",  ()-> new ArmorItem(FrugalArmors.COPPER, EquipmentSlot.LEGS, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_BOOTS = ITEMS.register("rose_gold_boots",  ()-> new ArmorItem(FrugalArmors.COPPER, EquipmentSlot.FEET, (new Item.Properties()).tab(Frugality.TAB)));

    public static final RegistryObject<Item> ROSE_GOLD_SWORD = ITEMS.register("rose_gold_sword", ()-> new SwordItem(FrugalTiers.ROSE_GOLD, 3, -2.4F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_SHOVEL = ITEMS.register("rose_gold_shovel", ()-> new ShovelItem(FrugalTiers.ROSE_GOLD, 1.5F, -3.0F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_PICKAXE = ITEMS.register("rose_gold_pickaxe", ()->new PickaxeItem(FrugalTiers.ROSE_GOLD, 1, -2.8F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_AXE = ITEMS.register("rose_gold_axe", ()-> new AxeItem(FrugalTiers.ROSE_GOLD, 6.5F, -3.1F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_HOE = ITEMS.register("rose_gold_hoe", ()-> new HoeItem(FrugalTiers.ROSE_GOLD, -2, -1.5F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> ROSE_GOLD_HAMMER = ITEMS.register("rose_gold_hammer", ()-> new HammerItem(FrugalTiers.ROSE_GOLD, -1, -2.5F, (new Item.Properties()).tab(Frugality.TAB)));

    public static final RegistryObject<Item> WOODEN_HAMMER = ITEMS.register("wooden_hammer", ()-> new HammerItem(Tiers.WOOD, 1, -2.6F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer", ()-> new HammerItem(Tiers.STONE, 0, -2.6F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> GOLDEN_HAMMER = ITEMS.register("golden_hammer", ()-> new HammerItem(Tiers.GOLD, 1, -2.4F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", ()-> new HammerItem(Tiers.IRON, -1, -2.4F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", ()-> new HammerItem(Tiers.DIAMOND, -2, -2.3F, (new Item.Properties()).tab(Frugality.TAB)));
    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", ()-> new HammerItem(Tiers.NETHERITE, -3, -2.4F, (new Item.Properties()).tab(Frugality.TAB).fireResistant()));

    public static final RegistryObject<Item> WOODEN_MORTAR = ITEMS.register("wooden_mortar", ()-> new MortarItem(Ingredient.of(ItemTags.PLANKS), new Item.Properties().tab(Frugality.TAB).durability(36)));
    public static final RegistryObject<Item> STONE_MORTAR = ITEMS.register("stone_mortar", ()-> new MortarItem(Ingredient.of(ItemTags.STONE_CRAFTING_MATERIALS), new Item.Properties().tab(Frugality.TAB).durability(84)));
    public static final RegistryObject<Item> OBSIDIAN_MORTAR = ITEMS.register("obsidian_mortar", ()-> new MortarItem(Ingredient.of(Items.OBSIDIAN), new Item.Properties().tab(Frugality.TAB).durability(198)));

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

}
