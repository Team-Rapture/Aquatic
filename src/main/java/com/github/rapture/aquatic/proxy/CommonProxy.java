package com.github.rapture.aquatic.proxy;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.api.oxygen.IOxygenProvider;
import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.api.ph.IPHProvider;
import com.github.rapture.aquatic.api.ph.PHHandler;
import com.github.rapture.aquatic.block.fluid.FluidAquaWater;
import com.github.rapture.aquatic.client.gui.GuiHandler;
import com.github.rapture.aquatic.init.AquaticOreDictionary;
import com.github.rapture.aquatic.init.AquaticRecipes;
import com.github.rapture.aquatic.init.AquaticTiles;
import com.github.rapture.aquatic.tileentity.TileDepthGenerator;
import com.github.rapture.aquatic.util.AutoRegistry;
import com.github.rapture.aquatic.util.FluidUtils;
import com.github.rapture.aquatic.util.UpdateChecker;
import com.github.rapture.aquatic.util.capability.OxygenStorage;
import com.github.rapture.aquatic.util.capability.PHStorage;
import com.github.rapture.aquatic.world.dimension.DimensionAquatic;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
		AquaticTiles.registerTiles();
		DimensionAquatic.init();
		CapabilityManager.INSTANCE.register(IOxygenProvider.class, new OxygenStorage(), OxygenHandler::new);
		CapabilityManager.INSTANCE.register(IPHProvider.class, new PHStorage(), PHHandler::new);
		NetworkRegistry.INSTANCE.registerGuiHandler(Aquatic.instance, new GuiHandler());
		AquaticOreDictionary.initOreDic();
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
