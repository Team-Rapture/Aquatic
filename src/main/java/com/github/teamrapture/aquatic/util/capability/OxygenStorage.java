package com.github.teamrapture.aquatic.util.capability;

import com.github.rapture.aquatic.api.oxygen.IOxygenProvider;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class OxygenStorage implements Capability.IStorage<IOxygenProvider> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IOxygenProvider> capability, IOxygenProvider instance, EnumFacing side) {
        return new NBTTagInt(instance.getOxygenStored());
    }

    @Override
    public void readNBT(Capability<IOxygenProvider> capability, IOxygenProvider instance, EnumFacing side, NBTBase nbt) {
        instance.setOxygen(((NBTPrimitive) nbt).getInt());
    }
}
