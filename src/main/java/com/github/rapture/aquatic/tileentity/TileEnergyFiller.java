package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.util.CustomEnergyStorage;
import com.github.rapture.aquatic.util.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEnergyFiller extends TileEntityInventory {

    public CustomEnergyStorage storage = new CustomEnergyStorage(100000);

    public TileEnergyFiller() {
        super(2);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        storage.writeToNBT(nbt);
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        super.update();
        if (!inventory.getStackInSlot(0).isEmpty()) {
            if (inventory.getStackInSlot(0).hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
                IEnergyStorage energyProvider = inventory.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP);
                if (energyProvider.getEnergyStored() < energyProvider.getMaxEnergyStored()) {
                    if (energyProvider.getMaxEnergyStored() - energyProvider.getEnergyStored() >= 20 && storage.getEnergyStored() >= 20) {
                        energyProvider.receiveEnergy(20, false);
                        storage.extractEnergy(20, false);
                    }
                } else {
                    inventory.setStackInSlot(1, inventory.getStackInSlot(0));
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) return (T) storage;
        return super.getCapability(capability, facing);
    }
}
