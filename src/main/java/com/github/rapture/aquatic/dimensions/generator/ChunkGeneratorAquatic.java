package com.github.rapture.aquatic.dimensions.generator;

import java.util.List;
import java.util.Random;

import com.github.rapture.aquatic.init.AquaticBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class ChunkGeneratorAquatic implements IChunkGenerator {

	private World world;

	private final NoiseGeneratorOctaves noiseGen1;
	private final NoiseGeneratorOctaves noiseGen2;
	private final NoiseGeneratorOctaves noiseGen3;
	private final NoiseGeneratorOctaves noiseGen4;
	private final NoiseGeneratorOctaves noiseGen5;
	private final NoiseGeneratorOctaves noiseGen6;
	private double[] noiseArray;
	private double[] stoneNoise;
	private double[] noiseData1;
	private double[] noiseData2;
	private double[] noiseData3;
	private double[] noiseData4;
	private double[] noiseData5;

	private NoiseGeneratorPerlin perlinAdditional1;
	private NoiseGeneratorPerlin perlinAdditional2;

	private IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();

	private IBlockState WATER = AquaticBlocks.AQUA_WATER_BLOCK.getDefaultState();
	public NoiseGeneratorOctaves scaleNoise;
	public NoiseGeneratorOctaves depthNoise;
	private AquaticGenerator gensPerlin;
	double[] data4;
	private Random random;

	public ChunkGeneratorAquatic(World world) {
		this.world = world;
		this.random = new Random();

		random.setSeed(random.nextLong());
		gensPerlin = new AquaticGenerator(random, 16);
		this.scaleNoise = new NoiseGeneratorOctaves(this.random, 10);
		this.depthNoise = new NoiseGeneratorOctaves(this.random, 16);

		noiseGen1 = new NoiseGeneratorOctaves(random, 16);
		noiseGen2 = new NoiseGeneratorOctaves(random, 16);
		noiseGen3 = new NoiseGeneratorOctaves(random, 8);
		noiseGen4 = new NoiseGeneratorOctaves(random, 4);
		noiseGen5 = new NoiseGeneratorOctaves(random, 10);
		noiseGen6 = new NoiseGeneratorOctaves(random, 16);
		perlinAdditional1 = new NoiseGeneratorPerlin(random, 4);
		perlinAdditional2 = new NoiseGeneratorPerlin(random, 4);
		stoneNoise = new double[256];

		this.world.setSeaLevel(90);
	}

	public void generateTerrain(int x, int z, ChunkPrimer chunkPrimer) {
		int i = 4;
		int k = i + 1;
		int l = 17;
		int i1 = i + 1;
		noiseArray = initializeNoiseField(noiseArray, x * i, 0, z * i, k, l, i1);

		for (int j1 = 0; j1 < i; ++j1)
			for (int k1 = 0; k1 < i; ++k1)
				for (int l1 = 0; l1 < 16; ++l1) {
					double d0 = 0.125D;
					double d1 = noiseArray[((j1 + 0) * i1 + k1 + 0) * l + l1 + 0];
					double d2 = noiseArray[((j1 + 0) * i1 + k1 + 1) * l + l1 + 0];
					double d3 = noiseArray[((j1 + 1) * i1 + k1 + 0) * l + l1 + 0];
					double d4 = noiseArray[((j1 + 1) * i1 + k1 + 1) * l + l1 + 0];
					double d5 = (noiseArray[((j1 + 0) * i1 + k1 + 0) * l + l1 + 1] - d1) * d0;
					double d6 = (noiseArray[((j1 + 0) * i1 + k1 + 1) * l + l1 + 1] - d2) * d0;
					double d7 = (noiseArray[((j1 + 1) * i1 + k1 + 0) * l + l1 + 1] - d3) * d0;
					double d8 = (noiseArray[((j1 + 1) * i1 + k1 + 1) * l + l1 + 1] - d4) * d0;

					for (int i2 = 0; i2 < 8; ++i2) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int j2 = 0; j2 < 4; ++j2) {
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;

							for (int k2 = 0; k2 < 4; ++k2) {
								IBlockState iblockstate = null;

								if (d15 > 0.0D)
									iblockstate = AquaticBlocks.AQUATIC_STONE.getDefaultState();

								int l2 = j2 + j1 * 4;
								int i3 = i2 + l1 * 8;
								int j3 = k2 + k1 * 4;
								chunkPrimer.setBlockState(l2, i3, j3, iblockstate);
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
	}

	private double[] initializeNoiseField(double[] noise, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
		if (noise == null)
			noise = new double[sizeX * sizeY * sizeZ];

		double d = 684.412D;
		double d1 = 2053.236D;
		noiseData4 = noiseGen5.generateNoiseOctaves(noiseData4, x, y, z, sizeX, 1, sizeZ, 1D, 0D, 1D);
		noiseData5 = noiseGen6.generateNoiseOctaves(noiseData5, x, y, z, sizeX, 1, sizeZ, 100D, 0D, 100D);
		noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, x, y, z, sizeX, sizeY, sizeZ, d * 0.0125D, d1 / 60D, d * 0.0125D);
		noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, x, y, z, sizeX, sizeY, sizeZ, d, d1, d);
		noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, x, y, z, sizeX, sizeY, sizeZ, d, d1, d);
		int index = 0;
		int j = 0;
		double ad[] = new double[sizeY];
		double oneOver512 = 1D / 512D;
		double groundNoiseMp = 1D / 2048D;

		for (int k = 0; k < sizeY; ++k) {
			ad[k] = Math.cos(k * Math.PI * 6D / sizeY) * 2D;
			double d2 = k;

			if (k > sizeY / 2)
				d2 = sizeY - 1 - k;

			if (d2 < 4D) {
				d2 = 4D - d2;
				ad[k] -= d2 * d2 * d2 * 10D;
			}
		}

		for (int xx = 0; xx < sizeX; ++xx)
			for (int zz = 0; zz < sizeZ; ++zz) {
				double d3 = (noiseData4[j] + 256D) * oneOver512;

				if (d3 > 1.0D)
					d3 = 1.0D;

				double d4 = 0.0D;
				double d5 = noiseData5[j] * 0.000125D;

				if (d5 < 0.0D)
					d5 = -d5;

				d5 = d5 * 3D - 3D;

				if (d5 < 0.0D) {
					d5 /= 2D;

					if (d5 < -1D)
						d5 = -1D;

					d5 /= 1.4D;
					d5 *= 0.5D;
					d3 = 0.0D;
				} else {
					if (d5 > 1.0D)
						d5 = 1.0D;

					d5 /= 6D;
				}

				d3 += 0.5D;
				d5 = d5 * sizeY * 0.0625D;
				j++;

				for (int yy = 0; yy < sizeY; ++yy) {
					double d6 = 0.0D;
					double d7 = ad[yy];
					double d8 = noiseData2[index] * groundNoiseMp;
					double d9 = noiseData3[index] * groundNoiseMp;
					double d10 = (noiseData1[index] * 0.1D + 1.0D) * 0.5D;

					if (d10 < 0.0D)
						d6 = d8;
					else if (d10 > 1.0D)
						d6 = d9;
					else
						d6 = d8 + (d9 - d8) * d10;

					d6 -= d7;

					if (yy > sizeY - 4) {
						double d11 = (yy - (sizeY - 4)) / 3F;
						d6 = d6 * (1.0D - d11) + -10D * d11;
					}

					if (yy < d4) {
						double d12 = (d4 - yy) * 0.25D;
						if (d12 < 0.0D)
							d12 = 0.0D;
						if (d12 > 1.0D)
							d12 = 1.0D;

						d6 = d6 * (1.0D - d12) + -10D * d12;
					}

					noise[index] = d6;
					++index;
				}
			}

		return noise;
	}

	@Override
	public Chunk generateChunk(int cx, int cz) {
		ChunkPrimer p = new ChunkPrimer();

		Random r = new Random();
		r.setSeed(world.getSeed());
		r.setSeed(cx * r.nextLong() + cz * r.nextLong());
		generateTerrain(cx, cz, p);
		generateWater(p, r, 1);
		generateBedrockLayer(p, r, 0);

		Chunk chunk = new Chunk(world, p, cx, cz);
		chunk.generateSkylightMap();
		return chunk;
	}

	private void generateBedrockLayer(ChunkPrimer p, Random r, int y) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				p.setBlockState(x, y, z, BEDROCK);
				p.setBlockState(x, 128, z, Blocks.BEDROCK.getDefaultState());
			}
		}
	}

	private void generateWater(ChunkPrimer p, Random r, int yn) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 126; y++) {
					if (p.getBlockState(x, y + yn, z) == Blocks.AIR.getDefaultState()) {
						p.setBlockState(x, y + yn, z, WATER);
					}
				}
			}
		}
	}

	public int getHeightAtPosition(double cx, double cz) {
		double scaling = 0.125;
		double val = this.gensPerlin.getValue(cx * scaling, cz * scaling);
		int height = ((int) (val * 11)) * 16;

		return 65 + height;
	}

	@Override
	public void populate(int x, int z) {

		int i = x * 16;
		int j = z * 16;

		BlockPos blockpos = new BlockPos(i, 0, j);
		Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));

		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.random, x, z, false);

		biome.decorate(this.world, this.random, new BlockPos(i, 0, j));

		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.random, x, z, false);

	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return null;
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
										   boolean findUnexplored) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {

	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
}