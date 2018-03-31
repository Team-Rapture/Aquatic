package com.github.rapture.aquatic.dimensions;

import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.dimensions.provider.WorldProviderAquatic;
import com.github.rapture.aquatic.util.ParticleUtils;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionAquatic {

	public static DimensionType dimension;

	public static void init() {
		dimension = DimensionType.register("Aquatic", "_aquatic", AquaticConfig.dimensionID, WorldProviderAquatic.class,
				false);
	DimensionManager.registerDimension(AquaticConfig.dimensionID, dimension);
	}
}
