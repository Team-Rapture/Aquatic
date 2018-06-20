package com.github.teamrapture.aquatic.init;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.util.RegistryCreate;
import com.github.teamrapture.aquatic.world.biome.BiomeAquatic;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

@RegistryCreate(value = Biome.class, modid = Aquatic.MODID)
public class AquaticBiomes {

    public static final Biome BIOME_AQUATIC = new BiomeAquatic(new Biome.BiomeProperties("Aquatic Ocean").setRainDisabled()).setRegistryName(Aquatic.MODID, "biome_aquatic_ocean");

    static {
        //add biome types for each biome based on the description in BIomeDictionary, this also improves modded mob spawn compatibility

        BiomeDictionary.addTypes(AquaticBiomes.BIOME_AQUATIC,
                BiomeDictionary.Type.OCEAN, //WATER is automatically set
                BiomeDictionary.Type.WET,
                BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.RARE
        );
    }
}
