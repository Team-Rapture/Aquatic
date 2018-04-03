package com.github.rapture.aquatic.block.fluid;

import com.github.rapture.aquatic.Aquatic;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;

public class FluidAquaWater extends net.minecraftforge.fluids.Fluid {

    public FluidAquaWater() {
        super("aqua_water", new ResourceLocation(Aquatic.MODID, "blocks/fluids/aqua_water_still"), new ResourceLocation(Aquatic.MODID, "blocks/fluids/aqua_water_flowing"));
        this.setGaseous(false);
        this.setDensity(1000);
        this.setRarity(EnumRarity.RARE);
    }

}
