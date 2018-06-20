package com.github.teamrapture.aquatic.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class AquaticRecipes {

    public static class OreDict {

        public static void initOreDictionary() {
            OreDictionary.registerOre("oreIron", new ItemStack(AquaticBlocks.IRON_ORE_DEPOSIT));
            OreDictionary.registerOre("oreGold", new ItemStack(AquaticBlocks.GOLD_ORE_DEPOSIT));
            OreDictionary.registerOre("oreEmerald", new ItemStack(AquaticBlocks.EMERALD_ORE_DEPOSIT));
            OreDictionary.registerOre("oreLapis", new ItemStack(AquaticBlocks.LAPIZ_ORE_DEPOSIT));
            OreDictionary.registerOre("oreRedstone", new ItemStack(AquaticBlocks.REDSTONE_ORE_DEPOSIT));
            OreDictionary.registerOre("oreQuartz", new ItemStack(AquaticBlocks.QUARTZ_ORE_DEPOSIT));
            OreDictionary.registerOre("oreDiamond", new ItemStack(AquaticBlocks.DIAMOND_ORE_DEPOSIT));
            OreDictionary.registerOre("oreCoal", new ItemStack(AquaticBlocks.COAL_ORE_DEPOSIT));
            OreDictionary.registerOre("stone", new ItemStack(AquaticBlocks.AQUATIC_STONE));
            OreDictionary.registerOre("stoneAquatic", new ItemStack(AquaticBlocks.AQUATIC_STONE));
        }
    }

    public static class Smelting {

        public static void registerRecipes() {
            GameRegistry.addSmelting(AquaticItems.RAW_ANGLER_FISH, new ItemStack(AquaticItems.COOKED_ANGLER_FISH), 0.1F);
            GameRegistry.addSmelting(new ItemStack(AquaticBlocks.IRON_ORE_DEPOSIT), new ItemStack(Items.IRON_INGOT), 100.1F);
            GameRegistry.addSmelting(new ItemStack(AquaticBlocks.GOLD_ORE_DEPOSIT), new ItemStack(Items.GOLD_INGOT), 100.1F);
            GameRegistry.addSmelting(new ItemStack(AquaticBlocks.COAL_ORE_DEPOSIT), new ItemStack(Items.COAL), 100.1F);
        }
    }

}
