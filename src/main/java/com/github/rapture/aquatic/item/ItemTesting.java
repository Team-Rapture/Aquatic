package com.github.rapture.aquatic.item;

import java.util.ArrayList;
import java.util.List;

import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.init.AquaticItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTesting extends ItemBase {

	public ItemTesting(String name) {
		super(name);
	
	}

	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
	
		return true;
	}

}
