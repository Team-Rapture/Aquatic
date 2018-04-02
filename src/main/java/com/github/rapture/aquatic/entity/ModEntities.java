package com.github.rapture.aquatic.entity;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.render.entity.RenderEntityBubble;
import com.github.rapture.aquatic.client.render.entity.boss.RenderScylla;
import com.github.rapture.aquatic.client.render.entity.hostile.RenderAnglerFish;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.entity.boss.EntityScylla;
import com.github.rapture.aquatic.entity.hostile.EntityAnglerFish;

import com.github.rapture.aquatic.entity.misc.EntityWaterBubble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

	public static void init() {
		EntityRegistry.registerModEntity(getEntityResource("anglerfish"), EntityAnglerFish.class, "anglerfish", 0,
				Aquatic.instance, 80, 3, false, 956291, 00400);
		EntityRegistry.addSpawn(EntityAnglerFish.class, AquaticConfig.angler_spawn_rate,
				AquaticConfig.angler_MIN_spawn_rate, AquaticConfig.angler_MAX_spawn_rate,
				EnumCreatureType.WATER_CREATURE, Biomes.DEEP_OCEAN, Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER,
				Biomes.FROZEN_RIVER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityAnglerFish.class, SpawnPlacementType.IN_WATER);
		// LootTableList.register(EntityAnglerFish.LOOT_);
		EntityRegistry.registerModEntity(getEntityResource("scyllaboss"), EntityScylla.class, "scyllaboss", 1,
				Aquatic.instance, 80, 3, false, 956291, 00400);
		EntityRegistry.addSpawn(EntityScylla.class, AquaticConfig.angler_spawn_rate,
				AquaticConfig.angler_MIN_spawn_rate, AquaticConfig.angler_MAX_spawn_rate,
				EnumCreatureType.WATER_CREATURE, Biomes.DEEP_OCEAN, Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER,
				Biomes.FROZEN_RIVER);
		EntitySpawnPlacementRegistry.setPlacementType(EntityScylla.class, SpawnPlacementType.IN_WATER);
		// LootTableList.register(EntityScylla.LOOT_);

		createEntity(EntityWaterBubble.class, "water_bubble", 2);
	}

	public static void renderEntities() {
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();

		rm.entityRenderMap.put(EntityAnglerFish.class, new RenderAnglerFish(rm));
		rm.entityRenderMap.put(EntityScylla.class, new RenderScylla(rm));
		rm.entityRenderMap.put(EntityWaterBubble.class, new RenderEntityBubble(rm));
	}

	private static ResourceLocation getEntityResource(String entityName) {
		return new ResourceLocation(Aquatic.MODID, entityName);
	}

	public static void createEntity(Class entityClass, String entityName, int ID) {
		EntityRegistry.registerModEntity(new ResourceLocation(Aquatic.MODID, entityName), entityClass, entityName, ID, Aquatic.instance, 64, 1, false);
	}
}
