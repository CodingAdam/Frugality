package com.umbriel.frugality;

import com.umbriel.frugality.block.cauldron.CustomCauldronInteraction;
import com.umbriel.frugality.client.RenderFix;
import com.umbriel.frugality.event.CommonEvents;
import com.umbriel.frugality.init.FrugalBlockEntities;
import com.umbriel.frugality.init.FrugalItems;
import com.umbriel.frugality.init.FrugalRecipes;
import com.umbriel.frugality.util.CauldronDispenseItemBehavior;
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

import static com.umbriel.frugality.init.FrugalItems.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("frugality")
public class Frugality
{
    public static final String MODID = "frugality";
    public static final Logger log = LogManager.getLogger(MODID);


    public Frugality() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FrugalItems.init();
        FrugalRecipes.init();
        FrugalBlockEntities.init();
        bus.addListener(RenderFix::init);
        CustomCauldronInteraction.bootStrap();
        CauldronDispenseItemBehavior.bootstrap();
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
        ItemBlockRenderTypes.setRenderLayer(MELTER.get(), RenderType.translucent());
    }

    public static final CreativeModeTab TAB = new CreativeModeTab("frugalitytab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(REINFORCED_COMPOSTER.get());
        }
    };
}
