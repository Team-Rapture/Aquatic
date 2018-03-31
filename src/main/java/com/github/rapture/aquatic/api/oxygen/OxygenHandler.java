package com.github.rapture.aquatic.api.oxygen;

import net.minecraft.nbt.NBTTagCompound;

public class OxygenHandler implements IOxygenProvider {

    private int oxygen = 0;
    private int maxOxygen;

    public OxygenHandler(int capacity) {
        this.maxOxygen = capacity;
    }

    public OxygenHandler(int capacity, int initialAmount) {
        this.maxOxygen = capacity;
        this.maxOxygen = initialAmount;
    }

    @Override
    public void setOxygen(int amount) {
        this.oxygen = amount;
        if(this.oxygen > this.maxOxygen) {
            this.oxygen = this.maxOxygen;
        }else if(this.oxygen < 0) {
            this.oxygen = 0;
        }
    }

    @Override
    public void setStorageAmount(int amount) {
        this.maxOxygen = amount;
    }

    @Override
    public void fillOxygen(int amount) {
        oxygen += amount;
        if(this.oxygen > this.maxOxygen)
            this.oxygen = this.maxOxygen;
    }

    @Override
    public void drainOxygen(int amount) {
        this.oxygen -= amount;
        if(this.oxygen < 0)
            this.oxygen = 0;
    }

    @Override
    public boolean canReceiveOxygen(int amount) {
        if(this.maxOxygen - this.oxygen >= amount) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canSendOxygen(int amount) {
        if(this.oxygen >= amount) {
            return true;
        }
        return false;
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
        this.oxygen = nbt.getInteger("Oxygen");
        if (this.oxygen > this.maxOxygen) {
            this.oxygen = this.maxOxygen;
        }

        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (this.oxygen < 0) {
            this.oxygen = 0;
        }
        nbt.setInteger("Oxygen", oxygen);

        return nbt;
    }
}
