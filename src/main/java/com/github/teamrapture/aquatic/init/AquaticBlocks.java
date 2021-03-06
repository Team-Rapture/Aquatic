package com.github.teamrapture.aquatic.init;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.block.BlockAnglerTorch;
import com.github.teamrapture.aquatic.block.BlockAquaCharm;
import com.github.teamrapture.aquatic.block.BlockBase;
import com.github.teamrapture.aquatic.block.BlockOxygenStone;
import com.github.teamrapture.aquatic.block.fluid.FluidAquaWaterBlock;
import com.github.teamrapture.aquatic.block.machine.AquaNetController;
import com.github.teamrapture.aquatic.block.machine.AquaNode;
import com.github.teamrapture.aquatic.block.machine.BlockCreativeGenerator;
import com.github.teamrapture.aquatic.block.machine.BlockDepthGenerator;
import com.github.teamrapture.aquatic.block.machine.BlockEnergyFiller;
import com.github.teamrapture.aquatic.block.machine.BlockOxygenFiller;
import com.github.teamrapture.aquatic.block.machine.BlockSolutionTank;
import com.github.teamrapture.aquatic.block.plants.BlockPistia;
import com.github.teamrapture.aquatic.block.plants.BlockPlantBase;
import com.github.teamrapture.aquatic.client.render.RenderAquaNetController;
import com.github.teamrapture.aquatic.client.render.RenderAquaNode;
import com.github.teamrapture.aquatic.client.render.RenderDepthGenerator;
import com.github.teamrapture.aquatic.client.render.RenderSolutionTank;
import com.github.teamrapture.aquatic.tileentity.TileAquaNetController;
import com.github.teamrapture.aquatic.tileentity.TileAquaNode;
import com.github.teamrapture.aquatic.tileentity.TileCreativeGenerator;
import com.github.teamrapture.aquatic.tileentity.TileDepthGenerator;
import com.github.teamrapture.aquatic.tileentity.TileEnergyFiller;
import com.github.teamrapture.aquatic.tileentity.TileOxygenFiller;
import com.github.teamrapture.aquatic.tileentity.TileSolutionTank;
import com.github.teamrapture.aquatic.util.RegistryCreate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@RegistryCreate(value = Block.class, modid = Aquatic.MODID)
public class AquaticBlocks {

	public static final Block PISTIA = new BlockPistia();
	public static final Block AQUANET_CONTROLLER = new AquaNetController();
	public static final Block AQUA_NODE = new AquaNode();
	public static final Block OXYGEN_STONE = new BlockOxygenStone();
	public static final Block AQUA_WATER_BLOCK = new FluidAquaWaterBlock();
	public static final Block SOLUTION_TANK = new BlockSolutionTank();
	public static final Block OXYGEN_FILLER = new BlockOxygenFiller();
	public static final Block ENERGY_FILLER = new BlockEnergyFiller();
	public static final Block DEPTH_GENERATOR = new BlockDepthGenerator();
	public static final Block CREATIVE_GENERATOR = new BlockCreativeGenerator();
	public static final Block ANGLER_TORCH = new BlockAnglerTorch();

	
	public static final Block GRASS_STONE_1 = new BlockBase("grass_stone_1");
	public static final Block BLUE_GRASS_STONE = new BlockBase("blue_grass_stone");
	public static final Block GRASS_STONE_2 = new BlockBase("grass_stone_2");
	public static final Block SANDY_GRASS_STONE = new BlockBase("grass_sandy");
	public static final Block AQUATIC_MOSS_STONE = new BlockBase("aquatic_mossy_stone");
	public static final Block SEA_GRASS = new BlockPlantBase("sea_grass");
	public static final Block BIG_PLANT = new BlockPlantBase("big_plant");
	
	public static final Block IRON_ORE_DEPOSIT = new BlockBase("iron_ore_deposit");
	public static final Block GOLD_ORE_DEPOSIT = new BlockBase("gold_ore_deposit");
	public static final Block DIAMOND_ORE_DEPOSIT = new BlockBase("diamond_ore_deposit");
	public static final Block COAL_ORE_DEPOSIT = new BlockBase("coal_ore_deposit");
	public static final Block REDSTONE_ORE_DEPOSIT = new BlockBase("redstone_ore_deposit");
	public static final Block LAPIZ_ORE_DEPOSIT = new BlockBase("lapiz_ore_deposit");
	public static final Block QUARTZ_ORE_DEPOSIT = new BlockBase("quartz_ore_deposit");
	public static final Block EMERALD_ORE_DEPOSIT = new BlockBase("emerald_ore_deposit");
	public static final Block AQUATIC_STONE = new BlockBase("aquatic_stone");
	public static final Block AQUATIC_STONE_CRACKED = new BlockBase("aquatic_stone_cracked");
	public static final Block AQUATIC_CHARM = new BlockAquaCharm("aquatic_charm", Material.CLAY);
	public static final Block HYDRILLA = new BlockPlantBase("hydrilla");
	public static final Block CORAL_REEF_PINK = new BlockPlantBase("coral_reef_pink");
	public static final Block CORAL_REEF_BLUE = new BlockPlantBase("coral_reef_blue");
	public static final Block CORAL_REEF_GREEN = new BlockPlantBase("coral_reef_green");
	public static final Block CORAL_REEF_RED = new BlockPlantBase("coral_reef_red");
	public static final Block CORAL_REEF_YELLOW = new BlockPlantBase("coral_reef_yellow");

    public static void registerTiles() {
        GameRegistry.registerTileEntity(TileAquaNetController.class, new ResourceLocation(Aquatic.MODID, "aquanet_controller"));
        GameRegistry.registerTileEntity(TileAquaNode.class, new ResourceLocation(Aquatic.MODID, "aqua_node"));
        GameRegistry.registerTileEntity(TileSolutionTank.class, new ResourceLocation(Aquatic.MODID, "solution_tank"));
        GameRegistry.registerTileEntity(TileOxygenFiller.class, new ResourceLocation(Aquatic.MODID, "oxygen_filler"));
        GameRegistry.registerTileEntity(TileEnergyFiller.class, new ResourceLocation(Aquatic.MODID, "energy_filler"));
        GameRegistry.registerTileEntity(TileDepthGenerator.class, new ResourceLocation(Aquatic.MODID, "depth_generator"));
        GameRegistry.registerTileEntity(TileCreativeGenerator.class, new ResourceLocation(Aquatic.MODID, "creative_generator"));
    }

    @SideOnly(Side.CLIENT)
    public static void bindTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAquaNetController.class, new RenderAquaNetController());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAquaNode.class, new RenderAquaNode());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSolutionTank.class, new RenderSolutionTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDepthGenerator.class, new RenderDepthGenerator());
    }
}
