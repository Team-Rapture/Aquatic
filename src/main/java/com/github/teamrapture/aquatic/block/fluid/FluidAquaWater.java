package com.github.teamrapture.aquatic.block.fluid;

import com.github.teamrapture.aquatic.Aquatic;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidAquaWater extends Fluid {

    public FluidAquaWater() {
        super("aquatic_water", new ResourceLocation(Aquatic.MODID, "blocks/fluids/aqua_water_still"), new ResourceLocation(Aquatic.MODID, "blocks/fluids/aqua_water_flowing"));
        this.setGaseous(false);
        this.setDensity(1000);
        this.setViscosity(500);
        this.setRarity(EnumRarity.COMMON);
    }
}
