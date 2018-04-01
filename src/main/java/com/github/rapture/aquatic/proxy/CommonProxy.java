package com.github.rapture.aquatic.proxy;

import com.github.rapture.aquatic.api.oxygen.IOxygenProvider;
import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.block.fluid.FluidAquaWater;
import com.github.rapture.aquatic.network.capability.OxygenStorage;
import com.github.rapture.aquatic.dimensions.DimensionAquatic;
import com.github.rapture.aquatic.entity.ModEntities;
import com.github.rapture.aquatic.init.AquaticTiles;
import com.github.rapture.aquatic.util.AutoRegistry;
import com.github.rapture.aquatic.util.UpdateChecker;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

	public static FluidAquaWater AQUA_WATER = new FluidAquaWater();

	public void preInit(FMLPreInitializationEvent event) {
		AQUA_WATER.register();
		AutoRegistry.findRegistryEntries(event);
	}

	public void init(FMLInitializationEvent event) {
		AquaticTiles.registerTiles();
		DimensionAquatic.init();
<<<<<<< HEAD
		ModEntities.init();
=======
		CapabilityManager.INSTANCE.register(IOxygenProvider.class, new OxygenStorage(), OxygenHandler.class);
>>>>>>> 908bffbeb20c9cd5fecd4979e37f132ef064b979
	}

	public void postInit(FMLPostInitializationEvent event) {

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
