package com.github.rapture.aquatic.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {

    public CustomEnergyStorage(int capacity) {
        super(capacity);
    }

    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setEnergyStored(int amount) {
        this.energy = MathHelper.clamp(amount, 0, this.capacity);
    }

    public CustomEnergyStorage readFromNBT(NBTTagCompound nbt) {
        this.setEnergyStored(nbt.getInteger("energy"));
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("energy", energy);
        return nbt;
    }

    public boolean canReceiveEnergy(int amount) {
        return (capacity - energy) >= amount;
    }
}