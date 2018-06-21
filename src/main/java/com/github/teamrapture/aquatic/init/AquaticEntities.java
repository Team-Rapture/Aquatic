package com.github.teamrapture.aquatic.init;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.entity.EntityWaterBubble;
import com.github.teamrapture.aquatic.entity.boss.EntityScylla;
import com.github.teamrapture.aquatic.entity.hostile.EntityAnglerFish;
import com.github.teamrapture.aquatic.entity.hostile.EntityShark;
import com.github.teamrapture.aquatic.entity.passive.EntityJellyFish;
import com.github.teamrapture.aquatic.util.RegistryCreate;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@RegistryCreate(value = EntityEntry.class, modid = Aquatic.MODID)
public class AquaticEntities {

    public static final EntityEntry ANGLERFISH = EntityEntryBuilder.create()
            .entity(EntityAnglerFish.class)
            .id(getEntityResource("anglerfish"), 0)
            .name("anglerfish").tracker(80, 3, false)
            .egg(956291, 256)
            .spawn(EnumCreatureType.WATER_CREATURE, AquaticConfig.dimension.anglerFish.spawnRate, AquaticConfig.dimension.anglerFish.minGroupSize, AquaticConfig.dimension.anglerFish.maxGroupSize, AquaticBiomes.BIOME_AQUATIC, Biomes.DEEP_OCEAN, Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER)
            .build();

    //TODO spawns?
    public static final EntityEntry SCYLLABOSS = EntityEntryBuilder.create()
            .entity(EntityScylla.class)
            .id(getEntityResource("scyllaboss"), 1)
            .name("scyllaboss").tracker(80, 3, false)
            .egg(956291, 256)
            .build();

    public static final EntityEntry WATER_BUBBLE = EntityEntryBuilder.create()
            .entity(EntityWaterBubble.class)
            .id(getEntityResource("water_bubble"), 2)
            .name("water_bubble").tracker(64, 1, false)
            .build();

    public static final EntityEntry JELLY_FISH = EntityEntryBuilder.create()
            .entity(EntityJellyFish.class)
            .id(getEntityResource("jellyfish"), 4)
            .name("jellyfish").tracker(80, 3, false)
            .egg(956291, 256)
            .spawn(EnumCreatureType.WATER_CREATURE, AquaticConfig.dimension.jellyFish.spawnRate, AquaticConfig.dimension.jellyFish.minGroupSize, AquaticConfig.dimension.jellyFish.maxGroupSize, AquaticBiomes.BIOME_AQUATIC, Biomes.DEEP_OCEAN, Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER)
            .build();


    public static final EntityEntry SHARK = EntityEntryBuilder.create()
            .entity(EntityShark.class)
            .id(getEntityResource("shark"), 3)
            .name("shark").tracker(80, 3, false)
            .egg(956291, 256)
            .spawn(EnumCreatureType.WATER_CREATURE, AquaticConfig.dimension.shark.spawnRate, AquaticConfig.dimension.shark.minGroupSize, AquaticConfig.dimension.shark.maxGroupSize, AquaticBiomes.BIOME_AQUATIC, Biomes.DEEP_OCEAN, Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER)
            .build();

    static {


    }

    private static ResourceLocation getEntityResource(String entityName) {
		return new ResourceLocation(Aquatic.MODID, entityName);
	}

	public static void init() {
        //EntityRegistry.addSpawn(EntitySquid.class, );

        EntitySpawnPlacementRegistry.setPlacementType(EntityAnglerFish.class, SpawnPlacementType.IN_WATER);
        // LootTableList.register(EntityAnglerFish.LOOT_);

        EntitySpawnPlacementRegistry.setPlacementType(EntityShark.class, SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.setPlacementType(EntityJellyFish.class, SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.setPlacementType(EntityScylla.class, SpawnPlacementType.IN_WATER);
        // LootTableList.register(EntityScylla.LOOT_);
    }

}
