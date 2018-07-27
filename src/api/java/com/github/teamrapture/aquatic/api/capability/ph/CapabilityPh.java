package com.github.teamrapture.aquatic.api.capability.ph;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityPh {

    @CapabilityInject(IPhProvider.class)
    public static final Capability<IPhProvider> PH_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPhProvider.class, new Capability.IStorage<IPhProvider>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IPhProvider> capability, IPhProvider instance, EnumFacing side) {
                return new NBTTagInt(instance.getPhStored());
            }

            @Override
            public void readNBT(Capability<IPhProvider> capability, IPhProvider instance, EnumFacing side, NBTBase nbt) {
                if(!(instance instanceof PhStorage)) throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                ((PhStorage) instance).ph = ((NBTPrimitive) nbt).getInt();
            }
        }, PhStorage::new);
    }
}
