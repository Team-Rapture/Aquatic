package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.tileentity.TileAquaNetController;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AquaticTiles {

    public static void registerTiles() {
        GameRegistry.registerTileEntity(TileAquaNetController.class, "aquatic:aquanet_controller");
    }
}
