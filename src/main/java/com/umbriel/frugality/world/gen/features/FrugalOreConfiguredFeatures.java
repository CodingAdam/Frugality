package com.umbriel.frugality.world.gen.features;

import com.umbriel.frugality.init.FrugalItems;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

public class FrugalOreConfiguredFeatures {

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SALT = FeatureUtils.register("ore_salt", Feature.ORE,
            new OreConfiguration(new BlockMatchTest(Blocks.GRAVEL), FrugalItems.SALT_ORE.get().defaultBlockState(), 15));

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_SILICA = FeatureUtils.register("ore_silica", Feature.ORE,
            new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, FrugalItems.SALT_ORE.get().defaultBlockState(), 64));

}
