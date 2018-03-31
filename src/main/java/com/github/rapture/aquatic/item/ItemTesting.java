package com.github.rapture.aquatic.item;

import com.github.rapture.aquatic.dimensions.TeleporterDim;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemTesting extends ItemBase {

	public ItemTesting(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

		if (worldIn instanceof WorldServer) {

			playerIn.posY += 1;
			System.out.println("fired");

		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));

	}

}
