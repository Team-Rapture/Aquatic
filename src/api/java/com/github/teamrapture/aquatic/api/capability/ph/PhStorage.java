package com.github.teamrapture.aquatic.api.capability.ph;

public class PhStorage implements IPhProvider {

    protected int ph;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public PhStorage() {
        this(1000);
    }

    public PhStorage(int capacity)
    {
        this(capacity, capacity, capacity, 0);
    }

    public PhStorage(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public PhStorage(int capacity, int maxReceive, int maxExtract)
    {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public PhStorage(int capacity, int maxReceive, int maxExtract, int energy)
    {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.ph = Math.max(0 , Math.min(capacity, energy));
    }

    @Override
    public int receivePh(int maxReceive, boolean simulate)
    {
        if (!canReceivePh())
            return 0;

        int energyReceived = Math.min(capacity - ph, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            ph += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractPh(int maxExtract, boolean simulate)
    {
        if (!canExtractPh())
            return 0;

        int energyExtracted = Math.min(ph, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            ph -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public boolean canReceivePh() {
        return maxReceive > 0;
    }

    @Override
    public boolean canExtractPh() {
        return maxExtract > 0;
    }

    @Override
    public int getPhStored() {
        return ph;
    }

    @Override
    public int getMaxPhStorage() {
        return capacity;
    }
}
