package com.github.teamrapture.aquatic.world.biome;

import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.entity.hostile.EntityAnglerFish;
import com.github.teamrapture.aquatic.entity.hostile.EntityShark;
import com.google.common.collect.Lists;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

import java.util.List;

public class BiomeAquatic extends Biome {

    public BiomeAquatic(BiomeProperties properties) {
        super(properties);
        this.addSpawnablesList();

    }

    public void addSpawnablesList() {
        this.spawnableCreatureList.clear();
        this.modSpawnableLists.put(EnumCreatureType.WATER_CREATURE, Lists.newArrayList(
                new Biome.SpawnListEntry(EntityAnglerFish.class, AquaticConfig.dimension.anglerFish.spawnRate, AquaticConfig.dimension.anglerFish.minGroupSize, AquaticConfig.dimension.anglerFish.maxGroupSize),
                new Biome.SpawnListEntry(EntityShark.class, AquaticConfig.dimension.shark.spawnRate, AquaticConfig.dimension.shark.minGroupSize, AquaticConfig.dimension.shark.maxGroupSize),
                new Biome.SpawnListEntry(EntityGuardian.class, AquaticConfig.dimension.guardian.spawnRate, AquaticConfig.dimension.guardian.minGroupSize, AquaticConfig.dimension.guardian.maxGroupSize),
                new Biome.SpawnListEntry(EntitySquid.class, AquaticConfig.dimension.squid.spawnRate, AquaticConfig.dimension.squid.minGroupSize, AquaticConfig.dimension.squid.maxGroupSize)
                //TODO add generic fishes to spawn list
        ));
    }

    @Override
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
        if (!this.modSpawnableLists.containsKey(creatureType))
            this.modSpawnableLists.put(creatureType, Lists.newArrayList());

        return this.modSpawnableLists.get(creatureType);
    }

    @Override
    public BiomeDecorator createBiomeDecorator() {
        return new BiomeDecoratorAquatic();
    }

    @Override
    public Class<? extends Biome> getBiomeClass() {
        return BiomeAquatic.class;
    }
}
