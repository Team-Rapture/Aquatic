package com.github.rapture.aquatic.proxy;

import com.github.rapture.aquatic.dimensions.DimensionAquatic;
import com.github.rapture.aquatic.init.AquaticTiles;
import com.github.rapture.aquatic.util.AutoRegistry;
import com.github.rapture.aquatic.util.UpdateChecker;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		AutoRegistry.findRegistryEntries(event);
	}

	public void init(FMLInitializationEvent event) {
		AquaticTiles.registerTiles();
		DimensionAquatic.init();
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

	public void serverStarting(FMLServerStartingEvent event) {
		UpdateChecker.notifyServer();
	}

	public void registerRender(Item item) {
		// NO-OP
	}

	public void handleLightMapColor(float partialTicks, float[] colors) {

	}
}
