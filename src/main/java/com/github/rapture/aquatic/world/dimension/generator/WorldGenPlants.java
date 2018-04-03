package com.github.rapture.aquatic.world.dimension.generator;

import com.github.rapture.aquatic.init.AquaticBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenPlants extends WorldGenerator {

    private final Block block;

    public WorldGenPlants(Block blockIn) {
        this.block = blockIn;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 5; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.getBlockState(position) == AquaticBlocks.AQUA_WATER_BLOCK.getDefaultState() && worldIn.getBlockState(position.down()) != AquaticBlocks.AQUA_WATER_BLOCK.getDefaultState()) {
                worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
            }
        }

        return true;
    }
}
