package com.github.rapture.aquatic.api.ph;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPHProvider {

    void setPH(int amount);
    void addPH(int amount);
    void drainPH(int amount);

    int getPHFromLocation(World world, BlockPos pos);
    int getPH();
    int getMaxPH();
}
