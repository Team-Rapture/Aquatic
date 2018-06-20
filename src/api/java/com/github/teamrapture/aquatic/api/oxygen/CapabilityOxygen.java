package com.github.teamrapture.aquatic.api.oxygen;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityOxygen implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IOxygenProvider.class)
    public static final Capability<IOxygenProvider> OXYGEN_CAPABILITY = null;

    private IOxygenProvider instance = OXYGEN_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == OXYGEN_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == OXYGEN_CAPABILITY ? OXYGEN_CAPABILITY.cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return OXYGEN_CAPABILITY.getStorage().writeNBT(OXYGEN_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        OXYGEN_CAPABILITY.getStorage().readNBT(OXYGEN_CAPABILITY, this.instance, null, nbt);
    }
}
