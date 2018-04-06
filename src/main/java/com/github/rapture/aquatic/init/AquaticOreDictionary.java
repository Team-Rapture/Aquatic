package com.github.rapture.aquatic.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class AquaticOreDictionary {
	
	
	
	public static void initOreDic() {
	OreDictionary.registerOre("oreIron", new ItemStack(AquaticBlocks.IRON_ORE_DEPOSIT));
	OreDictionary.registerOre("oreGold", new ItemStack(AquaticBlocks.GOLD_ORE_DEPOSIT));
	OreDictionary.registerOre("oreEmerald", new ItemStack(AquaticBlocks.EMERALD_ORE_DEPOSIT));
	OreDictionary.registerOre("oreLapis", new ItemStack(AquaticBlocks.LAPIZ_ORE_DEPOSIT));
	OreDictionary.registerOre("oreRedstone", new ItemStack(AquaticBlocks.REDSTONE_ORE_DEPOSIT));
	OreDictionary.registerOre("oreQuartz", new ItemStack(AquaticBlocks.QUARTZ_ORE_DEPOSIT));
	OreDictionary.registerOre("oreDiamond", new ItemStack(AquaticBlocks.DIAMOND_ORE_DEPOSIT));
	OreDictionary.registerOre("oreCoal", new ItemStack(AquaticBlocks.COAL_ORE_DEPOSIT));
	OreDictionary.registerOre("stone", new ItemStack(AquaticBlocks.AQUATIC_STONE));
	
	
	}
	
	
}
