package com.github.teamrapture.aquatic.api.capability.oxygen;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class OxygenStorage implements IOxygenProvider {

    protected int oxygen = 0;
    private int maxOxygen;

    public OxygenStorage() {
        this(10000);
    }

    public OxygenStorage(int capacity) {
        this.maxOxygen = capacity;
    }

    public OxygenStorage(int capacity, int initialAmount) {
        this(capacity);
        this.setOxygen(initialAmount);
    }

    @Override
    public void setOxygen(int amount) {
        this.oxygen = MathHelper.clamp(amount, 0, this.maxOxygen);
    }

    @Override
    public void setStorageAmount(int amount) {
        this.maxOxygen = amount;
        this.oxygen = Math.min(this.oxygen, amount);
    }

    @Override
    public void fillOxygen(int amount) {
        this.oxygen = MathHelper.clamp(this.oxygen + amount, 0, this.maxOxygen);
    }

    @Override
    public void drainOxygen(int amount) {
        fillOxygen(-amount);
    }

    @Override
    public boolean canReceiveOxygen(int amount) {
        return this.maxOxygen - this.oxygen >= amount;
    }

    @Override
    public boolean canSendOxygen(int amount) {
        return this.oxygen >= amount;
    }

    @Override
    public int getOxygenStored() {
        return this.oxygen;
    }

    @Override
    public int getMaxOxygenStorage() {
        return this.maxOxygen;
    }

    public OxygenStorage readFromNBT(NBTTagCompound nbt) {
        this.setOxygen(nbt.getInteger("oxygen"));
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("oxygen", oxygen);
        return nbt;
    }
}
