package com.github.teamrapture.aquatic.init;

import com.github.rapture.aquatic.client.render.RenderAquaNetController;
import com.github.rapture.aquatic.client.render.RenderAquaNode;
import com.github.rapture.aquatic.client.render.RenderDepthGenerator;
import com.github.rapture.aquatic.client.render.RenderSolutionTank;
import com.github.rapture.aquatic.tileentity.*;
import com.github.teamrapture.aquatic.client.render.RenderAquaNetController;
import com.github.teamrapture.aquatic.client.render.RenderAquaNode;
import com.github.teamrapture.aquatic.client.render.RenderDepthGenerator;
import com.github.teamrapture.aquatic.client.render.RenderSolutionTank;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AquaticTiles {

    public static void registerTiles() {
        GameRegistry.registerTileEntity(TileAquaNetController.class, "aquatic:aquanet_controller");
        GameRegistry.registerTileEntity(TileAquaNode.class, "aquatic:aqua_node");
        GameRegistry.registerTileEntity(TileSolutionTank.class, "aquatic:solution_tank");
        GameRegistry.registerTileEntity(TileOxygenFiller.class, "aquatic:oxygen_filler");
        GameRegistry.registerTileEntity(TileEnergyFiller.class, "aquatic:energy_filler");
        GameRegistry.registerTileEntity(TileDepthGenerator.class, "aquatic:depth_generator");
        GameRegistry.registerTileEntity(TileCreativeGenerator.class, "aquatic:creative_generator");
    }

    @SideOnly(Side.CLIENT)
    public static void bindTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAquaNetController.class, new RenderAquaNetController());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAquaNode.class, new RenderAquaNode());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSolutionTank.class, new RenderSolutionTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDepthGenerator.class, new RenderDepthGenerator());
    }
}
