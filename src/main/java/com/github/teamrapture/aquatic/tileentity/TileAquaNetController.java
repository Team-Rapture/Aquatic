package com.github.teamrapture.aquatic.tileentity;

import com.github.teamrapture.aquatic.api.IAquaNetworkNode;
import com.github.teamrapture.aquatic.api.capability.oxygen.CapabilityOxygen;
import com.github.teamrapture.aquatic.api.capability.oxygen.IOxygenProvider;
import com.github.teamrapture.aquatic.api.capability.oxygen.OxygenStorage;
import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import com.github.teamrapture.aquatic.client.render.hud.IHudSupport;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileAquaNetController extends TileEntityBase implements IHudSupport, ITickable, IAquaNetworkNode {

    //FIXME move to config!
    public static final int MAX_NETWORK_TRANSFER_AMOUNT = 512;
    public static final int MAX_ARMOR_TRANSFER_AMOUNT = 32;
    private final int REQUIRED_ENERGY_PER_TICK = 20; //TODO config!
    private final int GENERATED_OXYGEN_PER_TICK = AquaticConfig.machines.aquaNetGeneration;
    private static final boolean DYNAMIC_OXYGEN_GENERATION = true;


    private transient int airBlocks = 0;

    private IOxygenProvider oxygen = new OxygenStorage(16000, 4096);
    private IEnergyStorage energy = new EnergyStorage(128000, 16384);

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
        if(!world.isRemote) {
            if(world.getTotalWorldTime() % 40 == 0 && oxygen.canReceiveOxygen() && energy.canExtract()) {
                if(world.getBlockState(getPos().down()).getBlock() == AquaticBlocks.OXYGEN_STONE) airBlocks += 2;
                for(EnumFacing facing : EnumFacing.values()) {
                    if(world.isAirBlock(pos.offset(facing))) airBlocks++;
                }
            }
            if(energy.extractEnergy(REQUIRED_ENERGY_PER_TICK, true) >= REQUIRED_ENERGY_PER_TICK) {
                energy.extractEnergy(REQUIRED_ENERGY_PER_TICK, false);
                oxygen.receiveOxygen(DYNAMIC_OXYGEN_GENERATION ? Math.round(GENERATED_OXYGEN_PER_TICK * MathHelper.clamp(airBlocks / 5.0F, 0.0F, 1.0F)) : GENERATED_OXYGEN_PER_TICK, false);
                this.markDirty();
            }
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

    @Override
    public void setDirty() {
        this.markDirty();
    }
}
