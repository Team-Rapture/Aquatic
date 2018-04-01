package com.github.rapture.aquatic.config;

import net.minecraftforge.common.config.Config;

import static com.github.rapture.aquatic.Aquatic.*;

@Config(modid = MODID, name = MODNAME)
@Config.LangKey("config.aquatic.title")
public class AquaticConfig {

    @Config.Name("Enable Update Checker")
    @Config.Comment("whether to announce mod updates")
    public static boolean enableUpdateChecker = true;

    @Config.Name("Announce Beta Updates")
    @Config.Comment("enable to also show beta updates")
    public static boolean announceBetaUpdates = false;

    @Config.Name("Debug Mode")
    @Config.Comment("enable additional console output")
    public static boolean debugMode = false;

    @Config.Name("Aqua Net Controller")
    @Config.Comment("Amount of energy an aqua net controller will generate")
    public static int aquaNetGeneration = 50;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Config.Name("Dim ID")
    @Config.Comment("Dimension ID- CAREFUL WHEN CHANGING ID! Default: 300")
    public static int dimensionID = 300;
    
    @Config.Name("Angler Spawn Rate")
    @Config.Comment("Angler Fish Spawn Rate - Default: 8")
    public static int angler_spawn_rate = 8;
    
    
    @Config.Name("Angler MIN amount")
    @Config.Comment("Angler Fish Spawn Rate - Default: 1")
    public static int angler_MIN_spawn_rate = 1;
    
    @Config.Name("Angler MAX amount")
    @Config.Comment("Angler Fish Spawn Rate - Default: 3")
    public static int angler_MAX_spawn_rate = 3;
    
    
    
    
}
