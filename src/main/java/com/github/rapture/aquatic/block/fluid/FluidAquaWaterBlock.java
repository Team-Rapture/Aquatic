package com.github.rapture.aquatic.block.fluid;

import com.github.rapture.aquatic.proxy.CommonProxy;
import com.github.rapture.aquatic.util.ICustomModelProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;

import javax.annotation.Nonnull;

public class FluidAquaWaterBlock extends BlockFluidClassic implements ICustomModelProvider {

    public FluidAquaWaterBlock() {
        super(CommonProxy.AQUA_WATER, Material.WATER);
        this.setRegistryName(CommonProxy.AQUA_WATER.getName());
        this.setUnlocalizedName(fluidName);
    }

    @Override
    public void initModel() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }

    @Override
    public boolean shouldSideBeRendered(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side)
    {
        if(side == EnumFacing.UP)
        {
            return !world.getBlockState(pos.add(0,1,0)).isFullBlock();
        }
        return super.shouldSideBeRendered(state, world, pos, side);
    }
}
