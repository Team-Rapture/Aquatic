package com.github.rapture.aquatic.item.tool;

import java.util.ArrayList;
import java.util.List;

import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.init.AquaticItems;
import com.github.rapture.aquatic.item.ItemBase;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DivingKnife extends ItemBase {
	List<Block> breakable = new ArrayList<>();

	public DivingKnife(String name) {
		super(name);
		breakable.add(AquaticBlocks.coral_reef_blue);
		breakable.add(AquaticBlocks.coral_reef_pink);
		breakable.add(AquaticBlocks.coral_reef_red);

		breakable.add(AquaticBlocks.coral_reef_yellow);
		breakable.add(AquaticBlocks.coral_reef_green);
		breakable.add(AquaticBlocks.PISTIA);
	}

	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		if (!worldIn.isRemote) {
			stack.damageItem(1, entityLiving);
		}
		if (!worldIn.isRemote) {
			Block block = state.getBlock();
			if (breakable.contains(block))
				return worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(AquaticItems.ORGANIC_MATTER)))
								? super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving)
								: true;
		}
		return true;
	}

}
