package com.umbriel.frugality.world.gen.features;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class FrugalOrePlacements {

    public static final Holder<PlacedFeature> ORE_SALT_PLACED = PlacementUtils.register("ore_salt", FrugalOreConfiguredFeatures.ORE_SALT,
            commonOrePlacement(8, HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(45),
                    VerticalAnchor.absolute(150)
            )));

    public static final Holder<PlacedFeature> ORE_SILICA_PLACED = PlacementUtils.register("ore_silica", FrugalOreConfiguredFeatures.ORE_SILICA,
            commonOrePlacement(6, HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(56),
                    VerticalAnchor.absolute(128)
            )));


    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
}
