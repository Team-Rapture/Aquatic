package com.github.rapture.aquatic.world.dimension.generator;

import java.util.Random;

import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.init.AquaticBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenReefs implements IWorldGenerator {
	public WorldGenerator genCoralReef;

	public WorldGenReefs() {
		genCoralReef = new WorldGenOreReef();

	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == AquaticConfig.dimensionID) {
			this.runGenerator(world, rand, chunkX, chunkZ, 0, world.getSeaLevel() - 4);
		}
	}

	private void runGenerator(World world, Random rand, int chunk_X, int chunk_Z, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		if (rand.nextInt(10) == 0) {
			int x = chunk_X * 16 + rand.nextInt(16);
			int z = chunk_Z * 16 + rand.nextInt(16);
			int y = MathHelper.clamp(getTopSolidOrLiquidBlock(world, new BlockPos(x, 64, z)).getY(), minHeight,
					maxHeight);
			BlockPos position = new BlockPos(x + 8, y, z + 8);
			
			genCoralReef.generate(world, rand, position);

		}
	}

	public class WorldGenOreReef extends WorldGenerator {
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			int r = MathHelper.clamp(rand.nextInt(7), 4, 7);
			for (int x = -r; x <= r; x++)
				for (int z = -r; z <= r; z++) {
					BlockPos pos = getTopSolidOrLiquidBlock(worldIn,
							new BlockPos(position.getX() + x, position.getY(), position.getZ() + z));
					if ((x * x) + (z * z) > r * r || rand.nextInt(r) < r / 2 || pos.getY() > worldIn.getSeaLevel() - 5)
						continue;

					Block block = worldIn.getBlockState(pos).getBlock();
					if (block == AquaticBlocks.AQUATIC_STONE || block == AquaticBlocks.AQUATIC_STONE_CRACKED
							|| block == Blocks.SAND || block == AquaticBlocks.REEF_STONE.getDefaultState()) {
						if (worldIn.setBlockState(pos, AquaticBlocks.REEF_STONE.getDefaultState())) {
						}

						IBlockState variant = AquaticBlocks.CORAL_REEF_BLUE.getDefaultState();
						IBlockState variant1 = AquaticBlocks.CORAL_REEF_GREEN.getDefaultState();
						IBlockState variant2 = AquaticBlocks.CORAL_REEF_RED.getDefaultState();
						IBlockState variant3 = AquaticBlocks.CORAL_REEF_YELLOW.getDefaultState();
						IBlockState variant4 = AquaticBlocks.CORAL_REEF_PINK.getDefaultState();
						IBlockState variant5 = AquaticBlocks.HYDRILLA.getDefaultState();
						IBlockState variant6 = AquaticBlocks.OXYGEN_STONE.getDefaultState();

						for (int i = 1; i <= rand.nextInt(4); i++) {
							Random random = new Random();
							switch (random.nextInt(11)) {
							case 0:

								worldIn.setBlockState(pos.up(), variant1);
								break;
							case 1:

								worldIn.setBlockState(pos.up(), variant);
								break;
							case 2:

								worldIn.setBlockState(pos.up(i), variant2);
								break;
							case 3:

								worldIn.setBlockState(pos.up(), variant3);
								break;
							case 4:

								worldIn.setBlockState(pos.up(), variant4);
								break;
							case 5:

								worldIn.setBlockState(pos.up(), variant5);
								break;
							case 6:

								worldIn.setBlockState(pos.up(), variant6);
								break;

							}

						}
					}
				}
			return true;

		}
	}

	public static BlockPos getTopSolidOrLiquidBlock(World world, BlockPos pos) {
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		BlockPos blockpos;
		BlockPos blockpos1;

		for (blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos
				.getY() >= 0; blockpos = blockpos1) {
			blockpos1 = blockpos.down();
			IBlockState state = chunk.getBlockState(blockpos1);
			if (state.getMaterial().isSolid() && !state.getMaterial().isLiquid()
					&& !state.getBlock().isLeaves(state, world, blockpos1)
					&& !state.getBlock().isFoliage(world, blockpos1)) {
				break;
			}
		}
		return blockpos.down();
	}
}
