package com.github.teamrapture.aquatic.item.armor;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.api.capability.oxygen.CapabilityOxygen;
import com.github.teamrapture.aquatic.init.AquaticItems;
import com.github.teamrapture.aquatic.api.capability.oxygen.OxygenStorage;
import com.github.teamrapture.aquatic.util.NameUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ScubaSuit extends ItemArmor {

    public static final ArmorMaterial scuba_suit = EnumHelper.addArmorMaterial("scuba_suit", "aquatic:scuba_suit", 5, new int[]{1, 2, 3, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public EntityEquipmentSlot slot;
    public OxygenStorage oxygenStorage = new OxygenStorage(10000);

    public ScubaSuit(EntityEquipmentSlot slot, String name) {
        super(scuba_suit, 0, slot);
        this.slot = slot;
        NameUtil.name(this, name);
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (hasFullArmor(player)) {
            sendPlayerAir(player);
        }

        super.onArmorTick(world, player, itemStack);
    }

    public boolean hasFullArmor(EntityPlayer player) {
        return player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == AquaticItems.SCUBA_HELEMT
                && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == AquaticItems.SCUBA_CHEST
                && player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == AquaticItems.SCUBA_LEGGINGS
                && (player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == AquaticItems.SCUBA_FEET
                || player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == AquaticItems.HEAVY_IRON_BOOTS);

    }

    public void sendPlayerAir(EntityPlayer player) {
        if (player.getAir() < 300) {
            if (oxygenStorage.canSendOxygen(300)) {
                oxygenStorage.drainOxygen(300);
                player.setAir(player.getAir() + 30);
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ICapabilityProvider() {
            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityOxygen.OXYGEN;
            }

            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
                return capability == CapabilityOxygen.OXYGEN ? CapabilityOxygen.OXYGEN.cast(oxygenStorage) : null;
            }
        };
    }
}


