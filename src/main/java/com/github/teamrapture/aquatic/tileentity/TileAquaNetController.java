package com.github.teamrapture.aquatic.tileentity;

import com.github.teamrapture.aquatic.api.capability.oxygen.CapabilityOxygen;
import com.github.teamrapture.aquatic.api.capability.oxygen.IOxygenProvider;
import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import com.github.teamrapture.aquatic.client.render.hud.IHudSupport;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.api.capability.oxygen.OxygenStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileAquaNetController extends TileEntityBase implements IHudSupport, ITickable {

    private static final int REQUIRED_ENERGY = 20;
    private IOxygenProvider oxygen = new OxygenStorage(10000);
    private IEnergyStorage energy = new EnergyStorage(100000, 1024, 0);

    public TileAquaNetController() {

    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        CapabilityEnergy.ENERGY.readNBT(this.energy, null, nbt.getTag("energy"));
        CapabilityOxygen.OXYGEN.readNBT(this.oxygen, null, nbt.getTag("oxygen"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(this.energy, null));
        nbt.setTag("oxygen", CapabilityOxygen.OXYGEN.writeNBT(this.oxygen, null));
        return nbt;
    }

    @Override
    public void update() {
        if(!world.isRemote && world.getBlockState(getPos().down()).getBlock() == AquaticBlocks.OXYGEN_STONE && oxygen.canReceiveOxygen(AquaticConfig.machines.aquaNetGeneration) && energy.extractEnergy(REQUIRED_ENERGY, true) >= REQUIRED_ENERGY) {
            energy.extractEnergy(REQUIRED_ENERGY, false);
            oxygen.fillOxygen(AquaticConfig.machines.aquaNetGeneration);
            this.markDirty();
        }
    }

    @Override
    public EnumFacing getBlockOrientation() {
        return HudRender.getOrientationHoriz(getBlockMetadata());
    }

    @Override
    public String getDisplay() { //TODO debug; remove
        return energy.getEnergyStored() + " FE " + oxygen.getOxygenStored() + " Oxygen";
    }

    @Override
    public boolean isBlockAboveAir() {
        return getWorld().isAirBlock(pos.up());
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || capability == CapabilityOxygen.OXYGEN || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return CapabilityEnergy.ENERGY.cast(energy);
        if (capability == CapabilityOxygen.OXYGEN) return CapabilityOxygen.OXYGEN.cast(oxygen);
        return super.getCapability(capability, facing);
    }
}
