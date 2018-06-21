package com.github.teamrapture.aquatic.world;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.init.AquaticBiomes;
import com.github.teamrapture.aquatic.world.gen.ChunkGeneratorAquatic;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAquatic extends WorldProvider {

    public static DimensionType DIMENSION_TYPE = DimensionType.register("Aquatic", "_aquatic", AquaticConfig.dimension.dimensionID, WorldProviderAquatic.class, false);

    @Override
    protected void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(AquaticBiomes.BIOME_AQUATIC); //TODO biome provider!
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorAquatic(this.world);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return new Vec3d(0.2980392156862745D, 0.207843137254902D, 0.3372549019607843D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    @Override
    public float getCloudHeight() {
        return super.getCloudHeight() + 100.0F; //-> 228F default
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1) {
        return 10.0F; //TODO star brightness
    }

    @Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
        Aquatic.proxy.handleLightMapColor(partialTicks, colors);
    }

    @Override
    public DimensionType getDimensionType() {
        return DIMENSION_TYPE;
    }

    @Override
    public int getMoonPhase(long worldTime) {
        //TODO moon phase
        return super.getMoonPhase(worldTime);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return true;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return super.calculateCelestialAngle(worldTime, partialTicks);
    }

    @Override
    public double getMovementFactor() {
        return 3.5D;
    }

    @Override
    public boolean shouldClientCheckLighting() {
        return true;
    }
}
