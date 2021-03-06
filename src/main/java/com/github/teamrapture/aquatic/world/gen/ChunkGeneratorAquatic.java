package com.github.teamrapture.aquatic.world.gen;

import com.github.teamrapture.aquatic.init.AquaticBlocks;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorAquatic implements IChunkGenerator {

    //config
    private static final int SEA_LEVEL = 156;

    private static final IBlockState WATER = AquaticBlocks.AQUA_WATER_BLOCK.getDefaultState();
    private static final IBlockState AIR = Blocks.AIR.getDefaultState();
    private static final IBlockState AQUA_STONE = AquaticBlocks.AQUATIC_STONE.getDefaultState();
    private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private static final IBlockState SAND = Blocks.SAND.getDefaultState();
    private static final IBlockState STONE_CRACKED = AquaticBlocks.AQUATIC_STONE_CRACKED.getDefaultState();
    private static final IBlockState GRASS_STONE_DARK = AquaticBlocks.GRASS_STONE_1.getDefaultState();
    private static final IBlockState GRASS_STONE_LIGHT = AquaticBlocks.AQUATIC_STONE_CRACKED.getDefaultState();
    private final WorldGenerator ironGen = new WorldGenMinable(AquaticBlocks.IRON_ORE_DEPOSIT.getDefaultState(), 6, BlockMatcher.forBlock(AquaticBlocks.AQUATIC_STONE));
    private final WorldGenerator goldGen = new WorldGenMinable(AquaticBlocks.GOLD_ORE_DEPOSIT.getDefaultState(), 4, BlockMatcher.forBlock(AquaticBlocks.AQUATIC_STONE));
    private final WorldGenerator diamonGen = new WorldGenMinable(AquaticBlocks.DIAMOND_ORE_DEPOSIT.getDefaultState(), 3, BlockMatcher.forBlock(AquaticBlocks.AQUATIC_STONE));
    private final WorldGenerator coalGen = new WorldGenMinable(AquaticBlocks.COAL_ORE_DEPOSIT.getDefaultState(), 18, BlockMatcher.forBlock(AquaticBlocks.AQUATIC_STONE));
    private final WorldGenerator emeraldGen = new WorldGenMinable(AquaticBlocks.EMERALD_ORE_DEPOSIT.getDefaultState(), 2, BlockMatcher.forBlock(AquaticBlocks.AQUATIC_STONE));
    private final WorldGenerator lapizGen = new WorldGenMinable(AquaticBlocks.LAPIZ_ORE_DEPOSIT.getDefaultState(), 6, BlockMatcher.forBlock(AquaticBlocks.AQUATIC_STONE));
    private final WorldGenerator redstoneGen = new WorldGenMinable(AquaticBlocks.REDSTONE_ORE_DEPOSIT.getDefaultState(), 8, BlockMatcher.forBlock(AquaticBlocks.AQUATIC_STONE));
    private final WorldGenerator quartzGen = new WorldGenMinable(AquaticBlocks.QUARTZ_ORE_DEPOSIT.getDefaultState(), 13, BlockMatcher.forBlock(AquaticBlocks.AQUATIC_STONE));

    private double[] pnr;
    private double[] ar;
    private double[] br;
    private double[] noiseData4;
    private double[] dr;
    private WorldGenPlants coralGenerator0 = new WorldGenPlants(AquaticBlocks.CORAL_REEF_GREEN);
    private WorldGenPlants coralGenerator1 = new WorldGenPlants(AquaticBlocks.CORAL_REEF_PINK);
    private WorldGenPlants coralGenerator2 = new WorldGenPlants(AquaticBlocks.CORAL_REEF_RED);
    private WorldGenPlants coralGenerator3 = new WorldGenPlants(AquaticBlocks.CORAL_REEF_YELLOW);
    private WorldGenPlants coralGenerator4 = new WorldGenPlants(AquaticBlocks.CORAL_REEF_BLUE);
    private WorldGenPlants hydrillaGenerator = new WorldGenPlants(AquaticBlocks.HYDRILLA);
    private WorldGenPlants oxygenGenerator = new WorldGenPlants(AquaticBlocks.OXYGEN_STONE);

    private WorldGenPlants grassGenerator = new WorldGenPlants(AquaticBlocks.SEA_GRASS);
    private NoiseGeneratorOctaves scaleNoise;
    private NoiseGeneratorOctaves depthNoise;
    private World world;
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] depthBuffer = new double[256];
    private double[] buffer;
    private NoiseGeneratorOctaves lperlinNoise1;
    private NoiseGeneratorOctaves lperlinNoise2;
    private NoiseGeneratorOctaves perlinNoise1;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves stoneExculsivityNoiseGen;
    private NoiseGeneratorAquatic gensPerlin;
    private Random rand;

    public ChunkGeneratorAquatic(World world) {
        this.world = world;
        this.rand = new Random(world.getSeed());

        rand.setSeed(rand.nextLong());
        gensPerlin = new NoiseGeneratorAquatic(rand, 16);
        this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);

        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.stoneExculsivityNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);

        InitNoiseGensEvent.ContextHell ctx = new InitNoiseGensEvent.ContextHell(lperlinNoise1, lperlinNoise2, perlinNoise1, slowsandGravelNoiseGen, stoneExculsivityNoiseGen, scaleNoise, depthNoise);
        ctx = TerrainGen.getModdedNoiseGenerators(world, this.rand, ctx);
        this.lperlinNoise1 = ctx.getLPerlin1();
        this.lperlinNoise2 = ctx.getLPerlin2();
        this.perlinNoise1 = ctx.getPerlin();
        this.slowsandGravelNoiseGen = ctx.getPerlin2();
        this.stoneExculsivityNoiseGen = ctx.getPerlin3();
        this.scaleNoise = ctx.getScale();
        this.depthNoise = ctx.getDepth();

        this.world.setSeaLevel(SEA_LEVEL);
    }

    private void prepareHeights(int p_185936_1_, int p_185936_2_, ChunkPrimer primer) {
        int j = this.world.getSeaLevel() / 2 + 1;
        this.buffer = this.getHeights(this.buffer, p_185936_1_ * 4, 0, p_185936_2_ * 4, 5, 17, 5);

        for (int j1 = 0; j1 < 4; ++j1) {
            for (int k1 = 0; k1 < 4; ++k1) {
                for (int l1 = 0; l1 < 16; ++l1) {
                    double d1 = this.buffer[(j1 * 5 + k1) * 17 + l1];
                    double d2 = this.buffer[(j1 * 5 + k1 + 1) * 17 + l1];
                    double d3 = this.buffer[((j1 + 1) * 5 + k1) * 17 + l1];
                    double d4 = this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1];
                    double d5 = (this.buffer[(j1 * 5 + k1) * 17 + l1 + 1] - d1) * 0.125D;
                    double d6 = (this.buffer[(j1 * 5 + k1 + 1) * 17 + l1 + 1] - d2) * 0.125D;
                    double d7 = (this.buffer[((j1 + 1) * 5 + k1) * 17 + l1 + 1] - d3) * 0.125D;
                    double d8 = (this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 1] - d4) * 0.125D;

                    for (int i2 = 0; i2 < 8; ++i2) {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for (int j2 = 0; j2 < 4; ++j2) {
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.25D;

                            for (int k2 = 0; k2 < 4; ++k2) {
                                IBlockState iblockstate = null;
                                if (l1 * 8 + i2 < j) iblockstate = WATER;
                                if (d15 > 0.0D) iblockstate = AQUA_STONE;
                                int l2 = j2 + j1 * 4;
                                int i3 = i2 + l1 * 8;
                                int j3 = k2 + k1 * 4;
                                primer.setBlockState(l2, i3, j3, iblockstate);
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
        }
    }

    private void buildSurfaces(int p_185937_1_, int p_185937_2_, ChunkPrimer primer) {
        if (!ForgeEventFactory.onReplaceBiomeBlocks(this, p_185937_1_, p_185937_2_, primer, this.world)) return;
        int i = this.world.getSeaLevel() + 1;
        double d0 = 0.03125D;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_185937_1_ * 16, p_185937_2_ * 16, 0, 16, 16, 1, 0.03125D, 0.03125D, 1.0D);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_185937_1_ * 16, 109, p_185937_2_ * 16, 16, 1, 16, 0.03125D, 1.0D, 0.03125D);
        this.depthBuffer = this.stoneExculsivityNoiseGen.generateNoiseOctaves(this.depthBuffer, p_185937_1_ * 16, p_185937_2_ * 16, 0, 16, 16, 1, 0.0625D, 0.0625D, 0.0625D);

        for (int j = 0; j < 16; ++j) {
            for (int k = 0; k < 16; ++k) {
                boolean flag = this.slowsandNoise[j + k * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.gravelNoise[j + k * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                int l = (int) (this.depthBuffer[j + k * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                int i1 = -1;
                IBlockState iblockstate = AQUA_STONE;
                IBlockState iblockstate1 = AQUA_STONE;

                for (int j1 = 127; j1 >= 0; --j1) {
                    if (j1 < 127 - this.rand.nextInt(5) && j1 > this.rand.nextInt(5)) {
                        IBlockState iblockstate2 = primer.getBlockState(k, j1, j);

                        if (iblockstate2.getBlock() != null && iblockstate2.getMaterial() != Material.AIR) {
                            if (iblockstate2.getBlock() == AquaticBlocks.AQUATIC_STONE) {
                                if (i1 == -1) {
                                    if (l <= 0) {
                                        iblockstate = AIR;
                                        iblockstate1 = AQUA_STONE;
                                    } else if (j1 >= i - 4 && j1 <= i + 1) {
                                        iblockstate = AQUA_STONE;
                                        iblockstate1 = AQUA_STONE;

                                        if (flag1) {
                                            iblockstate = SAND;
                                            iblockstate1 = AQUA_STONE;
                                        }

                                        if (flag) {
                                            iblockstate = STONE_CRACKED;
                                            iblockstate1 = STONE_CRACKED;
                                        }
                                       
                                    }

                                    if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR)) {
                                        iblockstate = WATER;
                                    }

                                    i1 = l;

                                    if (j1 >= i - 1) {
                                        primer.setBlockState(k, j1, j, iblockstate);
                                    } else {
                                        primer.setBlockState(k, j1, j, iblockstate1);
                                    }
                                } else if (i1 > 0) {
                                    --i1;
                                    primer.setBlockState(k, j1, j, iblockstate1);
                                }
                            }
                        } else {
                            i1 = -1;
                        }
                    }
                    /*else {
                        primer.setBlockState(k, j1, j, BEDROCK);
                    }*/
                }
            }
        }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long) x * 796235255948L + (long) z * 679238861542L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.prepareHeights(x, z, chunkprimer);
        this.buildSurfaces(x, z, chunkprimer);
        generateBedrockLayer(chunkprimer, rand, 0);
        generateWater(chunkprimer, rand);

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();
        for (int i = 0; i < abyte.length; ++i) {
            abyte[i] = (byte) Biome.getIdForBiome(abiome[i]);
        }
        chunk.setBiomeArray(abyte);
        chunk.resetRelightChecks();
        return chunk;
    }

    private void generateBedrockLayer(ChunkPrimer p, Random r, int yLevel) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                p.setBlockState(x, yLevel, z, BEDROCK);
            }
        }
    }

    private void generateWater(ChunkPrimer p, Random r) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 1; y < SEA_LEVEL + 1; y++) {
                    if (p.getBlockState(x, y, z) == Blocks.AIR.getDefaultState()) {
                        p.setBlockState(x, y, z, WATER);
                    }
                }
            }
        }
    }

    private double[] getHeights(double[] noiseField, int posX, int posY, int posZ, int sizeX, int sizeY, int sizeZ) {
        if (noiseField == null) noiseField = new double[sizeX * sizeY * sizeZ];

        ChunkGeneratorEvent.InitNoiseField event = new ChunkGeneratorEvent.InitNoiseField(this, noiseField, posX, posY, posZ, sizeX, sizeY, sizeZ);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.DENY) return event.getNoisefield();

        this.noiseData4 = this.scaleNoise.generateNoiseOctaves(this.noiseData4, posX, posY, posZ, sizeX, 1, sizeZ, 1.0D, 0.0D, 1.0D);
        this.dr = this.depthNoise.generateNoiseOctaves(this.dr, posX, posY, posZ, sizeX, 1, sizeZ, 100.0D, 0.0D, 100.0D);
        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, posX, posY, posZ, sizeX, sizeY, sizeZ, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, posX, posY, posZ, sizeX, sizeY, sizeZ, 684.412D, 2053.236D, 684.412D);
        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, posX, posY, posZ, sizeX, sizeY, sizeZ, 684.412D, 2053.236D, 684.412D);
        int i = 0;
        double[] adouble = new double[sizeY];

        for (int j = 0; j < sizeY; ++j) {
            adouble[j] = Math.cos(j * Math.PI * 6.0D / sizeY) * 2.0D;
            double d2 = j;

            if (j > sizeY / 2) d2 = sizeY - 1 - j;

            if (d2 < 4.0D) {
                d2 = 4.0D - d2;
                adouble[j] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (int l = 0; l < sizeX; ++l) {
            for (int i1 = 0; i1 < sizeZ; ++i1) {

                for (int k = 0; k < sizeY; ++k) {
                    double d4 = adouble[k];
                    double d5 = this.ar[i] / 512.0D;
                    double d6 = this.br[i] / 512.0D;
                    double d7 = (this.pnr[i] / 10.0D + 1.0D) / 2.0D;
                    double d8;

                    if (d7 < 0.0D) d8 = d5;
                    else if (d7 > 1.0D) d8 = d6;
                    else d8 = d5 + (d6 - d5) * d7;

                    d8 = d8 - d4;

                    if (k > sizeY - 4) {
                        double d9 = k - (sizeY - 4) / 3.0F;
                        d8 = d8 * (1.0D - d9) + -10.0D * d9;
                    }

                    if (k < 0.0D) {
                        double d10 = k / -4.0D;
                        d10 = MathHelper.clamp(d10, 0.0D, 1.0D);
                        d8 = d8 * (1.0D - d10) + -10.0D * d10;
                    }
                    noiseField[i] = d8;
                    i++;
                }
            }
        }
        return noiseField;
    }

    @Override
    public void populate(int x, int z) {
        BlockFalling.fallInstantly = true;
        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));

        ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(this.world, this.rand, blockpos));

        if (rand.nextInt(3) == 0) this.coralGenerator0.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
        if (rand.nextInt(3) == 0) this.coralGenerator1.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
        if (rand.nextInt(3) == 0) this.coralGenerator2.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
        if (rand.nextInt(3) == 0) this.coralGenerator3.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
        if (rand.nextInt(3) == 0) this.coralGenerator4.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
        if (rand.nextInt(3) == 0) this.hydrillaGenerator.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
        if (rand.nextInt(3) == 0) this.oxygenGenerator.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
       
        for (int l1 = 0; l1 < 6; l1++) { 
            this.ironGen.generate(this.world, this.rand, blockpos.add(0, this.rand.nextInt(108) + 10, 0));
            this.diamonGen.generate(this.world, this.rand, blockpos.add(0, this.rand.nextInt(108) + 10, 0));
            this.coalGen.generate(this.world, this.rand, blockpos.add(0, this.rand.nextInt(108) + 10, 0));
            this.goldGen.generate(this.world, this.rand, blockpos.add(0, this.rand.nextInt(108) + 10, 0));
            this.redstoneGen.generate(this.world, this.rand, blockpos.add(0, this.rand.nextInt(108) + 10, 0));
            this.quartzGen.generate(this.world, this.rand, blockpos.add(0, this.rand.nextInt(108) + 10, 0));
            this.emeraldGen.generate(this.world, this.rand, blockpos.add(0, this.rand.nextInt(108) + 10, 0));
            this.lapizGen.generate(this.world, this.rand, blockpos.add(0, this.rand.nextInt(108) + 10, 0));
          }

        biome.decorate(this.world, this.rand, blockpos);

        if (TerrainGen.populate(this, this.world, this.rand, x, z, false,
                PopulateChunkEvent.Populate.EventType.ANIMALS)) {
            WorldGenEntities.spawnMobsInWorldGen(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
        }
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(this.world, this.rand, blockpos));
        BlockFalling.fallInstantly = false;
    }

    @Override
    public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
    }

    //TODO structure gen!
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
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