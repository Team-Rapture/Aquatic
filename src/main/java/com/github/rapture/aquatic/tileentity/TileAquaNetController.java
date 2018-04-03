package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.api.oxygen.capability.CapabilityOxygen;
import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.client.render.hud.IHudSupport;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.entity.misc.EntityWaterBubble;
import com.github.rapture.aquatic.util.CustomEnergyStorage;
import com.github.rapture.aquatic.util.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public class TileAquaNetController extends TileEntityBase implements IHudSupport {

    public CustomEnergyStorage storage = new CustomEnergyStorage(100000);
    public OxygenHandler oxygen = new OxygenHandler(10000);
    public int oxygenGeneration = AquaticConfig.aquaNetGeneration;
    public int energyToGenerate = 20;
    public boolean generatingOxygen = false;
    public int spawnTimer = 0;

    public TileAquaNetController() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        generatingOxygen = nbt.getBoolean("generatingOxygen");
        storage.readFromNBT(nbt);
        oxygen.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("generatingOxygen", generatingOxygen);
        storage.writeToNBT(nbt);
        oxygen.writeToNBT(nbt);
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        super.update();
        this.storage.setEnergyStored(storage.getMaxEnergyStored());
        spawnTimer++;
        if (spawnTimer >= 25) {
            EntityWaterBubble bubble = new EntityWaterBubble(world, new BlockPos(pos.getX() + world.rand.nextInt(8), pos.getY() + world.rand.nextInt(8), pos.getZ() + world.rand.nextInt(8)), pos);
            world.spawnEntity(bubble);
            spawnTimer = 0;
        }

        if (oxygen.canReceiveOxygen(oxygenGeneration)) {
            if (storage.getEnergyStored() >= energyToGenerate) {
                generatingOxygen = true;
                storage.extractEnergy(energyToGenerate, false);
                oxygen.fillOxygen(oxygenGeneration);
            } else {
                generatingOxygen = false;
            }
        } else {
            generatingOxygen = false;
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
        if (capability == CapabilityEnergy.ENERGY) return (T) storage;
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return (T) oxygen;
        return super.getCapability(capability, facing);
    }
}
