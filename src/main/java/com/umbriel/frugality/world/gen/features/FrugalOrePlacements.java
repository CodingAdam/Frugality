package com.umbriel.frugality.world.gen.features;

import com.umbriel.frugality.world.gen.features.FrugalOreConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static net.minecraft.data.worldgen.placement.OrePlacements.commonOrePlacement;

public class FrugalOrePlacements {

    public static final Holder<PlacedFeature> ORE_SALT_PLACED = PlacementUtils.register("ore_salt", FrugalOreConfiguredFeatures.ORE_SALT,
            commonOrePlacement(32, HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(45),
                    VerticalAnchor.absolute(150)
            )));

    public static final Holder<PlacedFeature> ORE_SILICA_PLACED = PlacementUtils.register("ore_silica", FrugalOreConfiguredFeatures.ORE_SILICA,
            commonOrePlacement(6, HeightRangePlacement.uniform(
                    VerticalAnchor.absolute(56),
                    VerticalAnchor.absolute(128)
            )));
}
