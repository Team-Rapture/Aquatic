package com.github.rapture.aquatic.world.dimension.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import java.util.Random;

public class BiomeAquaticDecorator extends BiomeDecorator {

    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
        super.decorate(worldIn, random, biome, pos);
    }
}
