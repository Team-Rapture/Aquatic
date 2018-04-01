package com.github.rapture.aquatic.block.fluid;

import com.github.rapture.aquatic.Aquatic;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidAquaWater extends Fluid {

    public FluidAquaWater() {
        super("aqua_water", new ResourceLocation(Aquatic.MODID, "blocks/fluids/aqua_water_still"), new ResourceLocation(Aquatic.MODID, "blocks/fluids/aqua_water_flowing"));
        this.setGaseous(false);
        this.setRarity(EnumRarity.RARE);
    }

    public void register() {
        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }
}
