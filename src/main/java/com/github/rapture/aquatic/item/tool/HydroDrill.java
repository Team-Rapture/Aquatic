package com.github.rapture.aquatic.item.tool;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.util.CustomEnergyStorage;
import com.github.rapture.aquatic.util.NameUtil;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class HydroDrill extends ItemTool {

    public static final ToolMaterial hydro_drill = EnumHelper.addToolMaterial("hydro_drill", 3, 1561, 8.0F, 3.0F, 10);
    public static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE, Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER, Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);
    public CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000);

    public HydroDrill() {
        super(1.0F, -2.8F, hydro_drill, EFFECTIVE_ON);
        NameUtil.name(this, "hydro_drill");
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return 30f;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        energyStorage.setEnergyStored(energyStorage.getEnergyStored() - energyStorage.getEnergyStored() - damage);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return this.energyStorage.getEnergyStored() / this.energyStorage.getMaxEnergyStored();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        ICapabilityProvider defaultProvider = super.initCapabilities(stack, nbt);
        return new ICapabilityProvider() {
            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityEnergy.ENERGY || (defaultProvider != null ? defaultProvider.hasCapability(capability, facing) : null);
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
