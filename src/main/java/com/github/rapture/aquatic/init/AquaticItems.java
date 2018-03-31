package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
<<<<<<< HEAD
import com.github.rapture.aquatic.item.armor.HeavyIronBoots;
=======
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.item.ItemAquaticCharm;
import com.github.rapture.aquatic.item.ItemTesting;
>>>>>>> origin/master
import com.github.rapture.aquatic.util.RegistryCreate;
import com.typesafe.config.ConfigRenderOptions;

import net.minecraft.item.Item;

@RegistryCreate(value = Item.class, modid = Aquatic.MODID)
public class AquaticItems {

<<<<<<< HEAD
    //register items by just creating public static final fields
    public static final Item heavy_iron_boots = new HeavyIronBoots();

=======
	public static final Item aquatic_charm = new ItemAquaticCharm("aquatic_charm", 1, 0, AquaticConfig.dimensionID);
	public static final Item test = new ItemTesting("test");
	
>>>>>>> origin/master
}
