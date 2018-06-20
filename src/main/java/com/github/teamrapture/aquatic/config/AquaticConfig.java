package com.github.teamrapture.aquatic.config;

import net.minecraftforge.common.config.Config;

import static com.github.teamrapture.aquatic.Aquatic.MODID;
import static com.github.teamrapture.aquatic.Aquatic.MODNAME;

@Config(modid = MODID, name = MODNAME)
@Config.LangKey("config.aquatic.title")
public class AquaticConfig {

    @Config.Name("Aquatic Machines")
    public static Machines machines = new Machines();

    @Config.Name("Aquatic Dimension")
    public static Dimension dimension = new Dimension();

    @Config.Name("Experimental Features")
    @Config.Comment("WARNING: these settings might be unstable!")
    public static ExperimentalFeatures experimental = new ExperimentalFeatures();

    @Config.Name("Update Checker")
    public static Updater updater = new Updater();

    @Config.Name("Mod Compatibility")
    @Config.Comment("These config values will only be effective if the other mod is actually loaded!")
    public static ModCompat compatibility = new ModCompat();

    @Config.Name("Debug Mode")
    @Config.Comment("enable additional console output")
    public static boolean debugMode = false;

    //mod compatibility configs, only effective if the other mod is actually loaded!
    public static class ModCompat {

        @Config.Name("Explosive Fishing")
        @Config.Comment({"ExplosiveFishing - Can you fish in the aquatic dimension?", "Default: true"})
        public boolean explosiveFishingInAquaticDimension = true;
    }

    public static class Machines {

        @Config.RangeInt(min = 0)
        @Config.Name("Aqua Net Controller")
        @Config.Comment({"Amount of oxygen an aqua net controller will generate per 20 FE", "Default: 10"})
        public int aquaNetGeneration = 10;

        @Config.Name("Aqua Node Laser Beams")
        @Config.Comment({"Set to false to disable laser beam render to player from Aqua Nodes", "Default: true"})
        public boolean aquaNodeBeam = true;

        @Config.RangeInt(min = 0)
        @Config.Name("Depth Generator energy usage")
        @Config.Comment({"Set the amount of power per ore the depth generator uses", "Default: 2000"})
        public int depthUsage = 2000;

        @Config.RangeInt(min = 0)
        @Config.Name("Creative Battery max transfer")
        @Config.Comment({"how much the creative battery should transfer per tick and connection", "Default: 16384"})
        public int creativeBatteryMaxTransfer = 16384;
    }

    public static class Dimension {

        @Config.RangeInt(min = 0, max = 1000)
        @Config.RequiresMcRestart
        @Config.Name("Dimension ID")
        @Config.Comment({"Dimension ID - CAREFUL WHEN CHANGING!", "Default: 300"})
        public int dimensionID = 300;

        @Config.RangeInt(min = 0, max = 100)
        @Config.Name("Angler Spawn Rate")
        @Config.Comment({"Angler Fish Spawn Rate", "Default: 8"})
        public int angler_spawn_rate = 8;

        @Config.RangeInt(min = 0, max = 100)
        @Config.Name("Angler MIN amount")
        @Config.Comment({"Angler Fish Spawn Rate", "Default: 1"})
        public int angler_MIN_spawn_rate = 1;

        @Config.RangeInt(min = 0, max = 100)
        @Config.Name("Angler MAX amount")
        @Config.Comment({"Angler Fish Spawn Rate", "Default: 3"})
        public int angler_MAX_spawn_rate = 3;
    }

    public static class ExperimentalFeatures {

        @Config.Name("Aqua Water ANY liquid source")
        @Config.Comment({"make aqua water accept any liquid when checking for source blocks to form a new source block", "WARNING: this might cause a lot of lag when pouring aqua water over large bodies of other liquid!"})
        public boolean aquaWaterAcceptsAnyLiquidSource = false;
    }

    public static class Updater {

        @Config.RequiresMcRestart
        @Config.Name("Enable Update Checker")
        @Config.Comment({"whether to announce mod updates", "Default: true"})
        public boolean enableUpdateChecker = true;

        @Config.RequiresMcRestart
        @Config.Name("Announce Beta Updates")
        @Config.Comment({"enable to also show beta updates", "Default: false"})
        public boolean announceBetaUpdates = false;
    }
}
