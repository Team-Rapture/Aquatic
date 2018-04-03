package com.github.rapture.aquatic.world.dimension.generator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenPlants extends WorldGenerator {

	private IBlockState state;

	public WorldGenPlants(Block blockIn) {
	    this.setBlock(blockIn);
	}

	public void setBlock(Block blockIn) {
		this.state = blockIn.getDefaultState();
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		for (int i = 0; i < rand.nextInt(64); ++i) {
		    BlockPos blockpos = position.add(4 - rand.nextInt(8), rand.nextInt(6) - rand.nextInt(6),
					4 - rand.nextInt(8));
		    int xMod = position.getX() % 16;
		    int zMod = position.getZ() % 16;
		    if(xMod == 0 || xMod == 15 || zMod == 0 || zMod == 15) continue;

			IBlockState state = worldIn.getBlockState(blockpos);
			if(state.getMaterial() == Material.WATER && worldIn.isSideSolid(blockpos.down(), EnumFacing.UP)) {
                setBlockAndNotifyAdequately(worldIn, blockpos, this.state);
			}
		}

		return true;
	}
   
	
}
