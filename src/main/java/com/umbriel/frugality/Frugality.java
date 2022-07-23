package com.umbriel.frugality;

import com.umbriel.frugality.block.cauldron.CustomCauldronInteraction;
import com.umbriel.frugality.client.RenderFix;
import com.umbriel.frugality.event.CommonEvents;
import com.umbriel.frugality.init.ModBlockEntities;
import com.umbriel.frugality.init.ModRecipes;
import com.umbriel.frugality.init.ModItems;
import com.umbriel.frugality.world.gen.OreGeneration;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.umbriel.frugality.init.ModItems.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("frugality")
public class Frugality
{
    public static final String MODID = "frugality";
    public static final Logger log = LogManager.getLogger(MODID);


    public Frugality() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.init();
        ModRecipes.init();
        ModBlockEntities.init();
        bus.addListener(RenderFix::init);
        CustomCauldronInteraction.bootStrap();
        //MinecraftForge.EVENT_BUS.addListener(this::onLivingSpecialSpawn);
        MinecraftForge.EVENT_BUS.register(CommonEvents.class);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        bus.addListener(this::clientSetup);
        bus.addListener(RenderFix::registerBlockColors);

    }
    private void clientSetup(FMLClientSetupEvent event) {
        setupRenderLayers();
    }
    public static void setupRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(SLOW_FIRE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SILICA_GLASS_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MELTER.get(), RenderType.translucent());
    }


//    private void setStackIfEmpty(LivingEntity entity, EquipmentSlot slot, ItemStack item) {
//        if (entity.getItemBySlot(slot).isEmpty()) {
//            entity.setItemSlot(slot, item);
//        }
//    }
//
//    private void setEntityArmorWithChance(Random random, LivingEntity entity, Item sword, Item helmet, Item chestplate, Item leggings, Item boots) {
//        if (entity instanceof Zombie && random.nextDouble() <  0.07) {
//            setStackIfEmpty(entity, EquipmentSlot.MAINHAND, sword.asItem().getDefaultInstance());
//        }
//        if (random.nextDouble() <  0.5) {
//            setStackIfEmpty(entity, EquipmentSlot.HEAD, helmet.asItem().getDefaultInstance());
//        }
//        if (random.nextDouble() <  0.5) {
//            setStackIfEmpty(entity, EquipmentSlot.CHEST, chestplate.asItem().getDefaultInstance());
//        }
//        if (random.nextDouble() <  0.5) {
//            setStackIfEmpty(entity, EquipmentSlot.LEGS, leggings.asItem().getDefaultInstance());
//        }
//        if (random.nextDouble() <  0.5) {
//            setStackIfEmpty(entity, EquipmentSlot.FEET, boots.asItem().getDefaultInstance());
//        }
//    }
//    //Set to spawn based on difficulty
//    private void onLivingSpecialSpawn(LivingSpawnEvent.SpecialSpawn event) {
//        LivingEntity entity = event.getEntityLiving();
//        if (entity instanceof Zombie || entity instanceof Skeleton) {
//            Random random = event.getWorld().getRandom();
//            double chance = random.nextDouble();
//            if (chance < 0.02) {
//                int selection = random.nextInt(3);
//                if(selection == 0){
//                    setEntityArmorWithChance(random, entity, Items.IRON_SHOVEL, COPPER_HELMET.get(), COPPER_CHESTPLATE.get(),
//                            COPPER_LEGGINGS.get(), COPPER_BOOTS.get());
//                }
//                if(selection == 1){
//                    setEntityArmorWithChance(random, entity, Items.IRON_SWORD, COPPER_HELMET.get(), COPPER_CHESTPLATE.get(),
//                            COPPER_LEGGINGS.get(), COPPER_BOOTS.get());
//                }
//            }
//        }
//    }
    public static final CreativeModeTab TAB = new CreativeModeTab("frugalitytab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(REINFORCED_COMPOSTER.get());
        }
    };
}
