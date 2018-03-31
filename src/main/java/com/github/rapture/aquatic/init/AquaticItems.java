package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.item.armor.HeavyIronBoots;
import com.github.rapture.aquatic.util.RegistryCreate;
import net.minecraft.item.Item;

@RegistryCreate(value = Item.class, modid = Aquatic.MODID)
public class AquaticItems {

    //register items by just creating public static final fields
    public static final Item heavy_iron_boots = new HeavyIronBoots();

}
