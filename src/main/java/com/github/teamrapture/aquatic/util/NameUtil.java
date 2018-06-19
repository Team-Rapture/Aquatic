package com.github.teamrapture.aquatic.util;

import com.github.rapture.aquatic.Aquatic;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class NameUtil {

    public static void name(Block block, String name) {
        block.setUnlocalizedName(Aquatic.MODID + "." + name);
        block.setRegistryName(name);
    }

    public static void name(Item item, String name) {
        item.setUnlocalizedName(Aquatic.MODID + "." + name);
        item.setRegistryName(name);
    }
}
