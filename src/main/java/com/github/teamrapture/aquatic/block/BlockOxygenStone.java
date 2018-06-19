package com.github.teamrapture.aquatic.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockOxygenStone extends BlockRenderInLiquidBase {

    public int bubbleTimer = 0;

    public BlockOxygenStone() {
        super("oxygen_stone");
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, getRadius(pos, 2, 6));
        if (players.size() > 0) {
            for (EntityPlayer player : players) {
                player.setAir(300);
            }
        }
        super.updateTick(world, pos, state, rand);
    }

    public AxisAlignedBB getRadius(BlockPos blockPos, int size, int height) {
        double hs = size / 2;
        double hh = height / 2;
        return new AxisAlignedBB(blockPos.getX() - hs, blockPos.getY() - hh, blockPos.getZ() - hs,
                blockPos.getX() + hs, blockPos.getY() + hh, blockPos.getZ() + hs);
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, true, pos.getX() + 1.5D - rand.nextDouble(), pos.getY() + 0.75 + (rand.nextDouble() * 0.3D), pos.getZ() + 1.5D - rand.nextDouble(), 0.05D, 0.35D * rand.nextDouble(), 0.05D);
    }

}
