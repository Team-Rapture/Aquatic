package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.block.AquaNetController;
import com.github.rapture.aquatic.block.AquaNode;
import com.github.rapture.aquatic.block.BlockAquaCharm;
import com.github.rapture.aquatic.block.plants.BlockCoralReef;
import com.github.rapture.aquatic.block.plants.BlockPistia;
import com.github.rapture.aquatic.util.RegistryCreate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@RegistryCreate(value = Block.class, modid = Aquatic.MODID)
public class AquaticBlocks {

	// register blocks by just creating public static final fields
	public static final Block coral_reef = new BlockCoralReef("coral_reef");
	public static final Block pistia = new BlockPistia();
	public static final Block aquanet_controller = new AquaNetController();
	public static final Block aqua_node = new AquaNode();
	public static final Block aquatic_charm = new BlockAquaCharm("aquatic_charm", Material.CLAY);

	public static final Block coral_reef_pink = new BlockCoralReef("coral_reef_pink");
	public static final Block coral_reef_blue = new BlockCoralReef("coral_reef_blue");
	public static final Block coral_reef_green = new BlockCoralReef("coral_reef_green");
	public static final Block coral_reef_red = new BlockCoralReef("coral_reef_red");
	public static final Block coral_reef_yellow = new BlockCoralReef("coral_reef_yellow");
}
