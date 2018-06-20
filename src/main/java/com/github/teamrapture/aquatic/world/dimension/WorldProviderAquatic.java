package com.github.teamrapture.aquatic.world.dimension;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.init.AquaticBiomes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAquatic extends WorldProvider {

    @Override
    protected void init() {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(AquaticBiomes.BIOME_AQUATIC);
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
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1) {
        return 10.0F;
    }

    @Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {
        Aquatic.proxy.handleLightMapColor(partialTicks, colors);
    }

    @Override
    public DimensionType getDimensionType() {
        return DimensionAquatic.dimension;
    }

    @Override
    public boolean isSurfaceWorld() {
        return super.isSurfaceWorld();
    }

    @Override
    public int getMoonPhase(long worldTime) {
        return (int) (worldTime / 24000L % 8L + 8L) % 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return true;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        int i = (int) (worldTime % 24000L);
        float f = ((float) i + partialTicks) / 24000.0F - 0.25F;

        if (f < 0.0F) {
            ++f;
        }

        if (f > 1.0F) {
            --f;
        }

        float f1 = 1.0F - (float) ((Math.cos((double) f * Math.PI) + 1.0D) / 2.0D);
        f = f + (f1 - f) / 3.0F;
        return f;
    }
}
