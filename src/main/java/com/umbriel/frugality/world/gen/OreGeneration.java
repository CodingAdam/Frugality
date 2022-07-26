package com.umbriel.frugality.world.gen;

import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import static net.minecraft.data.worldgen.placement.OrePlacements.commonOrePlacement;

public class OreGeneration {
    public static void generateOres(final BiomeLoadingEvent event){
        if(!(event.getCategory().equals(Biome.BiomeCategory.NETHER) || event.getCategory().equals(Biome.BiomeCategory.THEEND)) && (event.getCategory().equals(Biome.BiomeCategory.OCEAN) || event.getCategory().equals(Biome.BiomeCategory.RIVER))){
            generateOre(event, ModFillers.GRAVEL, FrugalItems.SALT_ORE.get().defaultBlockState(),
                    32, 15, 45, 150);

        }

        if(!(event.getCategory().equals(Biome.BiomeCategory.NETHER) || event.getCategory().equals(Biome.BiomeCategory.THEEND)) && (event.getCategory().equals(Biome.BiomeCategory.OCEAN) || event.getCategory().equals(Biome.BiomeCategory.RIVER))){
            generateOre(event, ModFillers.STONE, FrugalItems.SILICA_STONE.get().defaultBlockState(),
                    6, 64, 56, 128);

        }

    }

    private static void generateOre(BiomeLoadingEvent event, RuleTest fillerType, BlockState state, int rarity, int veinSize, int lower, int upper){
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Feature.ORE.
                configured(new OreConfiguration(fillerType, state, veinSize)).placed(commonOrePlacement(rarity, HeightRangePlacement.uniform(VerticalAnchor.absolute(lower), VerticalAnchor.absolute(upper)))));
    }


    public static final class ModFillers  {
        public static final RuleTest GRAVEL = new BlockMatchTest(Blocks.GRAVEL);
        public static final RuleTest STONE = new BlockMatchTest(Blocks.STONE);
    }



}
