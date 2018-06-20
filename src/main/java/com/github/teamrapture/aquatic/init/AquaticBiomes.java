package com.github.teamrapture.aquatic.init;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.util.RegistryCreate;
import com.github.teamrapture.aquatic.world.biome.BiomeAquatic;
import net.minecraft.world.biome.Biome;

@RegistryCreate(value = Biome.class, modid = Aquatic.MODID)
public class AquaticBiomes {

    public static final Biome BIOME_AQUATIC = new BiomeAquatic(new Biome.BiomeProperties("Aquatic Ocean").setRainDisabled()).setRegistryName(Aquatic.MODID, "biome_aquatic_ocean");
}
