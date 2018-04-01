package com.github.rapture.aquatic.item.tool;

import com.github.rapture.aquatic.Aquatic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HydroDrill extends ItemPickaxe {
    public static final ToolMaterial hydro_drill = EnumHelper.addToolMaterial("hydro_drill",3, 1561, 8.0F, 3.0F, 10);
    public EnergyStorage energyStorage = new EnergyStorage(10000);
    public HydroDrill() {
        super(hydro_drill);
        this.setRegistryName("hydro_drill");
        this.setUnlocalizedName("hydro_drill");
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
        setHarvestLevel("pickaxe",3);
        setHarvestLevel("spade",3);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ICapabilityProvider() {
            ICapabilityProvider defaultProvider = HydroDrill.super.initCapabilities(stack, nbt);
            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {

                return capability == CapabilityEnergy.ENERGY? true : defaultProvider.hasCapability(capability, facing);
            }

            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityEnergy.ENERGY ? (T) energyStorage : defaultProvider.getCapability(capability, facing);
            }
        };


    }



    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return super.showDurabilityBar(stack);
    }
    public int getCurrentEnergy (ItemStack itemStack){

        return energyStorage.getEnergyStored();
    }
}
