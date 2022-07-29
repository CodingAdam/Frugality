package com.umbriel.frugality.world.gen;

import com.umbriel.frugality.world.gen.features.FrugalOrePlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {

    public static void generateOres(final BiomeLoadingEvent event){
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        Biome.BiomeCategory biomeCategory  = event.getCategory();

        if(biomeCategory == Biome.BiomeCategory.OCEAN || biomeCategory == Biome.BiomeCategory.BEACH || biomeCategory == Biome.BiomeCategory.RIVER){
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrugalOrePlacements.ORE_SALT_PLACED);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FrugalOrePlacements.ORE_SILICA_PLACED);
        }

    }





}
