package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.item.ItemAquaticCharm;
import com.github.rapture.aquatic.item.ItemTesting;
import com.github.rapture.aquatic.item.armor.HeavyIronBoots;
import com.github.rapture.aquatic.util.RegistryCreate;

import net.minecraft.item.Item;

@RegistryCreate(value = Item.class, modid = Aquatic.MODID)
public class AquaticItems {


    //register items by just creating public static final fields
    public static final Item heavy_iron_boots = new HeavyIronBoots();


	public static final Item aquatic_charm = new ItemAquaticCharm("aquatic_charm", 1, 0, AquaticConfig.dimensionID);
	
	
//	USE FOR TESTING METHODS AND STUFF!
	public static final Item test = new ItemTesting("test");

}
