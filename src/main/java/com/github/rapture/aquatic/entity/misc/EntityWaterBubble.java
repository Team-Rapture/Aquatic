package com.github.rapture.aquatic.entity.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityWaterBubble extends Entity {

    public int deathCounter = 0;

    public EntityWaterBubble(World world) {
        super(world);
    }

    public EntityWaterBubble(World world, BlockPos spawnAt) {
        super(world);
        this.posX = spawnAt.getX();
        this.posY = spawnAt.getY();
        this.posZ = spawnAt.getZ();
        this.setEntityInvulnerable(true);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        deathCounter++;
        if (deathCounter >= 20 * 10) {
            this.setDead();
            deathCounter = 0;
        }
    }

    @Override
    public void entityInit() {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
    }
}
