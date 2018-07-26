package com.github.teamrapture.aquatic.api.capability.ph;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPHProvider {

    //FIXME add simulate and real return values, (see EnergyStorage)!

    void addPH(int amount);

    void drainPH(int amount);

    int getPHFromLocation(World world, BlockPos pos);

    int getPH();

    void setPH(int amount);

    int getMaxPH();
}
