package com.github.rapture.aquatic.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AquaticRecipes {
	
	
	
	@SubscribeEvent
	public static void FurnaceRegister(FMLPreInitializationEvent event) {

		FurnaceRecipes.instance().addSmelting(AquaticItems.RAW_ANGLER_FISH, new ItemStack(AquaticItems.COOKED_ANGLER_FISH), 0.1F);

		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(AquaticBlocks.IRON_ORE_DEPOSIT),
				new ItemStack(Items.IRON_INGOT), 100.1F);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(AquaticBlocks.GOLD_ORE_DEPOSIT),
				new ItemStack(Items.GOLD_INGOT), 100.1F);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(AquaticBlocks.COAL_ORE_DEPOSIT),
				new ItemStack(Items.COAL), 100.1F);

	}
}
