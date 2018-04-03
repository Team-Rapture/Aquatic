package com.github.rapture.aquatic.util;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;
import java.util.List;

public class FluidUtil {

    private static List<Fluid> FLUIDS = new ArrayList<>();

    public static Fluid createAndRegister(Fluid fluid) {
        if(FluidRegistry.registerFluid(fluid)) {
            FLUIDS.add(fluid);
            return fluid;
        }
        else return FluidRegistry.getFluid(fluid.getName());
    }

    public static void addBuckets() {
        for (Fluid fluid : FLUIDS) FluidRegistry.addBucketForFluid(fluid);
    }
}
