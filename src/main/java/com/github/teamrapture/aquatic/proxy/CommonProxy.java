package com.github.teamrapture.aquatic.proxy;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.api.oxygen.IOxygenProvider;
import com.github.teamrapture.aquatic.api.ph.IPHProvider;
import com.github.teamrapture.aquatic.block.fluid.FluidAquaWater;
import com.github.teamrapture.aquatic.client.gui.GuiHandler;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.init.AquaticCrafting;
import com.github.teamrapture.aquatic.init.AquaticRecipes;
import com.github.teamrapture.aquatic.oxygen.OxygenHandler;
import com.github.teamrapture.aquatic.oxygen.OxygenStorage;
import com.github.teamrapture.aquatic.ph.PHHandler;
import com.github.teamrapture.aquatic.ph.PHStorage;
import com.github.teamrapture.aquatic.tileentity.TileDepthGenerator;
import com.github.teamrapture.aquatic.util.AutoRegistry;
import com.github.teamrapture.aquatic.util.FluidUtils;
import com.github.teamrapture.aquatic.util.UpdateChecker;
import com.github.teamrapture.aquatic.world.WorldProviderAquatic;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	public static final Fluid AQUA_WATER = FluidUtils.createAndRegister(new FluidAquaWater());

	public void preInit(FMLPreInitializationEvent event) {
		AutoRegistry.findRegistryEntries(event);
		AquaticRecipes.FurnaceRegister(event);
		
	}

	public void init(FMLInitializationEvent event) {
		AquaticBlocks.registerTiles();
        DimensionManager.registerDimension(AquaticConfig.dimension.dimensionID, WorldProviderAquatic.DIMENSION_TYPE);
		CapabilityManager.INSTANCE.register(IOxygenProvider.class, new OxygenStorage(), OxygenHandler::new);
		CapabilityManager.INSTANCE.register(IPHProvider.class, new PHStorage(), PHHandler::new);
		NetworkRegistry.INSTANCE.registerGuiHandler(Aquatic.instance, new GuiHandler());
		AquaticCrafting.initOreDictionary();
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
