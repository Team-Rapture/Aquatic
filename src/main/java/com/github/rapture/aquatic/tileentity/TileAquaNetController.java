package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.api.oxygen.capability.CapabilityOxygen;
import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.client.render.hud.IHudSupport;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.entity.EntityWaterBubble;
import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.util.capability.CustomEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileAquaNetController extends TileEntityBase implements IHudSupport, ITickable {

    public OxygenHandler oxygen = new OxygenHandler(10000);
    public int energyToGenerate = 20;
    public int spawnTimer = 0;
    private CustomEnergyStorage storage = new CustomEnergyStorage(100000);
    private boolean generatingOxygen = false;

    public TileAquaNetController() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.generatingOxygen = nbt.getBoolean("generatingOxygen");
        storage.readFromNBT(nbt);
        oxygen.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("generatingOxygen", isGeneratingOxygen());
        storage.writeToNBT(nbt);
        oxygen.writeToNBT(nbt);
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
        }
        if (world.isRemote) return;
        if (world.getBlockState(pos.down()).getBlock() == AquaticBlocks.OXYGEN_STONE) {
            if (isGeneratingOxygen()) {
                spawnTimer++;
                if (spawnTimer % 20 == 0) {
                    EntityWaterBubble bubble = new EntityWaterBubble(world, new BlockPos(pos.getX() + world.rand.nextInt(8), pos.getY() + world.rand.nextInt(8), pos.getZ() + world.rand.nextInt(8)));
                    EntityWaterBubble bubble2 = new EntityWaterBubble(world, new BlockPos(pos.getX() - world.rand.nextInt(8), pos.getY() + world.rand.nextInt(8), pos.getZ() - world.rand.nextInt(8)));
                    world.spawnEntity(bubble);
                    world.spawnEntity(bubble2);
                }
            }

            if (oxygen.canReceiveOxygen(AquaticConfig.machines.aquaNetGeneration)) {
                if (storage.getEnergyStored() >= energyToGenerate) {
                    storage.extractEnergy(energyToGenerate, false);
                    oxygen.fillOxygen(AquaticConfig.machines.aquaNetGeneration);
                    setGeneratingOxygen(true);
                } else {
                    setGeneratingOxygen(false);
                }
            } else {
                setGeneratingOxygen(false);
            }
        } else {
            setGeneratingOxygen(false);
        }
    }

    @Override
    public EnumFacing getBlockOrientation() {
        return HudRender.getOrientationHoriz(getBlockMetadata());
    }

    @Override
    public String getDisplay() {
        return storage.getEnergyStored() + " FE " + oxygen.getOxygenStored() + " Oxygen";
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
        if (capability == CapabilityEnergy.ENERGY) return true;
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return CapabilityEnergy.ENERGY.cast(storage);
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return CapabilityOxygen.OXYGEN_CAPABILITY.cast(oxygen);
        return super.getCapability(capability, facing);
    }

    public boolean isGeneratingOxygen() {
        return generatingOxygen;
    }

    public void setGeneratingOxygen(boolean generatingOxygen) {
        this.generatingOxygen = generatingOxygen;
        markDirty();
    }
}
