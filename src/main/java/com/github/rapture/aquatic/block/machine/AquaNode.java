package com.github.rapture.aquatic.block.machine;

import com.github.rapture.aquatic.block.util.BlockContainerBase;
import com.github.rapture.aquatic.tileentity.TileAquaNode;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AquaNode extends BlockContainerBase {

    public AquaNode() {
        super("aqua_node", Material.ROCK);
        this.setHardness(1.0f);
        this.setHarvestLevel("pickaxe", 1);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAquaNode();
    }
}
