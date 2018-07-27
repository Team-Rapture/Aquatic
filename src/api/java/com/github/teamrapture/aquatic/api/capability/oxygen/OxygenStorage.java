package com.github.teamrapture.aquatic.api.capability.oxygen;

public class OxygenStorage implements IOxygenProvider {

    protected int oxygen;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public OxygenStorage() {
        this(16000);
    }

    public OxygenStorage(int capacity)
    {
        this(capacity, capacity, capacity, 0);
    }

    public OxygenStorage(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public OxygenStorage(int capacity, int maxReceive, int maxExtract)
    {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public OxygenStorage(int capacity, int maxReceive, int maxExtract, int energy)
    {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.oxygen = Math.max(0 , Math.min(capacity, energy));
    }

    @Override
    public int receiveOxygen(int maxReceive, boolean simulate)
    {
        if (!canReceiveOxygen())
            return 0;

        int energyReceived = Math.min(capacity - oxygen, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            oxygen += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractOxygen(int maxExtract, boolean simulate)
    {
        if (!canExtractOxygen())
            return 0;

        int energyExtracted = Math.min(oxygen, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            oxygen -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public boolean canReceiveOxygen() {
        return maxReceive > 0;
    }

    @Override
    public boolean canExtractOxygen() {
        return maxExtract > 0;
    }

    @Override
    public int getOxygenStored() {
        return oxygen;
    }

    @Override
    public int getMaxOxygenStorage() {
        return capacity;
    }
}
