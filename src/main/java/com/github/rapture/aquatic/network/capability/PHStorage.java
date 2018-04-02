package com.github.rapture.aquatic.network.capability;

import com.github.rapture.aquatic.api.ph.IPHProvider;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PHStorage implements Capability.IStorage<IPHProvider> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IPHProvider> capability, IPHProvider instance, EnumFacing side) {
        return new NBTTagInt(instance.getPH());
    }

    @Override
    public void readNBT(Capability<IPHProvider> capability, IPHProvider instance, EnumFacing side, NBTBase nbt) {
        instance.setPH(((NBTPrimitive) nbt).getInt());
    }
}
