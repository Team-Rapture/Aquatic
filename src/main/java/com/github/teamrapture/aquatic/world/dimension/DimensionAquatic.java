package com.github.teamrapture.aquatic.world.dimension;

import com.github.teamrapture.aquatic.config.AquaticConfig;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionAquatic {

    public static DimensionType dimension;

    public static void init() {
        dimension = DimensionType.register("Aquatic", "_aquatic", AquaticConfig.dimension.dimensionID, WorldProviderAquatic.class,
                false);
        DimensionManager.registerDimension(AquaticConfig.dimension.dimensionID, dimension);
    }
}
