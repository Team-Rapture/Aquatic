package com.github.rapture.aquatic.world.dimension.generator;

import com.github.rapture.aquatic.init.AquaticBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenPlants extends WorldGenerator {

<<<<<<< HEAD
	private Block block;
	private IBlockState state;

	public WorldGenPlants(Block blockIn) {
		this.setBlock(blockIn);
	}

	public void setBlock(Block blockIn) {
		this.block = blockIn;
		this.state = blockIn.getDefaultState();
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		for (int i = 0; i < 100; ++i) {
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4),
					rand.nextInt(8) - rand.nextInt(8));
		
			if (worldIn.getBlockState(position) == AquaticBlocks.AQUA_WATER_BLOCK.getDefaultState()
					&& (worldIn.getBlockState(position.down()) != AquaticBlocks.AQUA_WATER_BLOCK.getDefaultState()
							&& worldIn.getBlockState(position.down()) == AquaticBlocks.AQUATIC_STONE.getDefaultState() )) {
				worldIn.setBlockState(blockpos, this.state, 2);
			}
		}

		return true;
	}
=======
    private Block block;
    private IBlockState state;

    public WorldGenPlants(Block blockIn) {
        this.setBlock(blockIn);
    }

    public void setBlock(Block blockIn) {
        this.block = blockIn;
        this.state = blockIn.getDefaultState();
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 20; ++i) {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4),
                    rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.getBlockState(position) == AquaticBlocks.AQUATIC_STONE.getDefaultState()
                    && (worldIn.getBlockState(position.down()) != AquaticBlocks.AQUA_WATER_BLOCK.getDefaultState()
                    || blockpos.getY() < 20)) {
                worldIn.setBlockState(blockpos, this.state, 2);
            }
        }

        return true;
    }
>>>>>>> 457b3cd208e090010db1b82e8b6a08256d15a87f
}
