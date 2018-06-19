package com.github.teamrapture.aquatic.api.ph;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPHProvider {

    void addPH(int amount);

    void drainPH(int amount);

    int getPHFromLocation(World world, BlockPos pos);

    int getPH();

    void setPH(int amount);

    int getMaxPH();
}
