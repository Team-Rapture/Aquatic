package com.github.rapture.aquatic.api.ph.capability;

import com.github.rapture.aquatic.api.ph.IPHProvider;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityPH implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IPHProvider.class)
    public static final Capability<IPHProvider> PH_CAPABILITY = null;

    private IPHProvider instance = PH_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PH_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == PH_CAPABILITY ? PH_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return PH_CAPABILITY.getStorage().writeNBT(PH_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        PH_CAPABILITY.getStorage().readNBT(PH_CAPABILITY, this.instance, null, nbt);
    }
}
