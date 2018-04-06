package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.item.ItemAquaticCharm;
import com.github.rapture.aquatic.item.ItemBase;
import com.github.rapture.aquatic.item.armor.HeavyIronBoots;
import com.github.rapture.aquatic.item.armor.ScubaSuit;
import com.github.rapture.aquatic.item.food.BaseFoodItem;
import com.github.rapture.aquatic.item.tool.DivingKnife;
import com.github.rapture.aquatic.item.tool.HydroDrill;
import com.github.rapture.aquatic.util.RegistryCreate;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

@RegistryCreate(value = Item.class, modid = Aquatic.MODID)
public class AquaticItems {

    public static final Item HEAVY_IRON_BOOTS = new HeavyIronBoots();
    public static final Item SCUBA_HELEMT = new ScubaSuit(EntityEquipmentSlot.HEAD, "scuba_helmet");
    public static final Item SCUBA_CHEST = new ScubaSuit(EntityEquipmentSlot.CHEST, "scuba_chestplate");
    public static final Item SCUBA_LEGGINGS = new ScubaSuit(EntityEquipmentSlot.LEGS, "scuba_leggings");
    public static final Item SCUBA_FEET = new ScubaSuit(EntityEquipmentSlot.FEET, "scuba_fins");
    public static final Item HYDRO_DRILL = new HydroDrill();
    public static final Item SCYLLA_SKULL = new ItemBase("scylla_skull");
    public static final Item DIVING_KNIFE = new DivingKnife("diving_knife", ToolMaterial.IRON);
    public static final Item ORGANIC_MATTER = new ItemBase("organic_matter");
public static final Item AQUATIC_CHARM = new ItemAquaticCharm("aquatic_charm_item");
    public static final Item ANGLER_LIGHT = new ItemBase("angler_light");
    public static final Item SHARK_TOOTH = new ItemBase("shark_tooth");
    // Foods
    
    public static final Item RAW_SCYLLA_MEAT = new BaseFoodItem("raw_scylla_meat", 10, true);
    public static final Item COOKED_SCYLLA_MEAT = new BaseFoodItem("cooked_scylla_meat", 10, true);
    public static final Item RAW_FIN = new BaseFoodItem("raw_shark_fin", 10, false);
    public static final Item RAW_ANGLER_FISH = new BaseFoodItem("raw_angler_fish", 10, true);
    public static final Item RAW_SLICED_MEAT = new BaseFoodItem("raw_sliced_meat", 10, true);
    public static final Item SLICED_MEAT = new BaseFoodItem("cooked_sliced_meat", 10, true);
    public static final Item COOKED_ANGLER_FISH = new BaseFoodItem("cooked_angler_fish", 10, true);
    public static final Item COOKED_FIN = new BaseFoodItem("cooked_shark_fin", 10, false);
   // public static final Item SCYLLA_SKULL = new ItemBase("scylla_skull");
    
    
    
}
