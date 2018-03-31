package com.github.rapture.aquatic.dimensions.provider;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.dimensions.DimensionAquatic;
import com.github.rapture.aquatic.dimensions.generator.ChunkGeneratorAquatic;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderAquatic extends WorldProvider
{

	@Override
    protected void init()
    {
        this.hasSkyLight = true;
        this.biomeProvider = new BiomeProviderSingle(Biomes.DEEP_OCEAN);
    }

	@Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorAquatic(this.world);
    }

	@Override
    public boolean canRespawnHere()
    {
        return false;
    }

	@Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
    	return 0.5F;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks)
    {
        return new Vec3d(0.2980392156862745D, 0.207843137254902D, 0.3372549019607843D);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return false;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
		return 10.0F;
    }

	@Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors)
    {
    	Aquatic.proxy.handleLightMapColor(partialTicks, colors);
    }

	@Override
	public DimensionType getDimensionType() 
	{
		return DimensionAquatic.dimension;
	}
}
