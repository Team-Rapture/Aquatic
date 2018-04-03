package com.github.rapture.aquatic.proxy;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.block.fluid.FluidAquaWater;
import com.github.rapture.aquatic.client.gui.GuiHandler;
import com.github.rapture.aquatic.init.AquaticTiles;
import com.github.rapture.aquatic.network.CapabilityRegistry;
import com.github.rapture.aquatic.tileentity.TileDepthGenerator;
import com.github.rapture.aquatic.util.AutoRegistry;
import com.github.rapture.aquatic.util.FluidUtil;
import com.github.rapture.aquatic.util.UpdateChecker;
import com.github.rapture.aquatic.world.dimension.DimensionAquatic;
import com.github.rapture.aquatic.world.dimension.biome.BiomeAquaticDecorator;
import com.github.rapture.aquatic.world.dimension.generator.WorldGenReefs;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public static final Fluid AQUA_WATER = FluidUtil.createAndRegister(new FluidAquaWater());

    public void preInit(FMLPreInitializationEvent event) {
        AutoRegistry.findRegistryEntries(event);
        GameRegistry.registerWorldGenerator(new WorldGenReefs(),1);
  
    }

    public void init(FMLInitializationEvent event) {
        AquaticTiles.registerTiles();
        DimensionAquatic.init();
        CapabilityRegistry.registerCapabilities();
        NetworkRegistry.INSTANCE.registerGuiHandler(Aquatic.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
        TileDepthGenerator.init();
    }

    public void serverStarting(FMLServerStartingEvent event) {
        UpdateChecker.notifyServer();
    }

    public void registerRender(Item item) {
        // NO-OP
    }

    public void registerRender(Block block) {
        // NO-OP
    }

    public void handleLightMapColor(float partialTicks, float[] colors) {

    }
}
