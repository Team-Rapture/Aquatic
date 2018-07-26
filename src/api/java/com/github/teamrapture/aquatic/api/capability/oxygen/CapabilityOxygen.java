package com.github.teamrapture.aquatic.api.capability.oxygen;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityOxygen {

    @CapabilityInject(IOxygenProvider.class)
    public static final Capability<IOxygenProvider> OXYGEN = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IOxygenProvider.class, new Capability.IStorage<IOxygenProvider>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IOxygenProvider> capability, IOxygenProvider instance, EnumFacing side) {
                return new NBTTagInt(instance.getOxygenStored());
            }

            @Override
            public void readNBT(Capability<IOxygenProvider> capability, IOxygenProvider instance, EnumFacing side, NBTBase nbt) {
                if(!(instance instanceof OxygenStorage)) throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                ((OxygenStorage) instance).oxygen = ((NBTPrimitive) nbt).getInt();
            }
        }, OxygenStorage::new);
    }
}
