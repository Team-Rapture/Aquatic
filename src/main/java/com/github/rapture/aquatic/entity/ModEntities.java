package com.github.rapture.aquatic.entity;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.render.entity.RenderEntityBubble;
import com.github.rapture.aquatic.client.render.entity.boss.RenderScylla;
import com.github.rapture.aquatic.client.render.entity.hostile.RenderAnglerFish;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.entity.boss.EntityScylla;
import com.github.rapture.aquatic.entity.hostile.EntityAnglerFish;

import com.github.rapture.aquatic.entity.misc.EntityWaterBubble;
import com.github.rapture.aquatic.util.RegistryCreate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.DebugRendererWater;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@RegistryCreate(value = EntityEntry.class, modid = Aquatic.MODID)
public class ModEntities {

    public static final EntityEntry ANGLERFISH = EntityEntryBuilder.create()
            .entity(EntityAnglerFish.class)
            .id(getEntityResource("anglerfish"), 0)
            .name("anglerfish").tracker(80, 3, false)
            .egg(956291, 256)
            .spawn(EnumCreatureType.WATER_CREATURE, AquaticConfig.angler_spawn_rate, AquaticConfig.angler_MIN_spawn_rate, AquaticConfig.angler_MAX_spawn_rate, Biomes.DEEP_OCEAN, Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER)
            .build();

    public static final EntityEntry SCYLLABOSS = EntityEntryBuilder.create()
            .entity(EntityScylla.class)
            .id(getEntityResource("scyllaboss"), 1)
            .name("scyllaboss").tracker(80, 3, false)
            .egg(956291, 256)
            .spawn(EnumCreatureType.WATER_CREATURE, AquaticConfig.angler_spawn_rate, AquaticConfig.angler_MIN_spawn_rate, AquaticConfig.angler_MAX_spawn_rate, Biomes.DEEP_OCEAN, Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER)
            .build();

    public static final EntityEntry WATER_BUBBLE = EntityEntryBuilder.create()
            .entity(EntityWaterBubble.class)
            .id(getEntityResource("water_bubble"), 2)
            .name("water_bubble").tracker(64, 1, false)
            .build();

    static {
        EntitySpawnPlacementRegistry.setPlacementType(EntityAnglerFish.class, SpawnPlacementType.IN_WATER);
        // LootTableList.register(EntityAnglerFish.LOOT_);

        EntitySpawnPlacementRegistry.setPlacementType(EntityScylla.class, SpawnPlacementType.IN_WATER);
        // LootTableList.register(EntityScylla.LOOT_);
    }

	private static ResourceLocation getEntityResource(String entityName) {
		return new ResourceLocation(Aquatic.MODID, entityName);
	}

}
