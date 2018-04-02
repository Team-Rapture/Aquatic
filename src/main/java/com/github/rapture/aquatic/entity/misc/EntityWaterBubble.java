package com.github.rapture.aquatic.entity.misc;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityWaterBubble extends Entity {

    public BlockPos endPos = null;
    public int deathCounter = 0;

    public EntityWaterBubble(World world) {
        super(world);
    }

    public EntityWaterBubble(World world, BlockPos spawnAt, BlockPos moveTo) {
        super(world);
        this.posX = spawnAt.getX();
        this.posY = spawnAt.getY();
        this.posZ = spawnAt.getZ();
        endPos = moveTo;
        this.setEntityInvulnerable(true);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        deathCounter++;
        if (deathCounter >= 20 * 20) {
            this.setDead();
            deathCounter = 0;
        }
    }

    @Override
    public void entityInit() {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        if(nbt.hasKey("x")) {
            endPos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        if(endPos != null) {
            nbt.setInteger("x", endPos.getX());
            nbt.setInteger("y", endPos.getY());
            nbt.setInteger("z", endPos.getZ());
        }
    }
}
