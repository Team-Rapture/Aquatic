package com.github.teamrapture.aquatic.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockOxygenStone extends BlockRenderInLiquidBase {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625D, 0, 0.0625D, 0.9375D, 0.4375D, 0.9375D);

    public BlockOxygenStone() {
        super("oxygen_stone");



        //this.setTickRandomly(true);
    }

    //FIXME make bubbles not affect gameplay and render them in the random display tick
    //cause: lag.

    /*
    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
        if(!worldIn.isRemote) spawnBubble(worldIn, pos, random);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entityIn) {
        if(!world.isRemote && world.getTotalWorldTime() % 10 == 0) {
            entityIn.attackEntityFrom(DamageSource.IN_FIRE, 0.5F);
            spawnBubble(world, pos, RANDOM);
        }
    }

    private static void spawnBubble(World world, BlockPos pos, Random random) {//TODO adjust values?
        EntityWaterBubble bubble = new EntityWaterBubble(world, pos.getX() + 1.2D - random.nextDouble() * 1.2D, pos.getY() + 0.5D + random.nextDouble() * 0.2D, pos.getZ() + 1.2D - random.nextDouble() * 1.2D);
        bubble.setSize(random.nextFloat() * 0.7F + 0.4F);
        bubble.setAirSupply((int) (bubble.getSize() * 300));
        world.spawnEntity(bubble);
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, false, pos.getX() + rand.nextDouble(), pos.getY() + 0.4375D + rand.nextDouble() * 0.0875D, pos.getZ() + rand.nextDouble(), rand.nextDouble() * 0.5D - 0.25D, 0.35D * rand.nextDouble() + 0.15D, rand.nextDouble() * 0.5D - 0.25D);
    }
    */

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB; //TODO randomly offset by small amount on x/z?
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if(face == EnumFacing.UP) return BlockFaceShape.CENTER_BIG;
        if(face == EnumFacing.DOWN) return BlockFaceShape.UNDEFINED;
        return BlockFaceShape.MIDDLE_POLE_THICK;
    }

}
