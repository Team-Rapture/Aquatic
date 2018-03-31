package com.github.rapture.aquatic.item.armor;

import com.github.rapture.aquatic.Aquatic;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;

import javax.annotation.Nullable;

public class HeavyIronBoots extends ItemArmor {
    public static final ArmorMaterial heavyIronBoots = EnumHelper.addArmorMaterial("heavy_iron_boots", "aquatic:heavy_iron_boots", 25, new int[]{0, 0, 0, 4}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5F);

    public HeavyIronBoots() {
        super(heavyIronBoots, 0, EntityEquipmentSlot.FEET);
        this.setUnlocalizedName("heavy_iron_boots");
        this.setRegistryName("heavy_iron_boots");
        this.setCreativeTab(Aquatic.CREATIVE_TAB);

    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.capabilities.isCreativeMode) return;
        if (world.getBlockState(player.getPosition().down(1)).getMaterial() == Material.WATER) {
            world.spawnParticle(EnumParticleTypes.WATER_DROP, false, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 0, 2, 0);



            player.motionY -= 0.04F;
        } else {


            for (Entity e : world.getEntitiesWithinAABB(EntityLiving.class, player.getEntityBoundingBox().expand(0, 8, 0))) {
                if (player.collidedVertically) {
                    e.attackEntityFrom(DamageSource.ANVIL, 2);
                }

            }
        }

        super.onArmorTick(world, player, itemStack);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }

}