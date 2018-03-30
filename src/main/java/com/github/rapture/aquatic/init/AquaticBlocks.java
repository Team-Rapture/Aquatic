package com.github.rapture.aquatic.init;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.block.plants.BlockCoralReef;
import com.github.rapture.aquatic.block.plants.BlockPistia;
import com.github.rapture.aquatic.util.RegistryCreate;
import net.minecraft.block.Block;

@RegistryCreate(value = Block.class, modid = Aquatic.MODID)
public class AquaticBlocks {

    //register blocks by just creating public static final fields
    public static Block coral_reef = new BlockCoralReef();
    public static Block pistia = new BlockPistia();

}
