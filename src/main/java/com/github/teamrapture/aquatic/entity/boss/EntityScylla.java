package com.github.teamrapture.aquatic.entity.boss;

import com.github.teamrapture.aquatic.entity.hostile.EntityAnglerFish;
import com.github.teamrapture.aquatic.init.AquaticItems;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
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

import javax.annotation.Nullable;

public class EntityScylla extends EntityWaterMob implements IMob {
    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(),
            BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
    private EntityLivingBase targetedEntity; //TODO set target via AI task?
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
        //EntityAIMoveTowardsRestriction entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
        //this.tasks.addTask(4, new EntityAIAttackMelee(this, 1, true));
        //this.tasks.addTask(5, entityaimovetowardsrestriction);

        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        //entityaimovetowardsrestriction.setMutexBits(3);
        //this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, true));

    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
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

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        //FIXME entity attributes need to be added before they can be set
        //this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        //this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
        //this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    private void initFishSpawn() {
        for (int i = 0; i < this.rand.nextInt(19); i++) {
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
                if(entity instanceof EntityCreature) entity.setAttackTarget(this.targetedEntity);
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

    @SideOnly(Side.CLIENT) //TODO use this method or delete it
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
        super.updateAITasks();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Nullable
    @Override
    protected Item getDropItem() {
        return AquaticItems.SCYLLA_SKULL;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        if(wasRecentlyHit) {
            EntityItem entityitem = this.dropItem(this.getDropItem(), 1);
            if (entityitem != null) {
                entityitem.setNoDespawn();
            }
        }
    }

    @Override
    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }
}
