package com.github.rapture.aquatic.block;

import java.util.Random;

import com.github.rapture.aquatic.init.AquaticItems;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAquaCharm extends BlockBase {

	public BlockAquaCharm(String name, Material material) {
		super(name, material);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);

	
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return AquaticItems.aquatic_charm;
	}
}
