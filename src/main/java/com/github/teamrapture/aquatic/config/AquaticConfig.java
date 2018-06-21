package com.github.teamrapture.aquatic.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.github.teamrapture.aquatic.Aquatic.*;

@Config(modid = MODID, name = MODNAME)
@Config.LangKey("config.aquatic.title")
public class AquaticConfig {

    @Config.Name("Machines")
    public static final Machines machines = new Machines();

    @Config.Name("Dimension")
    public static final Dimension dimension = new Dimension();

    @Config.Name("Experimental Features")
    @Config.Comment("WARNING: these settings might be unstable!")
    public static final ExperimentalFeatures experimental = new ExperimentalFeatures();

    @Config.Name("Mod Updates")
    public static final Updater updates = new Updater();

    @Config.Name("Mod Compatibility")
    @Config.Comment("These config values will only be effective if the other mod is actually loaded!")
    public static final ModCompat compatibility = new ModCompat();

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

        @SideOnly(Side.CLIENT)
        @Config.Name("Aqua Node Laser Beams")
        @Config.Comment({"Set to false to disable laser beam render to player from Aqua Nodes", "CLIENT ONLY", "Default: true"})
        public boolean aquaNodeBeam = true;

        @Config.RangeInt(min = 0)
        @Config.Name("Depth Generator energy usage")
        @Config.Comment({"Set the amount of power per ore the depth generator uses", "Default: 2000"})
        public int depthUsage = 2048;

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

        @Config.RequiresMcRestart
        @Config.Name("Anglerfish")
        public final EntitySpawn anglerFish = new EntitySpawn(10, 1, 3);

        @Config.RequiresMcRestart
        @Config.Name("JellyFish")
        public final EntitySpawn jellyFish = new EntitySpawn(21, 5, 14);

        @Config.RequiresMcRestart
        @Config.Name("Shark")
        public final EntitySpawn shark = new EntitySpawn(8, 1, 2);
    }

    public static class EntitySpawn {

        @Config.RangeInt(min = 0, max = 100)
        @Config.Name("Spawn Rate")
        @Config.Comment("Spawn Weight")
        public int spawnRate;

        @Config.RangeInt(min = 0, max = 100)
        @Config.Name("MIN per group")
        @Config.Comment("minimum group size")
        public int minGroupSize;

        @Config.RangeInt(min = 0, max = 100)
        @Config.Name("MAX per group")
        @Config.Comment("maximum group size")
        public int maxGroupSize;

        public EntitySpawn(int spawnRate, int minGroupSize, int maxGroupSize) {
            this.spawnRate = spawnRate;
            this.minGroupSize = minGroupSize;
            this.maxGroupSize = maxGroupSize;
        }
    }

    public static class ExperimentalFeatures {

        @Config.Name("Debug Mode")
        @Config.Comment("enable additional console output")
        public boolean debugMode = false;

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
