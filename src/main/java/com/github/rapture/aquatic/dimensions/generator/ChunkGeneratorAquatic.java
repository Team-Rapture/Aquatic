package com.github.rapture.aquatic.dimensions.generator;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockFalling;
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

public class ChunkGeneratorAquatic implements IChunkGenerator {

	private World world;

	private IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
	private IBlockState SAND = Blocks.SAND.getDefaultState();

	private IBlockState WATER = Blocks.WATER.getDefaultState();
	public NoiseGeneratorOctaves scaleNoise;
	public NoiseGeneratorOctaves depthNoise;
	private AquaticGenerator gensPerlin;
	private final WorldGenMiniIsland miniIsland = new WorldGenMiniIsland();
	double[] data4;
	private Random random;

	public ChunkGeneratorAquatic(World world) {
		this.world = world;
		this.random = new Random();

		random.setSeed(random.nextLong());
		gensPerlin = new AquaticGenerator(random, 16);
		this.scaleNoise = new NoiseGeneratorOctaves(this.random, 10);
		this.depthNoise = new NoiseGeneratorOctaves(this.random, 16);

		this.world.setSeaLevel(90);
		System.out.println(world.getSeaLevel());

	}

	@Override
	public Chunk generateChunk(int cx, int cz) {
		ChunkPrimer p = new ChunkPrimer();

		Random r = new Random();
		r.setSeed(world.getSeed());
		r.setSeed(cx * r.nextLong() + cz * r.nextLong());
		generateBedrockLayer(p, r, 0);
		generateWater(p, r, 1);

		Chunk chunk = new Chunk(world, p, cx, cz);
		chunk.generateSkylightMap();
		return chunk;
	}

	private void generateBedrockLayer(ChunkPrimer p, Random r, int y) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				p.setBlockState(x, y, z, BEDROCK);
			}
		}
	}

	private void generateWater(ChunkPrimer p, Random r, int yn) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 80; y++) {
					p.setBlockState(x, y + yn, z, WATER);
					
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
		 BlockFalling.fallInstantly = true;
		int i = x * 16;
		int j = z * 16;

		BlockPos blockpos = new BlockPos(i, 0, j);
		Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));

		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.random, x, z, false);

		if (this.random.nextInt(24) == 0) {
			this.miniIsland.generate(this.world, this.random, blockpos.add(this.random.nextInt(16) + 8,
					110 + this.random.nextInt(16), this.random.nextInt(16) + 8));

			if (this.random.nextInt(14) == 0) {
				this.miniIsland.generate(this.world, this.random, blockpos.add(this.random.nextInt(16) + 8,
						110 + this.random.nextInt(16), this.random.nextInt(16) + 8));
			}
		}

		biome.decorate(this.world, this.random, new BlockPos(i, 0, j));

		net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.random, x, z, false);
		 BlockFalling.fallInstantly = false;
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
