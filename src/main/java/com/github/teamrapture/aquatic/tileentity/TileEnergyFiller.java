package com.github.teamrapture.aquatic.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileEnergyFiller extends TileEntityInventory implements ITickable {

    public IEnergyStorage storage = new EnergyStorage(128000, 16364, 0);

    public TileEnergyFiller() {
        super(2);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        CapabilityEnergy.ENERGY.readNBT(storage, null, nbt.getTag("energy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(storage, null));
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3); //TODO what's this for?
            if (!inventory.getStackInSlot(0).isEmpty()) {
                IEnergyStorage energyProvider = inventory.getStackInSlot(0).getCapability(CapabilityEnergy.ENERGY, null);
                if (energyProvider != null) {
                    if (energyProvider.getEnergyStored() < energyProvider.getMaxEnergyStored()) {
                        if (energyProvider.getMaxEnergyStored() - energyProvider.getEnergyStored() >= 20 && storage.getEnergyStored() >= 20) {
                            energyProvider.receiveEnergy(20, false);
                            storage.extractEnergy(20, false);
                            this.markDirty();
                        }
                    } else {
                        inventory.setStackInSlot(1, inventory.getStackInSlot(0));
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return (T) storage;
        return super.getCapability(capability, facing);
    }
}
