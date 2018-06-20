package com.github.teamrapture.aquatic;

import com.github.teamrapture.aquatic.creativetab.CreativeTab;
import com.github.teamrapture.aquatic.proxy.CommonProxy;
import net.minecraft.init.Items;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        name = Aquatic.MODNAME,
        version = Aquatic.VERSION,
        modid = Aquatic.MODID,
        certificateFingerprint = Aquatic.FINGERPRINT_KEY,
        dependencies = Aquatic.DEPENDENCIES,
        updateJSON = Aquatic.UPDATE_JSON
)
public class Aquatic {

    public static final String MODNAME = "Aquatic";
    public static final String MODID = "aquatic";
    public static final String VERSION = "@VERSION@";
    public static final String FINGERPRINT_KEY = "@FINGERPRINTKEY@";
    public static final String DEPENDENCIES = ""; // none for now
    public static final String UPDATE_JSON = "@UPDATEJSON@";
    public static final CreativeTab CREATIVE_TAB = new CreativeTab(MODID);
    @Instance(value = Aquatic.MODID)
    public static Aquatic instance;
    @SidedProxy(clientSide = "com.github.teamrapture.aquatic.proxy.ClientProxy", serverSide = "com.github.teamrapture.aquatic.proxy.ServerProxy")
    public static CommonProxy proxy;
    private static Logger log = LogManager.getLogger(MODID);

    static {
        FluidRegistry.enableUniversalBucket();
        log.debug("Enabled Forge Universal Bucket!");
    }

    public static Logger getLogger() {
        return log;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        CREATIVE_TAB.setIcon(Items.PRISMARINE_SHARD);
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
}
