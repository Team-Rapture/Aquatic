package com.github.rapture.aquatic.entity.passive;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityJellyFish extends EntityWaterMob {
	public float jellyPitch;
	public float prevjellyPitch;
	public float jellyYaw;
	public float prevjellyYaw;
	/**
	 * appears to be rotation in radians; we already have pitch & yaw, so this
	 * completes the triumvirate.
	 */
	public float jellyRotation;
	/** previous jellyRotation in radians */
	public float prevjellyRotation;
	/** angle of the tentacles in radians */
	public float tentacleAngle;
	/** the last calculated angle of the tentacles in radians */
	public float lastTentacleAngle;
	private float randomMotionSpeed;
	/** change in jellyRotation in radians. */
	private float rotationVelocity;
	private float rotateSpeed;
	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;

	public EntityJellyFish(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.8F);
        this.rand.setSeed((long)(1 + this.getEntityId()));
        this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
    }

	
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityJellyFish.AIMoveRandom(this));
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
	}

	public float getEyeHeight() {
		return this.height * 0.5F;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_SQUID_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_SQUID_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_SQUID_DEATH;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 0.4F;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk
	 * on. used for spiders and wolves to prevent them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}
/*
	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_SQUID;
	}*/

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 * For example, zombies and skeletons use this to react to sunlight and start to
	 * burn.
	 */
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.prevjellyPitch = this.jellyPitch;
		this.prevjellyYaw = this.jellyYaw;
		this.prevjellyRotation = this.jellyRotation;
		this.lastTentacleAngle = this.tentacleAngle;
		this.jellyRotation += this.rotationVelocity;

		if ((double) this.jellyRotation > (Math.PI * 2D)) {
			if (this.world.isRemote) {
				this.jellyRotation = ((float) Math.PI * 2F);
			} else {
				this.jellyRotation = (float) ((double) this.jellyRotation - (Math.PI * 2D));

				if (this.rand.nextInt(10) == 0) {
					this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
				}

				this.world.setEntityState(this, (byte) 19);
			}
		}

		if (this.inWater) {
			if (this.jellyRotation < (float) Math.PI) {
				float f = this.jellyRotation / (float) Math.PI;
				this.tentacleAngle = MathHelper.sin(f * f * (float) Math.PI) * (float) Math.PI * 0.25F;

				if ((double) f > 0.75D) {
					this.randomMotionSpeed = 1.0F;
					this.rotateSpeed = 1.0F;
				} else {
					this.rotateSpeed *= 0.8F;
				}
			} else {
				this.tentacleAngle = 0.0F;
				this.randomMotionSpeed *= 0.9F;
				this.rotateSpeed *= 0.99F;
			}

			if (!this.world.isRemote) {
				this.motionX = (double) (this.randomMotionVecX * this.randomMotionSpeed);
				this.motionY = (double) (this.randomMotionVecY * this.randomMotionSpeed);
				this.motionZ = (double) (this.randomMotionVecZ * this.randomMotionSpeed);
			}

			float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.renderYawOffset += (-((float) MathHelper.atan2(this.motionX, this.motionZ)) * (180F / (float) Math.PI)
					- this.renderYawOffset) * 0.1F;
			this.rotationYaw = this.renderYawOffset;
			this.jellyYaw = (float) ((double) this.jellyYaw + Math.PI * (double) this.rotateSpeed * 1.5D);
			this.jellyPitch += (-((float) MathHelper.atan2((double) f1, this.motionY)) * (180F / (float) Math.PI)
					- this.jellyPitch) * 0.1F;
		} else {
			this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.jellyRotation)) * (float) Math.PI * 0.25F;

			if (!this.world.isRemote) {
				this.motionX = 0.0D;
				this.motionZ = 0.0D;

				if (this.isPotionActive(MobEffects.LEVITATION)) {
					this.motionY += 0.05D
							* (double) (this.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1)
							- this.motionY;
				} else if (!this.hasNoGravity()) {
					this.motionY -= 0.08D;
				}

				this.motionY *= 0.9800000190734863D;
			}

			this.jellyPitch = (float) ((double) this.jellyPitch + (double) (-90.0F - this.jellyPitch) * 0.02D);
		}
	}

	public void travel(float strafe, float vertical, float forward) {
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	public boolean getCanSpawnHere() {
		return this.posY > 45.0D && this.posY < (double) this.world.getSeaLevel() && super.getCanSpawnHere();
	}

	/**
	 * Handler for {@link World#setEntityState}
	 */
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 19) {
			this.jellyRotation = 0.0F;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
		this.randomMotionVecX = randomMotionVecXIn;
		this.randomMotionVecY = randomMotionVecYIn;
		this.randomMotionVecZ = randomMotionVecZIn;
	}

	public boolean hasMovementVector() {
		return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
	}

	static class AIMoveRandom extends EntityAIBase {
		private final EntityJellyFish jelly;

		public AIMoveRandom(EntityJellyFish p_i45859_1_) {
			this.jelly = p_i45859_1_;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {
			int i = this.jelly.getIdleTime();

			if (i > 100) {
				this.jelly.setMovementVector(0.0F, 0.0F, 0.0F);
			} else if (this.jelly.getRNG().nextInt(50) == 0 || !this.jelly.inWater || !this.jelly.hasMovementVector()) {
				float f = this.jelly.getRNG().nextFloat() * ((float) Math.PI * 2F);
				float f1 = MathHelper.cos(f) * 0.2F;
				float f2 = -0.1F + this.jelly.getRNG().nextFloat() * 0.2F;
				float f3 = MathHelper.sin(f) * 0.2F;
				this.jelly.setMovementVector(f1, f2, f3);
			}
		}
	}
}
