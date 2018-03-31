package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.block.AquaNetController;
import com.github.rapture.aquatic.block.AquaNode;
import com.github.rapture.aquatic.block.plants.BlockCoralReef;
import com.github.rapture.aquatic.block.plants.BlockPistia;
import com.github.rapture.aquatic.block.BlockOxygenStone;
import com.github.rapture.aquatic.util.RegistryCreate;
import net.minecraft.block.Block;

@RegistryCreate(value = Block.class, modid = Aquatic.MODID)
public class AquaticBlocks {

    //register blocks by just creating public static final fields
    public static final Block CORAL_REEF = new BlockCoralReef();
    public static final Block PISTIA = new BlockPistia();
    public static final Block AQUANET_CONTROLLER = new AquaNetController();
    public static final Block AQUA_NODE = new AquaNode();
    public static final Block OXYGEN_STONE = new BlockOxygenStone();

}
