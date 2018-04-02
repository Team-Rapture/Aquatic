package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.client.render.RenderAquaNetController;
import com.github.rapture.aquatic.client.render.RenderAquaNode;
import com.github.rapture.aquatic.client.render.RenderSolutionTank;
import com.github.rapture.aquatic.tileentity.TileAquaNetController;
import com.github.rapture.aquatic.tileentity.TileAquaNode;
import com.github.rapture.aquatic.tileentity.TileSolutionTank;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AquaticTiles {

    public static void registerTiles() {
        GameRegistry.registerTileEntity(TileAquaNetController.class, "aquatic:aquanet_controller");
        GameRegistry.registerTileEntity(TileAquaNode.class, "aquatic:aqua_node");
        GameRegistry.registerTileEntity(TileSolutionTank.class, "aquatic:solution_tank");
    }

    public static void bindTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAquaNetController.class, new RenderAquaNetController());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAquaNode.class, new RenderAquaNode());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSolutionTank.class, new RenderSolutionTank());
    }
}
