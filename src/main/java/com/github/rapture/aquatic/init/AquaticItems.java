package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.item.ItemAquaticCharm;
import com.github.rapture.aquatic.item.ItemTesting;
import com.github.rapture.aquatic.item.armor.HeavyIronBoots;
import com.github.rapture.aquatic.item.armor.ScubaSuit;
import com.github.rapture.aquatic.util.RegistryCreate;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

@RegistryCreate(value = Item.class, modid = Aquatic.MODID)
public class AquaticItems {


    //register items by just creating public static final fields
    public static final Item HEAVY_IRON_BOOTS = new HeavyIronBoots();
    public static final Item SCUBA_HELEMT = new ScubaSuit(EntityEquipmentSlot.HEAD , "scuba_helmet");
    public static final Item SCUBA_CHEST = new ScubaSuit(EntityEquipmentSlot.HEAD , "scuba_chestplate");
    public static final Item SCUBA_LEGGINGS = new ScubaSuit(EntityEquipmentSlot.HEAD , "scuba_leggings");
    public static final Item SCUBA_FEET = new ScubaSuit(EntityEquipmentSlot.HEAD , "scuba_fins");



	public static final Item AQUATIC_CHARM = new ItemAquaticCharm("aquatic_charm_item", 1, 0, AquaticConfig.dimensionID);
	public static final Item TEST = new ItemTesting("test");


}
