package com.github.rapture.aquatic.api.ph;

import com.github.rapture.aquatic.api.ph.capability.CapabilityPH;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PHHandler implements IPHProvider {

    private int PH = 0;
    private int maxPH = 10000;

    public PHHandler(int ph) {
        this.PH = ph;
    }

    @Override
    public void setPH(int amount) {
        this.PH = amount;
        if (this.PH < 0) {
            this.PH = 0;
        }
    }

    @Override
    public void addPH(int amount) {
        this.PH += amount;
        if (this.PH > this.maxPH) {
            this.PH = this.maxPH;
        }
    }

    @Override
    public void drainPH(int amount) {
        this.PH -= amount;
        if (this.PH < 0) {
            this.PH = 0;
        }
    }

    @Override
    public int getPHFromLocation(World world, BlockPos pos) {
        IPHProvider capPH = world.getChunkFromBlockCoords(pos).getCapability(CapabilityPH.PH_CAPABILITY, EnumFacing.UP);
        return capPH.getPH();
    }

    @Override
    public int getPH() {
        return this.PH;
    }

    @Override
    public int getMaxPH() {
        return this.maxPH;
    }
}
