package com.github.rapture.aquatic.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockRenderInLiquidBase extends BlockBase {

    public static final PropertyInteger FAKE_LEVEL = PropertyInteger.create("level", 0, 15);

    public BlockRenderInLiquidBase(String name) {
        super(name, Material.WATER);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FAKE_LEVEL, 0));
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FAKE_LEVEL, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FAKE_LEVEL);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FAKE_LEVEL);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

}
