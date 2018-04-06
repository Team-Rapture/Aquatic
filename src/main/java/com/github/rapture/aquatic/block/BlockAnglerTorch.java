package com.github.rapture.aquatic.block;

import java.util.Random;

import com.github.rapture.aquatic.Aquatic;

import com.github.rapture.aquatic.init.AquaticBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockAnglerTorch extends BlockBase {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool[] STICK = new PropertyBool[]{PropertyBool.create("stick")};
	public boolean isTorchOnTop = false;

	public BlockAnglerTorch() {
		super("angler_torch", Material.ROCK);
		this.setTickRandomly(true);
		this.setCreativeTab(Aquatic.CREATIVE_TAB);
		this.setLightLevel(2.0f);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(STICK[0], false));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.4000000059604645D * 0.85, 0.0D, 0.4000000059604645D * 0.85, 0.6000000238418579D, 1, 0.6000000238418579D);
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return new AxisAlignedBB(3.5 * 0.16, 1, 3.5 * 0.16, 0.16, 0, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.7D;
		double d2 = (double) pos.getZ() + 0.5D;
		double d3 = 0.22D;
		double d4 = 0.27D;

		if (enumfacing.getAxis().isHorizontal()) {
			EnumFacing enumfacing1 = enumfacing.getOpposite();
			worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 + 0.27D * (double) enumfacing1.getFrontOffsetX(),
					d1 + 0.22D, d2 + 0.27D * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + 0.27D * (double) enumfacing1.getFrontOffsetX(),
					d1 + 0.22D, d2 + 0.27D * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
		} else {
			worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

	private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
		BlockPos blockpos = pos.offset(facing.getOpposite());
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

		if (facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos)) {
			return true;
		} else if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
			return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
		} else {
			return true;
		}
	}

	private boolean canPlaceOn(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		switch (face) {
			case DOWN:
				return false;
			case UP:
				return isWater(world, pos.add(0, 1, 0));
			case NORTH:
				return isWater(world, pos.add(0, 0, -1));
			case SOUTH:
				return isWater(world, pos.add(0, 0, 1));
			case EAST:
				return isWater(world, pos.add(1, 0, 0));
			case WEST:
				return isWater(world, pos.add(-1, 0, 0));
		}
		return false;
	}

	private boolean isWater(IBlockAccess world, BlockPos pos) {
		return world.getBlockState(pos).getMaterial() == Material.WATER;
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

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return true;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.setDefaultFacing(worldIn, pos, state);
	}

	public void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
											float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
								ItemStack stack) {
		if (worldIn.getBlockState(pos.up()).getBlock() == AquaticBlocks.ANGLER_TORCH) {
			isTorchOnTop = true;
			worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(STICK[0], isTorchOnTop), 2);
		} else {
			isTorchOnTop = false;
			worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING, STICK[0]});
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.up()).getBlock() == AquaticBlocks.ANGLER_TORCH) {
			isTorchOnTop = true;
			state = state.withProperty(STICK[0], isTorchOnTop);
		}
		return state;
	}
}
