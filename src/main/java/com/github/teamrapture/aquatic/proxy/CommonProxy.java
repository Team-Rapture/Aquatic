package com.github.teamrapture.aquatic.proxy;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.api.capability.oxygen.CapabilityOxygen;
import com.github.teamrapture.aquatic.api.capability.ph.CapabilityPH;
import com.github.teamrapture.aquatic.block.fluid.FluidAquaWater;
import com.github.teamrapture.aquatic.client.gui.GuiHandler;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.init.AquaticBiomes;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.init.AquaticEntities;
import com.github.teamrapture.aquatic.init.AquaticRecipes;
import com.github.teamrapture.aquatic.tileentity.TileDepthGenerator;
import com.github.teamrapture.aquatic.util.AutoRegistry;
import com.github.teamrapture.aquatic.util.FluidUtils;
import com.github.teamrapture.aquatic.util.UpdateChecker;
import com.github.teamrapture.aquatic.world.WorldProviderAquatic;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public static final Fluid AQUATIC_WATER = FluidUtils.createAndRegister(new FluidAquaWater());

	public void preInit(FMLPreInitializationEvent event) {
		AutoRegistry.findRegistryEntries(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(Aquatic.instance, new GuiHandler());
        CapabilityOxygen.register();
        CapabilityPH.register();
	}

	public void init(FMLInitializationEvent event) {
        AquaticBlocks.registerTiles();
        AquaticBiomes.registerBiomeTypes();
        AquaticEntities.init();
        DimensionManager.registerDimension(AquaticConfig.dimension.dimensionID, WorldProviderAquatic.DIMENSION_TYPE);
        AquaticRecipes.OreDict.initOreDictionary();
        AquaticRecipes.Smelting.registerRecipes();
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
