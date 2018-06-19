package com.github.teamrapture.aquatic.tileentity;

import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileCreativeGenerator extends TileEntityBase implements IEnergyStorage, ITickable {

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return CapabilityEnergy.ENERGY.cast(this);
        return super.getCapability(capability, facing);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return maxExtract;
    }

    @Override
    public int getEnergyStored() {
        return getMaxEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return 1000000;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @Override
    public void update() {
        if (this.world.isRemote) return;
        for (EnumFacing facing : EnumFacing.values()) {
            TileEntity te = this.world.getTileEntity(this.pos.offset(facing));
            if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                if (energyStorage.canReceive())
                    energyStorage.receiveEnergy(AquaticConfig.machines.creativeBatteryMaxTransfer, false);
            }
        }
    }
}
