package com.github.rapture.aquatic.item.armor;

import com.github.rapture.aquatic.Aquatic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ScubaSuit extends ItemArmor {
    public static final ArmorMaterial scuba_suit = EnumHelper.addArmorMaterial("scuba_suit", "scuba_suit", 5, new int[]{1, 2, 3, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public EntityEquipmentSlot slot;

    public ScubaSuit(EntityEquipmentSlot slot, String name) {
        super(scuba_suit, 0, slot);
        this.slot = slot;
        this.setUnlocalizedName("scuba_suit"+ slot);
        this.setRegistryName(name);
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
    }


    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
    }
}
