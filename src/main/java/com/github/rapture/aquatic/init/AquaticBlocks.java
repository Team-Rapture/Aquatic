package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.block.BlockAquaCharm;
import com.github.rapture.aquatic.block.BlockOxygenStone;
import com.github.rapture.aquatic.block.fluid.FluidAquaWaterBlock;
import com.github.rapture.aquatic.block.machine.AquaNetController;
import com.github.rapture.aquatic.block.machine.AquaNode;
import com.github.rapture.aquatic.block.machine.BlockSolutionTank;
import com.github.rapture.aquatic.block.plants.BlockPistia;
import com.github.rapture.aquatic.block.plants.BlockPlantBase;
import com.github.rapture.aquatic.block.util.BlockBase;
import com.github.rapture.aquatic.util.RegistryCreate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemShears;

@RegistryCreate(value = Block.class, modid = Aquatic.MODID)
public class AquaticBlocks {

    public static final Block aquatic_charm = new BlockAquaCharm("aquatic_charm", Material.CLAY);

    // register blocks by just creating public static final fields
    public static final Block CORAL_REEF = new BlockPlantBase("coral_reef");
    public static final Block HYDRILLA = new BlockPlantBase("hydrilla");
    public static final Block PISTIA = new BlockPistia();
    public static final Block AQUANET_CONTROLLER = new AquaNetController();
    public static final Block AQUA_NODE = new AquaNode();
    public static final Block OXYGEN_STONE = new BlockOxygenStone();
    public static final Block AQUA_WATER_BLOCK = new FluidAquaWaterBlock();
    public static final Block AQUATIC_STONE = new BlockBase("aquatic_stone");
    public static final Block AQUATIC_STONE_CRACKED = new BlockBase("aquatic_stone_cracked");
    public static final Block SOLUTION_TANK = new BlockSolutionTank();

    public static final Block coral_reef_pink = new BlockPlantBase("coral_reef_pink");
    public static final Block coral_reef_blue = new BlockPlantBase("coral_reef_blue");
    public static final Block coral_reef_green = new BlockPlantBase("coral_reef_green");
    public static final Block coral_reef_red = new BlockPlantBase("coral_reef_red");
    public static final Block coral_reef_yellow = new BlockPlantBase("coral_reef_yellow");


}
