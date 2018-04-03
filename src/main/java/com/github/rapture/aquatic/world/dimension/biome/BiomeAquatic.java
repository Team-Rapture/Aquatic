package com.github.rapture.aquatic.world.dimension.biome;

import com.github.rapture.aquatic.entity.hostile.EntityAnglerFish;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.biome.Biome;

public class BiomeAquatic extends Biome {

    public BiomeAquatic(BiomeProperties properties) {
        super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityAnglerFish.class, 100, 4, 4));
        this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntitySquid.class, 50, 4, 4));
        this.decorator = new BiomeAquaticDecorator();
    }
}
