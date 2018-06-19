package com.github.teamrapture.aquatic.item.armor;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.util.NameUtil;
import com.github.rapture.aquatic.util.ParticleUtils;
import com.github.teamrapture.aquatic.util.NameUtil;
import com.github.teamrapture.aquatic.util.ParticleUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO Fix Bubble Rendering to Follow Player As They Sink (Bubbles Vertical Line)
public class HeavyIronBoots extends ItemArmor {
    public static final ArmorMaterial heavyIronBoots = EnumHelper.addArmorMaterial("heavy_iron_boots", "aquatic:heavy_iron_boots", 25, new int[]{0, 0, 0, 4}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5F);

    public HeavyIronBoots() {
        super(heavyIronBoots, 0, EntityEquipmentSlot.FEET);
        NameUtil.name(this, "heavy_iron_boots");
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.isInWater()) {
            if (player.capabilities.isFlying) return;
            if (world.getBlockState(player.getPosition().down(1)).getMaterial() == Material.WATER && !player.onGround) {
                ParticleUtils.spawnParticles(player, EnumParticleTypes.WATER_BUBBLE, (int) Math.round(Math.random() * 4), player.getPositionVector().x, player.getPositionVector().y, player.getPositionVector().z, (Math.random() - 0.5) * 0.8, 0.5, (Math.random() - 0.5) * 0.8, 0);
                player.motionY -= 0.08F;
            }

            player.stepHeight = 1.0f;
        } else {
            for (Entity e : world.getEntitiesWithinAABB(EntityLiving.class, player.getEntityBoundingBox().expand(0, 8, 0))) {
                if (player.collidedVertically) {
                    e.attackEntityFrom(DamageSource.ANVIL, 2);
                }
            }
            player.stepHeight = 0.6f;
        }

        super.onArmorTick(world, player, itemStack);
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!stack.isItemEnchanted()) {
            stack.addEnchantment(Enchantments.DEPTH_STRIDER, 3);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

}
