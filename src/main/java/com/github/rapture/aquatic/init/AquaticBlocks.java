package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.block.AquaNetController;
import com.github.rapture.aquatic.block.AquaNode;
import com.github.rapture.aquatic.block.plants.BlockCoralReef;
import com.github.rapture.aquatic.block.plants.BlockPistia;
import com.github.rapture.aquatic.util.RegistryCreate;
import net.minecraft.block.Block;

@RegistryCreate(value = Block.class, modid = Aquatic.MODID)
public class AquaticBlocks {

    //register blocks by just creating public static final fields
    public static final Block coral_reef = new BlockCoralReef();
    public static final Block pistia = new BlockPistia();
    public static final Block aquanet_controller = new AquaNetController();
    public static final Block aqua_node = new AquaNode();

}
