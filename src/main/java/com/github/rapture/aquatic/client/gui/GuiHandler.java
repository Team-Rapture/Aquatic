package com.github.rapture.aquatic.client.gui;

import com.github.rapture.aquatic.client.gui.container.ContainerEnergyFiller;
import com.github.rapture.aquatic.client.gui.container.ContainerOxygenFiller;
import com.github.rapture.aquatic.client.gui.container.ContainerSolutionTank;
import com.github.rapture.aquatic.client.gui.machines.GuiEnergyFiller;
import com.github.rapture.aquatic.client.gui.machines.GuiOxygenFiller;
import com.github.rapture.aquatic.client.gui.machines.GuiSolutionTank;
import com.github.rapture.aquatic.tileentity.TileEnergyFiller;
import com.github.rapture.aquatic.tileentity.TileOxygenFiller;
import com.github.rapture.aquatic.tileentity.TileSolutionTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    public static final int GUI_SOLUTION_TANK = 0;
    public static final int GUI_OXYGEN_FILLER = 1;
    public static final int GUI_ENERGY_FILLER = 2;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (ID == GUI_SOLUTION_TANK) {
            if (te instanceof TileSolutionTank) {
                return new ContainerSolutionTank(player.inventory, (TileSolutionTank) te);
            }
        }

        if (ID == GUI_OXYGEN_FILLER) {
            if (te instanceof TileOxygenFiller) {
                return new ContainerOxygenFiller(player.inventory, (TileOxygenFiller) te);
            }
        }

        if (ID == GUI_ENERGY_FILLER) {
            if (te instanceof TileEnergyFiller) {
                return new ContainerEnergyFiller(player.inventory, (TileEnergyFiller) te);
            }
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (ID == GUI_SOLUTION_TANK) {
            if (te instanceof TileSolutionTank) {
                return new GuiSolutionTank(new ContainerSolutionTank(player.inventory, (TileSolutionTank) te), (TileSolutionTank) te);
            }
        }

        if (ID == GUI_OXYGEN_FILLER) {
            if (te instanceof TileOxygenFiller) {
                return new GuiOxygenFiller(new ContainerOxygenFiller(player.inventory, (TileOxygenFiller) te), (TileOxygenFiller) te);
            }
        }

        if (ID == GUI_ENERGY_FILLER) {
            if (te instanceof TileEnergyFiller) {
                return new GuiEnergyFiller(new ContainerEnergyFiller(player.inventory, (TileEnergyFiller) te), (TileEnergyFiller) te);
            }
        }

        return null;
    }
}
