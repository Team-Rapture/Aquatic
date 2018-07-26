package com.github.teamrapture.aquatic.block;

import com.github.teamrapture.aquatic.entity.EntityWaterBubble;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOxygenStone extends BlockRenderInLiquidBase {

    public BlockOxygenStone() {
        super("oxygen_stone");
        this.setTickRandomly(true);
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if(!world.isRemote) for(int i = 0; i < 3; i++) {  //TODO adjust values?
            EntityWaterBubble bubble = new EntityWaterBubble(world, pos.getX() + 1.2D - rand.nextDouble() * 1.2D, pos.getY() + 0.5D + rand.nextDouble() * 0.2D, pos.getZ() + 1.2D - rand.nextDouble() * 1.2D);
            bubble.setSize(rand.nextFloat() * 0.7F + 0.4F);
            bubble.setAirSupply((int) MathHelper.clamp(bubble.getSize() * 300, 0, 300));
            world.spawnEntity(bubble);
        }
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, true, pos.getX() + 1.5D - rand.nextDouble(), pos.getY() + 0.75 + (rand.nextDouble() * 0.3D), pos.getZ() + 1.5D - rand.nextDouble(), 0.05D - rand.nextDouble() * 1.0D, 0.35D * rand.nextDouble() + 0.15D, 0.05D - rand.nextDouble() * 1.0D);
    }

}
