package com.github.teamrapture.aquatic.entity.boss;

import com.github.teamrapture.aquatic.entity.hostile.EntityAnglerFish;
import com.github.teamrapture.aquatic.init.AquaticItems;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityScylla extends EntityMob {
    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(),
            BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
    protected EntityAIWander wander;
    private EntityLivingBase targetedEntity;
    private int spawn;

    public EntityScylla(World worldIn) {
        super(worldIn);
        this.setHealth(this.getMaxHealth());
        this.setPathPriority(PathNodeType.WATER, 8.0F);
        experienceValue = 30;
        setSize(2F, 4.7F);
        this.isImmuneToFire = false;
        stepHeight = 2;
        spawn = 0;
    }

    @Override
    protected void initEntityAI() {
        EntityAIMoveTowardsRestriction entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
        this.wander = new EntityAIWander(this, 1.0D, 80);
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1, true));
        this.tasks.addTask(5, entityaimovetowardsrestriction);
        this.tasks.addTask(7, this.wander);

        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.wander.setMutexBits(3);
        entityaimovetowardsrestriction.setMutexBits(3);
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, true));

    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateSwimmer(this, worldIn);
    }

    @Override
    public float getBlockPathWeight(BlockPos pos) {
        return this.world.getBlockState(pos).getMaterial().isLiquid()
                ? 10.0F + this.world.getLightBrightness(pos) - 0.5F
                : super.getBlockPathWeight(pos);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return 15728880;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    }

    public boolean isNonBoss() {
        return false;
    }

    private void initFishSpawn() {
        for (int i = 0; i < 20 + world.rand.nextInt(19); i++) {
            EntityLiving entity = null;
            switch (world.rand.nextInt(4)) {
                case 0: {
                    entity = new EntityGuardian(world);

                    break;
                }
                case 1: {
                    entity = new EntityAnglerFish(world);

                    break;
                }
                case 2: {
                    entity = new EntitySquid(world);

                    break;
                }
                case 3: {
                    entity = new EntityElderGuardian(world);

                    break;
                }

            }
            if (entity != null) {
                float range = 6F;
                entity.setPosition(posX + 0.5 + Math.random() * range - range / 2, this.posY + 2,
                        posZ + 0.5 + Math.random() * range - range / 2);
                entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);
            }
            world.spawnEntity(entity);
        }
    }

    @Override
    public void onLivingUpdate() {

        super.updateAITasks();

        if (this.getHealth() < this.getMaxHealth() * 0.95F && this.getHealth() > 0.0F && this.spawn == 0) {
            this.world.setEntityState(this, (byte) 12);

            if (!this.world.isRemote) {
                initFishSpawn();

            }
            this.spawn = 1;
        }

        if (this.getHealth() < this.getMaxHealth() * 0.75F && this.getHealth() > 0.0F && this.spawn == 1) {
            this.world.setEntityState(this, (byte) 12);

            if (!this.world.isRemote) {
                initFishSpawn();

            }
            this.spawn = 2;
        }
        if (this.getHealth() < this.getMaxHealth() * 0.55F && this.getHealth() > 0.0F && this.spawn == 2) {
            this.world.setEntityState(this, (byte) 12);

            if (!this.world.isRemote) {
                initFishSpawn();

            }
            this.spawn = 3;
        }
        if (this.getHealth() < this.getMaxHealth() * 0.35F && this.getHealth() > 0.0F && this.spawn == 3) {
            this.world.setEntityState(this, (byte) 12);

            if (!this.world.isRemote) {
                initFishSpawn();

            }
            this.spawn = 4;
        }
        if (this.getHealth() < this.getMaxHealth() * 0.25F && this.getHealth() > 0.0F && this.spawn == 4) {
            this.world.setEntityState(this, (byte) 12);

            if (!this.world.isRemote) {
                initFishSpawn();

            }
            this.spawn = 5;
        }
        if (this.getHealth() < this.getMaxHealth() * 0.05F && this.getHealth() > 0.0F && this.spawn == 5) {
            this.world.setEntityState(this, (byte) 12);

            if (!this.world.isRemote) {
                initFishSpawn();

            }
            this.spawn = 6;
        }
        super.onLivingUpdate();
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles(EnumParticleTypes particleType) {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
                    this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width,
                    this.posY + 1.0D + (double) (this.rand.nextFloat() * this.height),
                    this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2
            );
        }
    }

    @Override
    protected void updateAITasks() {

        {
            super.updateAITasks();

            this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }

    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
        EntityItem entityitem = this.dropItem(AquaticItems.SCYLLA_SKULL, 1);

        if (entityitem != null) {
            entityitem.setNoDespawn();
        }
    }

    /**
     * Sets the custom name tag for this entity
     */
    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    // WATER MOB CODE
    // ---------------------------------------------------------------------------//
    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    @Override
    public boolean getCanSpawnHere() {
        return true;
    }

    /**
     * Checks that the entity is not colliding with any blocks / liquids
     */
    @Override
    public boolean isNotColliding() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    @Override
    public int getTalkInterval() {
        return 120;
    }

    /**
     * Get the experience points the entity currently has.
     */
    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return 1 + this.world.rand.nextInt(3);
    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater()) {
            --i;
            this.setAir(i);

            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAir(300);
        }
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }
}
