package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.block.BlockAnglerTorch;
import com.github.rapture.aquatic.block.BlockAquaCharm;
import com.github.rapture.aquatic.block.BlockBase;
import com.github.rapture.aquatic.block.BlockOxygenStone;
import com.github.rapture.aquatic.block.fluid.FluidAquaWaterBlock;
import com.github.rapture.aquatic.block.machine.AquaNetController;
import com.github.rapture.aquatic.block.machine.AquaNode;
import com.github.rapture.aquatic.block.machine.BlockCreativeGenerator;
import com.github.rapture.aquatic.block.machine.BlockDepthGenerator;
import com.github.rapture.aquatic.block.machine.BlockEnergyFiller;
import com.github.rapture.aquatic.block.machine.BlockOxygenFiller;
import com.github.rapture.aquatic.block.machine.BlockSolutionTank;
import com.github.rapture.aquatic.block.plants.BlockPistia;
import com.github.rapture.aquatic.block.plants.BlockPlantBase;
import com.github.rapture.aquatic.util.RegistryCreate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@RegistryCreate(value = Block.class, modid = Aquatic.MODID)
public class AquaticBlocks {

    public static final Block AQUATIC_CHARM = new BlockAquaCharm("aquatic_charm", Material.CLAY);

    public static final Block HYDRILLA = new BlockPlantBase("hydrilla");
    public static final Block PISTIA = new BlockPistia();
    public static final Block AQUANET_CONTROLLER = new AquaNetController();
    public static final Block AQUA_NODE = new AquaNode();
    public static final Block OXYGEN_STONE = new BlockOxygenStone();
    public static final Block AQUA_WATER_BLOCK = new FluidAquaWaterBlock();
    public static final Block AQUATIC_STONE = new BlockBase("aquatic_stone");
    public static final Block AQUATIC_STONE_CRACKED = new BlockBase("aquatic_stone_cracked");
    public static final Block SOLUTION_TANK = new BlockSolutionTank();
    public static final Block OXYGEN_FILLER = new BlockOxygenFiller();
    public static final Block ENERGY_FILLER = new BlockEnergyFiller();
    public static final Block DEPTH_GENERATOR = new BlockDepthGenerator();
    public static final Block CREATIVE_GENERATOR = new BlockCreativeGenerator();

    public static final Block IRON_ORE_DEPOSIT = new BlockBase("iron_ore_deposit");
    public static final Block GOLD_ORE_DEPOSIT = new BlockBase("gold_ore_deposit");
    public static final Block DIAMOND_ORE_DEPOSIT = new BlockBase("diamond_ore_deposit");
    public static final Block COAL_ORE_DEPOSIT = new BlockBase("coal_ore_deposit");
    public static final Block REDSTONE_ORE_DEPOSIT = new BlockBase("redstone_ore_deposit");
    public static final Block LAPIZ_ORE_DEPOSIT = new BlockBase("lapiz_ore_deposit");
    public static final Block QUARTZ_ORE_DEPOSIT = new BlockBase("quartz_ore_deposit");
    public static final Block EMERALD_ORE_DEPOSIT = new BlockBase("emerald_ore_deposit") {
    	@Override
    public void getDrops(net.minecraft.util.NonNullList<net.minecraft.item.ItemStack> drops, net.minecraft.world.IBlockAccess world, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, int fortune) {};
    };

    public static final Block CORAL_REEF_PINK = new BlockPlantBase("coral_reef_pink");
    public static final Block CORAL_REEF_BLUE = new BlockPlantBase("coral_reef_blue");
    public static final Block CORAL_REEF_GREEN = new BlockPlantBase("coral_reef_green");
    public static final Block CORAL_REEF_RED = new BlockPlantBase("coral_reef_red");
    public static final Block CORAL_REEF_YELLOW = new BlockPlantBase("coral_reef_yellow");

    public static final Block ANGLER_TORCH = new BlockAnglerTorch("angler_torch", Material.PLANTS).setLightLevel(2);

}
