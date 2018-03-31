package com.github.rapture.aquatic.config;

import static com.github.rapture.aquatic.Aquatic.MODID;
import static com.github.rapture.aquatic.Aquatic.MODNAME;

import net.minecraftforge.common.config.Config;

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
    public static int aquaNetGeneration = 5;
    @Config.Name("Dim ID")
    @Config.Comment("Dimension Id, Default 300")
    public static int dimensionID = 300;
}
