package com.github.teamrapture.aquatic.util;

import com.github.teamrapture.aquatic.Aquatic;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class NameUtil {

    public static void name(Block block, String name) {
        block.setTranslationKey(Aquatic.MODID + "." + name);
        block.setRegistryName(name);
    }

    public static void name(Item item, String name) {
        item.setTranslationKey(Aquatic.MODID + "." + name);
        item.setRegistryName(name);
    }
}
