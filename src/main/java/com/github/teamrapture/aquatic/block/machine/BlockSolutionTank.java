package com.github.teamrapture.aquatic.block.machine;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.block.BlockContainerBase;
import com.github.rapture.aquatic.client.gui.GuiHandler;
import com.github.rapture.aquatic.tileentity.TileSolutionTank;
import com.github.teamrapture.aquatic.client.gui.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class BlockSolutionTank extends BlockContainerBase {

    public BlockSolutionTank() {
        super("solution_tank", Material.ROCK);
        this.setHardness(1.0f);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileSolutionTank te = (TileSolutionTank) world.getTileEntity(pos);

        IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
        if (FluidUtil.interactWithFluidHandler(player, hand, handler)) {
        } else {
            if (!world.isRemote) {
                player.openGui(Aquatic.instance, GuiHandler.GUI_SOLUTION_TANK, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSolutionTank();
    }
}
