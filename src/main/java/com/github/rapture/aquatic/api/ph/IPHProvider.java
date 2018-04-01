package com.github.rapture.aquatic.api.ph;

import net.minecraft.util.math.BlockPos;

public interface IPHProvider {

    void setPH(int amount);
    void addPH(int amount);
    void extractPH(int amount);

    int getPH(BlockPos pos);
}
