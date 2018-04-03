package com.github.rapture.aquatic.world.dimension.biome;

import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.world.dimension.generator.WorldGenPlants;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class BiomeAquaticDecorator extends BiomeDecorator {

    private WorldGenPlants corals = new WorldGenPlants(AquaticBlocks.CORAL_REEF_BLUE);

    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
        super.decorate(worldIn, random, biome, pos);
    }

    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldIn, random, this.chunkPos));

        if (TerrainGen.decorate(worldIn, random, this.chunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
            for (int l2 = 0; l2 < 5; ++l2) {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0) {
                    this.corals.setBlock(AquaticBlocks.CORAL_REEF_BLUE);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }

            for (int l2 = 0; l2 < 5; ++l2) {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0) {
                    this.corals.setBlock(AquaticBlocks.CORAL_REEF_YELLOW);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }

            for (int l2 = 0; l2 < 4; ++l2) {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0) {
                    this.corals.setBlock(AquaticBlocks.CORAL_REEF_RED);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }

            for (int l2 = 0; l2 < 4; ++l2) {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0) {
                    this.corals.setBlock(AquaticBlocks.CORAL_REEF_PINK);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }

            for (int l2 = 0; l2 < 2; ++l2) {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0) {
                    this.corals.setBlock(AquaticBlocks.CORAL_REEF_GREEN);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }

            for (int l2 = 0; l2 < 1; ++l2) {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0) {
                    this.corals.setBlock(AquaticBlocks.HYDRILLA);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }
        }
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldIn, random, this.chunkPos));
    }


}
