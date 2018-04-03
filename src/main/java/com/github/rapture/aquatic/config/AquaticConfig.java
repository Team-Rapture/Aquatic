package com.github.rapture.aquatic.config;

import net.minecraftforge.common.config.Config;

import static com.github.rapture.aquatic.Aquatic.MODID;
import static com.github.rapture.aquatic.Aquatic.MODNAME;

@Config(modid = MODID, name = MODNAME)
@Config.LangKey("config.aquatic.title")
public class AquaticConfig {

    @Config.RequiresMcRestart
    @Config.Name("Enable Update Checker")
    @Config.Comment("whether to announce mod updates")
    public static boolean enableUpdateChecker = true;

    @Config.RequiresMcRestart
    @Config.Name("Announce Beta Updates")
    @Config.Comment("enable to also show beta updates")
    public static boolean announceBetaUpdates = false;

    @Config.Name("Debug Mode")
    @Config.Comment("enable additional console output")
    public static boolean debugMode = false;

    @Config.Name("Aqua Net Controller")
    @Config.Comment("Amount of oxygen an aqua net controller will generate per 20 FE")
    public static int aquaNetGeneration = 10;

    @Config.Name("Aqua Node Laser Beams")
    @Config.Comment("Set to false to disable laser beam render to player from Aqua Nodes")
    public static boolean aquaNodeBeam = true;

    @Config.Name("Depth Generator energy usage")
    @Config.Comment("Set the amount of power per ore the depth generator uses")
    public static int depthUsage = 2000;

    @Config.RequiresMcRestart
    @Config.Name("Dimension ID")
    @Config.Comment("Dimension ID - CAREFUL WHEN CHANGING! Default: 300")
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
