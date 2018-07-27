package com.github.teamrapture.aquatic.item.armor;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.api.capability.oxygen.CapabilityOxygen;
import com.github.teamrapture.aquatic.api.capability.oxygen.IOxygenProvider;
import com.github.teamrapture.aquatic.api.capability.oxygen.OxygenStorage;
import com.github.teamrapture.aquatic.init.AquaticItems;
import com.github.teamrapture.aquatic.tileentity.TileAquaNetController;
import com.github.teamrapture.aquatic.util.NameUtil;
import com.github.teamrapture.aquatic.util.capability.CapabilityProviderSerializable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nullable;

public class ScubaSuit extends ItemArmor {

    public static final ArmorMaterial scuba_suit = EnumHelper.addArmorMaterial("scuba_suit", "aquatic:scuba_suit", 5, new int[]{1, 2, 3, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public EntityEquipmentSlot slot;

    public ScubaSuit(EntityEquipmentSlot slot, String name) {
        super(scuba_suit, 0, slot);
        this.slot = slot;
        NameUtil.name(this, name);
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
    }

    private int getCurrentOxygenStored(ItemStack stack) {
        IOxygenProvider oxygen = stack.getCapability(CapabilityOxygen.OXYGEN, null);
        if(oxygen != null) return oxygen.getOxygenStored();
        return 0;
    }

    private int getMaxOxygenStored(ItemStack stack) {
        IOxygenProvider oxygen = stack.getCapability(CapabilityOxygen.OXYGEN, null);
        if(oxygen != null) return oxygen.getMaxOxygenStorage();
        return 0;
    }

    @Override
    public boolean getShareTag() { //needed to make sure the capabilities stay in sync with the client
        return true;
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        stack.setTagInfo("sync_aquatic_oxygen", new NBTTagInt(getCurrentOxygenStored(stack))); //trick MC into sending the stack capabilities to the client!
        return super.getNBTShareTag(stack);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (hasFullArmor(player)) {
            sendPlayerAir(player, itemStack);
        }
        super.onArmorTick(world, player, itemStack);
    }

    private boolean hasFullArmor(EntityPlayer player) {
        return player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == AquaticItems.SCUBA_HELEMT
                && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == AquaticItems.SCUBA_CHEST
                && player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == AquaticItems.SCUBA_LEGGINGS
                && (player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == AquaticItems.SCUBA_FEET
                || player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == AquaticItems.HEAVY_IRON_BOOTS);

    }

    private void sendPlayerAir(EntityPlayer player, ItemStack stack) {
        IOxygenProvider oxygen = stack.getCapability(CapabilityOxygen.OXYGEN, null);
        if(oxygen != null) {
            int air = player.getAir();
            if (air < 300) {
                int diff = 300 - air;
                int amount = oxygen.extractOxygen(Math.min(diff, TileAquaNetController.MAX_ARMOR_TRANSFER_AMOUNT), false);
                if(amount > 0) {
                    player.setAir(air + amount);
                }
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilityProviderSerializable<>(CapabilityOxygen.OXYGEN, null, new OxygenStorage(1000, TileAquaNetController.MAX_ARMOR_TRANSFER_AMOUNT));
    }
}


