package com.github.rapture.aquatic.world.dimension.biome;

import java.util.Random;

import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.world.dimension.generator.WorldGenPlants;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class BiomeAquaticDecorator extends BiomeDecorator {
	public WorldGenPlants corals = new WorldGenPlants(AquaticBlocks.coral_reef_blue);

    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
        super.decorate(worldIn, random, biome, pos);
    }
    @Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, this.chunkPos));

        
        

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, this.chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        {
            for (int l2 = 0; l2 < 5; ++l2)
            {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0)
                {
                    this.corals.setBlock(AquaticBlocks.coral_reef_blue);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }

            for (int l2 = 0; l2 < 5; ++l2)
            {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0)
                {
                    this.corals.setBlock(AquaticBlocks.coral_reef_yellow);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }

            for (int l2 = 0; l2 < 4; ++l2)
            {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0)
                {
                    this.corals.setBlock(AquaticBlocks.coral_reef_red);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }
            
            for (int l2 = 0; l2 < 4; ++l2)
            {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0)
                {
                    this.corals.setBlock(AquaticBlocks.coral_reef_pink);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }

            for (int l2 = 0; l2 < 2; ++l2)
            {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0)
                {
                    this.corals.setBlock(AquaticBlocks.coral_reef_green);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }
            
            for (int l2 = 0; l2 < 1; ++l2)
            {
                int i7 = random.nextInt(16) + 8;
                int l10 = random.nextInt(16) + 8;
                int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                if (j14 > 0)
                {
                    this.corals.setBlock(AquaticBlocks.HYDRILLA);
                    this.corals.generate(worldIn, random, this.chunkPos.add(i7, random.nextInt(j14), l10));
                }
            }
        }

      

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(worldIn, random, this.chunkPos));
    }

	
}
