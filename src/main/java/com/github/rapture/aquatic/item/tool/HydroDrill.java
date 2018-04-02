package com.github.rapture.aquatic.item.tool;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.util.CustomEnergyStorage;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HydroDrill extends ItemPickaxe {

    public static final ToolMaterial hydro_drill = EnumHelper.addToolMaterial("hydro_drill", 3, 1561, 8.0F, 3.0F, 10);
    public CustomEnergyStorage energyStorage = new CustomEnergyStorage(10000);

    public HydroDrill() {
        super(hydro_drill);
        this.setRegistryName("hydro_drill");
        this.setUnlocalizedName("hydro_drill");
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
        this.setHarvestLevel("pickaxe", 3);
        this.setHarvestLevel("shovel", 3);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return super.showDurabilityBar(stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        ICapabilityProvider defaultProvider = super.initCapabilities(stack, nbt);
        return new ICapabilityProvider() {
            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityEnergy.ENERGY || defaultProvider.hasCapability(capability, facing);
            }

            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityEnergy.ENERGY ? (T) energyStorage :
                        (defaultProvider == null ? null : defaultProvider.getCapability(capability, facing));
            }
        };
    }
}
