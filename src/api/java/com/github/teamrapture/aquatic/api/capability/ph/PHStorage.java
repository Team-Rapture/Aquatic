package com.github.teamrapture.aquatic.api.capability.ph;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class PHStorage implements IPHProvider {

    private static final int MAX_PH = 10000;
    protected int ph;

    public PHStorage() {
        this(0);
    }

    public PHStorage(int ph) {
        this.ph = ph;
    }

    @Override
    public void addPH(int amount) {
        this.ph = MathHelper.clamp(this.ph + amount, 0, MAX_PH);
    }

    @Override
    public void drainPH(int amount) {
        addPH(-amount);
    }

    @Override
    public int getPHFromLocation(World world, BlockPos pos) {
        Chunk chunk = world.getChunk(pos);
        if (chunk.hasCapability(CapabilityPH.PH_CAPABILITY, null)) {
            return world.getChunk(pos).getCapability(CapabilityPH.PH_CAPABILITY, null).getPH();
        }
        return 0;

    }

    @Override
    public int getPH() {
        return this.ph;
    }

    @Override
    public void setPH(int amount) {
        this.ph = Math.max(0, amount);
    }

    @Override
    public int getMaxPH() {
        return MAX_PH;
    }
}
