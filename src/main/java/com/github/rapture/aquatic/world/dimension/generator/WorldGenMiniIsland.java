package com.github.rapture.aquatic.world.dimension.generator;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMiniIsland extends WorldGenerator {

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		float f = (float) (rand.nextInt(25));

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < worldIn.rand.nextInt(34); y++) {
					this.setBlockAndNotifyAdequately(worldIn, position.add(x, y, z), Blocks.CLAY.getDefaultState());
				}
			}
		}
		return true;
	}

	/**
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		float f = (float) (rand.nextInt(25));

		for (int i = 0; f > 0.5F; --i) {
			for (int j = MathHelper.floor(-f); j <= MathHelper.ceil(f); ++j) {
				for (int k = MathHelper.floor(-f); k <= MathHelper.ceil(f); ++k) {
					if ((float) (j * j + k * k) <= (f + 30.0F) * (f + 10.0F)) {
						this.setBlockAndNotifyAdequately(worldIn, position.add(j, i, k), Blocks.DIRT.getDefaultState());
						
					}
				}
			}

			f = (float) ((double) f - ((double) rand.nextInt(10)));
		}

		this.setBlockAndNotifyAdequately(worldIn, position.up((int) f + 1), AquaticBlocks.coral_reef.getDefaultState());

		return true;
	}
	*/
}
