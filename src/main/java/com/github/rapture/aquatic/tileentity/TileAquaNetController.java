package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.client.render.hud.IHudSupport;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.util.CustomEnergyStorage;
import com.github.rapture.aquatic.util.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileAquaNetController extends TileEntityBase implements IHudSupport {

    public CustomEnergyStorage storage = new CustomEnergyStorage(50000);
    public int generation = AquaticConfig.aquaNetGeneration;

    public TileAquaNetController() {
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
        if (world.canSeeSky(pos) && world.isDaytime()) {
            if(pos.getY() > world.getSeaLevel()) {
                if(storage.canReceiveEnergy(generation)) {
                    storage.receiveEnergy(generation, false);
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

    @Override
    public EnumFacing getBlockOrientation() {
        return HudRender.getOrientationHoriz(getBlockMetadata());
    }

    @Override
    public String getDisplay() {
        return storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " FE";
    }

    @Override
    public boolean isBlockAboveAir() {
        return getWorld().isAirBlock(pos.up());
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }
}
