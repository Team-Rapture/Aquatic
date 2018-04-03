package com.github.rapture.aquatic.world.dimension.biome;

import java.util.ArrayList;
import java.util.List;

import com.github.rapture.aquatic.entity.hostile.EntityAnglerFish;
import com.google.common.collect.Lists;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class BiomeAquatic extends Biome {

	public BiomeAquatic(BiomeProperties properties) {
		super(properties);
		this.addSpawnablesList();
	
	}

	public void addSpawnablesList() {
		ArrayList<SpawnListEntry> entityList = new ArrayList<SpawnListEntry>();

		entityList.add(new Biome.SpawnListEntry(EntitySquid.class, 7, 2, 3));
		entityList.add(new Biome.SpawnListEntry(EntityAnglerFish.class, 5, 1, 2));

		this.modSpawnableLists.put(EnumCreatureType.CREATURE, entityList);
	}
	@Override
    public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType)
    {
        if (!this.modSpawnableLists.containsKey(creatureType)) this.modSpawnableLists.put(creatureType, Lists.<SpawnListEntry>newArrayList());

        return this.modSpawnableLists.get(creatureType);
    }
	@Override
    public BiomeDecorator createBiomeDecorator()
    {
        return new BiomeAquaticDecorator();
    }
    @Override
    public Class <? extends Biome > getBiomeClass()
    {
    	return BiomeAquatic.class;
    }
}
