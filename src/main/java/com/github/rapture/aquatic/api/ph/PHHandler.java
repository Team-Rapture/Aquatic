package com.github.rapture.aquatic.api.ph;

import com.github.rapture.aquatic.api.ph.capability.CapabilityPH;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class PHHandler implements IPHProvider {

    private static final int MAX_PH = 10000;
    private int PH;

    public PHHandler() {
        this(0);
    }

    public PHHandler(int ph) {
        this.PH = ph;
    }

    @Override
    public void addPH(int amount) {
        this.PH = MathHelper.clamp(this.PH + amount, 0, MAX_PH);
    }

    @Override
    public void drainPH(int amount) {
        addPH(-amount);
    }

    @Override
    public int getPHFromLocation(World world, BlockPos pos) {
        Chunk chunk = world.getChunkFromBlockCoords(pos);
        if (chunk.hasCapability(CapabilityPH.PH_CAPABILITY, null)) {
            return world.getChunkFromBlockCoords(pos).getCapability(CapabilityPH.PH_CAPABILITY, null).getPH();
        }
        return 0;

    }

    @Override
    public int getPH() {
        return this.PH;
    }

    @Override
    public void setPH(int amount) {
        this.PH = Math.max(0, amount);
    }

    @Override
    public int getMaxPH() {
        return MAX_PH;
    }
}
