package com.github.teamrapture.aquatic.block;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.util.IHasItemBlock;
import com.github.teamrapture.aquatic.util.NameUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockContainerBase extends BlockContainer implements IHasItemBlock {

    public BlockContainerBase(String name, Material material) {
        super(material);
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
        NameUtil.name(this, name);
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return face != EnumFacing.DOWN && world.getBlockState(pos.offset(face)).getMaterial() == Material.WATER;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

}
