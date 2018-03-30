package com.github.rapture.aquatic.block;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.util.IHasItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockBase extends Block implements IHasItemBlock {

    public BlockBase(String name, Material material) {
        super(material);
        this.setUnlocalizedName(Aquatic.MODID + "." + name);
    }

    public BlockBase(String name) {
        this(name, Material.ROCK);
        this.setSoundType(SoundType.STONE);
    }

}
