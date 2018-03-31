package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.item.ItemAquaticCharm;
import com.github.rapture.aquatic.item.ItemTesting;
import com.github.rapture.aquatic.util.RegistryCreate;
import com.typesafe.config.ConfigRenderOptions;

import net.minecraft.item.Item;

@RegistryCreate(value = Item.class, modid = Aquatic.MODID)
public class AquaticItems {

	public static final Item aquatic_charm = new ItemAquaticCharm("aquatic_charm", 1, 0, AquaticConfig.dimensionID);
	public static final Item test = new ItemTesting("test");
	
}
