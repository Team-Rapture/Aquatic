package com.github.teamrapture.aquatic.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityWaterBubble extends Entity {

    private static final DataParameter<Integer> AIR_SUPPLY = EntityDataManager.createKey(EntityWaterBubble.class, DataSerializers.VARINT);
    private static final DataParameter<Float> SIZE = EntityDataManager.createKey(EntityWaterBubble.class, DataSerializers.FLOAT);

    public EntityWaterBubble(World world) {
        super(world);
        this.setEntityInvulnerable(true);
    }

    public EntityWaterBubble(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y, z);
    }

    public EntityWaterBubble(World world, BlockPos spawnAt) {
        this(world, spawnAt.getX() + 0.5D, spawnAt.getY(), spawnAt.getZ() + 0.5D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.isInWater() || this.ticksExisted >= 20 * 60 * 2) {
            this.setDead();
        }
        this.move(MoverType.SELF, this.posX, this.posY + 0.075D, this.posZ);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        player.setAir(player.getAir() + this.getAirSupply());
        this.setDead();
    }

    @Override
    public void entityInit() {
        this.dataManager.register(SIZE, 0.5F);
        this.dataManager.register(AIR_SUPPLY, 100);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
    }

    public int getAirSupply() {
        return this.dataManager.get(AIR_SUPPLY);
    }

    public void setAirSupply(int airSupply) {
        this.dataManager.set(AIR_SUPPLY, Math.max(airSupply, 0));
    }

    public float getSize() {
        return this.dataManager.get(SIZE);
    }

    public void setSize(float size) {
        this.dataManager.set(SIZE, MathHelper.clamp(size, 0.0F, 1.0F));
    }
}
