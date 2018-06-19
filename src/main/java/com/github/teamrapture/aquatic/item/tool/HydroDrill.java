package com.github.teamrapture.aquatic.item.tool;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.util.NameUtil;
import com.github.rapture.aquatic.util.capability.CustomEnergyStorage;

import com.github.teamrapture.aquatic.util.NameUtil;
import com.github.teamrapture.aquatic.util.capability.CustomEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HydroDrill extends ItemTool {

	public static final ToolMaterial hydro_drill = EnumHelper.addToolMaterial("hydro_drill", 3, 1561, 8.0F, 3.0F, 10);
	private CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000);

	public HydroDrill() {
		super(1.0F, -2.8F, hydro_drill, Collections.emptySet());
		NameUtil.name(this, "hydro_drill");
		this.setCreativeTab(Aquatic.CREATIVE_TAB);
		this.setHarvestLevel("pickaxe", 3);
		this.setHarvestLevel("axe", 3);
		this.setHarvestLevel("shovel", 3);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float storage = 30;
		if (this.energyStorage.getEnergyStored() < 300) {
			return 0f;
		}
		if (this.energyStorage.getEnergyStored() >= 0) {
			return 30f;
		}
		return storage;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && (double) state.getBlockHardness(worldIn, pos) != 0.0D) {
			energyStorage.extractEnergy(300, false);

		}

		return true;
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
		return new ICapabilityProvider() {
			@Override
			public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
				return capability == CapabilityEnergy.ENERGY;
			}

			@Nullable
			@Override
			public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
				return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(energyStorage) : null;
			}
		};

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		int damage = energyStorage.getEnergyStored();
		int maxDamage = energyStorage.getMaxEnergyStored();
		tooltip.add(damage + " FE" + " / " + maxDamage + " FE");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
