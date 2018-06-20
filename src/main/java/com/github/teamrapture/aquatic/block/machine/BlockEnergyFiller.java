package com.github.teamrapture.aquatic.block.machine;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.block.BlockContainerBase;
import com.github.teamrapture.aquatic.client.gui.GuiHandler;
import com.github.teamrapture.aquatic.tileentity.TileEnergyFiller;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEnergyFiller extends BlockContainerBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockEnergyFiller() {
        super("energy_filler", Material.ROCK);
        this.setHardness(1.0f);
        this.setHarvestLevel("pickaxe", 1);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(Aquatic.instance, GuiHandler.GUI_ENERGY_FILLER, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing facing = state.getValue(FACING);
            boolean flag = false;
            switch (facing) {
                case NORTH:
                    flag = iblockstate.isFullBlock() && !iblockstate1.isFullBlock();
                    break;
                case EAST:
                    flag = iblockstate3.isFullBlock() && !iblockstate2.isFullBlock();
                    break;
                case WEST:
                    flag = iblockstate2.isFullBlock() && !iblockstate3.isFullBlock();
                    break;
                case SOUTH:
                    flag = iblockstate1.isFullBlock() && !iblockstate.isFullBlock();
                    break;
            }
            worldIn.setBlockState(pos, state.withProperty(FACING, flag ? facing.getOpposite() : facing), 0x1b); //1, 2, 8, 16 = 0b1101 = 0x1b = 27
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEnergyFiller();
    }
}
