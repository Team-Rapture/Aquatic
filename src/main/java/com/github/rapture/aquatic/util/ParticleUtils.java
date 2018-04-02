package com.github.rapture.aquatic.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ParticleUtils {
    /**
     * Spawns particles
     */
    public static void spawnParticles(Entity entity, EnumParticleTypes particleType, int numberOfParticles, double xPos, double yPos, double zPos, double xOffset, double yOffset, double zOffset, double speed) {
        spawnParticles(entity.world, particleType, numberOfParticles, xPos, yPos, zPos, xOffset, yOffset, zOffset, speed);
    }

    /**
     * Spawns particles
     */
    public static void spawnParticles(World world, EnumParticleTypes particleType, int numberOfParticles, double xPos, double yPos, double zPos, double xOffset, double yOffset, double zOffset, double speed) {
        if (world instanceof WorldServer) {
            ((WorldServer) world).spawnParticle(particleType, xPos, yPos, zPos, numberOfParticles, xOffset, yOffset, zOffset, speed);
        } else if (world.isRemote) for (int i = 0; i < numberOfParticles; i++)
            world.spawnParticle(particleType, particleType.getShouldIgnoreRange(), xPos + world.rand.nextDouble() * xOffset, yPos + world.rand.nextDouble() * yOffset, zPos + world.rand.nextDouble() * zOffset, world.rand.nextDouble() * speed, world.rand.nextDouble() * speed, world.rand.nextDouble() * speed);
    }
}
