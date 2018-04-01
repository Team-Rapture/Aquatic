package com.github.rapture.aquatic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.rapture.aquatic.creativetab.CreativeTab;
import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.proxy.CommonProxy;

import net.minecraft.init.Items;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(name = Aquatic.MODNAME, version = Aquatic.VERSION, modid = Aquatic.MODID, certificateFingerprint = Aquatic.FINGERPRINT_KEY, dependencies = Aquatic.DEPENDENCIES, updateJSON = Aquatic.UPDATE_JSON)

public class Aquatic {

	public static final String MODNAME = "Aquatic";
	public static final String MODID = "aquatic";
	public static final String VERSION = "@VERSION@";
	public static final String FINGERPRINT_KEY = "@FINGERPRINTKEY@";
	public static final String DEPENDENCIES = ""; // none for now
	public static final String UPDATE_JSON = "@UPDATEJSON@";
	@Instance(value = Aquatic.MODID)
	public static Aquatic instance;
	private static Logger log = LogManager.getLogger(MODID);

	@SidedProxy(clientSide = "com.github.rapture.aquatic.proxy.ClientProxy", serverSide = "com.github.rapture.aquatic.proxy.ServerProxy")
	public static CommonProxy proxy;

	public static Logger getLogger() {
		return log;
	}

	public static final CreativeTab CREATIVE_TAB = new CreativeTab(MODID);

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		CREATIVE_TAB.setIcon(Items.PRISMARINE_SHARD);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		getLogger().info("Initialization finished.");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		getLogger().info("Post-Initialization finished.");
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
		getLogger().info("World initialization complete.");
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFogRender(EntityViewRenderEvent.FogDensity e) {
		if (e.getState().getBlock() == AquaticBlocks.AQUA_WATER_BLOCK) {
			e.setDensity(0.0F);
			e.setCanceled(true);
		}
	}
}
