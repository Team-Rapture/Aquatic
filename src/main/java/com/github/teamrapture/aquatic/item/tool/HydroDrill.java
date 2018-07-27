package com.github.teamrapture.aquatic.item.tool;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.util.NameUtil;
import com.github.teamrapture.aquatic.util.capability.CapabilityProviderSerializable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

public class HydroDrill extends ItemTool {

	public static final ToolMaterial hydro_drill = EnumHelper.addToolMaterial("hydro_drill", 3, 1561, 8.0F, 3.0F, 10);

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
		IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
		return storage != null ? storage.extractEnergy(300, true) / 300.0F : 30F;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (!worldIn.isRemote && (double) state.getBlockHardness(worldIn, pos) != 0.0D) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null) storage.extractEnergy(300, false);
		}
		return true;
	}

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

	@Override
	public boolean canHarvestBlock(IBlockState state) {
		return true;
	}

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
	    double max = getMaxEnergyStored(stack);
	    if(max == 0.0D) return super.getDurabilityForDisplay(stack); //avoid math errors

        double difference = max - getCurrentEnergyStored(stack);
        return difference / max;
    }

    @Override
    public boolean getShareTag() { //needed to make sure the capabilities stay in sync with the client
        return true;
    }

    private int getCurrentEnergyStored(ItemStack stack) {
        IEnergyStorage battery = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if(battery != null) return battery.getEnergyStored();
        return 0;
    }

    private int getMaxEnergyStored(ItemStack stack) {
        IEnergyStorage battery = stack.getCapability(CapabilityEnergy.ENERGY, null);
        if(battery != null) return battery.getMaxEnergyStored();
        return 0;
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        stack.setTagInfo("sync_aquatic_energy", new NBTTagInt(getCurrentEnergyStored(stack))); //trick MC into sending the stack capabilities to the client!
        return super.getNBTShareTag(stack);
    }


    @Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new CapabilityProviderSerializable<>(CapabilityEnergy.ENERGY, null, new EnergyStorage(128000, 16384));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NumberFormat numberFormatter = NumberFormat.getInstance();
		tooltip.add(numberFormatter.format(getCurrentEnergyStored(stack)) + " / " + numberFormatter.format(getMaxEnergyStored(stack)) + " FE"); //TODO localize!
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
