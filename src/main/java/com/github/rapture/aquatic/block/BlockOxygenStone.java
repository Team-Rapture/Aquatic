package com.github.rapture.aquatic.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOxygenStone extends BlockBase {

    public BlockOxygenStone() {
        super("oxygen_stone");
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, true, pos.getX() + 1.5D - rand.nextDouble(), pos.getY() + 0.75 + (rand.nextDouble() * 0.3D), pos.getZ()  + 1.5D - rand.nextDouble(), 0.05D, 0.35D * rand.nextDouble(), 0.05D);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
