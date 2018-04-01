package com.github.rapture.aquatic.entity.hostile;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAnglerFish extends EntityMob {
	protected EntityAIWander wander;
	private EntityLivingBase targetedEntity;
	private static final DataParameter<Boolean> MOVING = EntityDataManager.<Boolean>createKey(EntityAnglerFish.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager
			.<Integer>createKey(EntityAnglerFish.class, DataSerializers.VARINT);

	public EntityAnglerFish(World worldIn) {
		super(worldIn);
		this.setPathPriority(PathNodeType.WATER, 8.0F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
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
	protected PathNavigate createNavigator(World worldIn) {
		return new PathNavigateSwimmer(this, worldIn);
	}
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(MOVING, Boolean.valueOf(false));
		this.dataManager.register(TARGET_ENTITY, Integer.valueOf(0));
	}

	public boolean isMoving() {
		return ((Boolean) this.dataManager.get(MOVING)).booleanValue();
	}

	private void setMoving(boolean moving) {
		this.dataManager.set(MOVING, Boolean.valueOf(moving));
	}

	private void setTargetedEntity(int entityId) {
		this.dataManager.set(TARGET_ENTITY, Integer.valueOf(entityId));
	}
	
	public boolean hasTargetedEntity() {
		return ((Integer) this.dataManager.get(TARGET_ENTITY)).intValue() != 0;
	}

	@Nullable
	public EntityLivingBase getTargetedEntity() {
		if (!this.hasTargetedEntity()) {
			return null;
		} else if (this.world.isRemote) {
			if (this.targetedEntity != null) {
				return this.targetedEntity;
			} else {
				Entity entity = this.world.getEntityByID(((Integer) this.dataManager.get(TARGET_ENTITY)).intValue());

				if (entity instanceof EntityLivingBase) {
					this.targetedEntity = (EntityLivingBase) entity;
					return this.targetedEntity;
				} else {
					return null;
				}
			}
		} else {
			return this.getAttackTarget();
		}
	}
	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);

		if (TARGET_ENTITY.equals(key)) {

			this.targetedEntity = null;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness() {
		return 1.0F;
	}
	@Override
    public float getBlockPathWeight(BlockPos pos)
    {
        return this.world.getBlockState(pos).getMaterial() == Material.WATER ? 10.0F + this.world.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
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
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return true;
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
