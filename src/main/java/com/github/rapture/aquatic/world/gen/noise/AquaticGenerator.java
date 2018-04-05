package com.github.rapture.aquatic.world.gen.noise;

import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorSimplex;

import java.util.Random;

public class AquaticGenerator extends NoiseGenerator {

    private final NoiseGeneratorSimplex[] noiseLevels;
    private final int levels;

    public AquaticGenerator(Random random, int levels) {
        this.levels = levels;
        this.noiseLevels = new NoiseGeneratorSimplex[levels];

        for (int i = 0; i < levels; ++i) {
            this.noiseLevels[i] = new NoiseGeneratorSimplex(random);
        }
    }

    public double getValue(double x, double y) {
        double d0 = 0.0D;
        double d1 = 1.0D;

        for (int i = 0; i < levels; ++i) {
            d0 += noiseLevels[i].getValue(x * d1, y * d1) * d1;
            d1 /= 2.0D;
        }

        return d0;
    }
}
