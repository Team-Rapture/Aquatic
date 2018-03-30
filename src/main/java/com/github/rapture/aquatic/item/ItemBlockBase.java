package com.github.rapture.aquatic.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block block) {
        super(block);
        this.setUnlocalizedName(block.getUnlocalizedName().substring(5));
        this.setRegistryName(block.getRegistryName());
    }

}
