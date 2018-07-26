package com.github.teamrapture.aquatic.api.capability.ph;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityPH {

    @CapabilityInject(IPHProvider.class)
    public static final Capability<IPHProvider> PH_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPHProvider.class, new Capability.IStorage<IPHProvider>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IPHProvider> capability, IPHProvider instance, EnumFacing side) {
                return new NBTTagInt(instance.getPH());
            }

            @Override
            public void readNBT(Capability<IPHProvider> capability, IPHProvider instance, EnumFacing side, NBTBase nbt) {
                if(!(instance instanceof PHStorage)) throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                ((PHStorage) instance).ph = ((NBTPrimitive) nbt).getInt();
            }
        }, PHStorage::new);
    }
}
