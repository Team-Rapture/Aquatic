package com.github.rapture.aquatic.world.gen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenMiniIsland extends WorldGenerator {

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < worldIn.rand.nextInt(34); y++) {
                    this.setBlockAndNotifyAdequately(worldIn, position.add(x, y, z), Blocks.CLAY.getDefaultState());
                }
            }
        }
        return true;
    }
}
