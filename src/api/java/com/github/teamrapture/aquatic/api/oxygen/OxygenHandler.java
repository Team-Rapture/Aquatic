package com.github.teamrapture.aquatic.api.oxygen;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;

public class OxygenHandler implements IOxygenProvider {

    private int oxygen = 0;
    private int maxOxygen;

    public OxygenHandler() {
        this(10000);
    }

    public OxygenHandler(int capacity) {
        this.maxOxygen = capacity;
    }

    public OxygenHandler(int capacity, int initialAmount) {
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

    public OxygenHandler readFromNBT(NBTTagCompound nbt) {
        this.setOxygen(nbt.getInteger("oxygen"));
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("oxygen", oxygen);
        return nbt;
    }
}
