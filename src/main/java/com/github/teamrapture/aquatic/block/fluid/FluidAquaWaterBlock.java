package com.github.teamrapture.aquatic.block.fluid;

import com.deflatedpickle.picklelib.api.IFishable;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.proxy.CommonProxy;
import com.github.rapture.aquatic.util.ICustomModelProvider;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.proxy.CommonProxy;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@Optional.Interface(modid = "picklelib", iface = "com.deflatedpickle.picklelib.api.IFishable")
public class FluidAquaWaterBlock extends BlockFluidClassic implements ICustomModelProvider, IFishable {

    public FluidAquaWaterBlock() {
        super(CommonProxy.AQUA_WATER, Material.WATER);
        this.setRegistryName(CommonProxy.AQUA_WATER.getName());
        this.setUnlocalizedName(fluidName);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }

    @Override
    public boolean shouldSideBeRendered(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        if (side == EnumFacing.UP) {
            IBlockState upperState = world.getBlockState(pos.add(0, 1, 0));
            return (!isSourceBlock(world, pos) || !upperState.isFullBlock()) && upperState.getMaterial() != Material.WATER;
        }
        return super.shouldSideBeRendered(state, world, pos, side);
    }

    @Override
    public boolean isFishable() {
        return AquaticConfig.compatibility.explosiveFishingInAquaticDimension;
    }
}
