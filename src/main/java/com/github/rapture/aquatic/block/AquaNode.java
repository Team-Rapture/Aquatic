package com.github.rapture.aquatic.block;

import com.github.rapture.aquatic.tileentity.TileAquaNode;
import com.github.rapture.aquatic.util.IHasItemBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AquaNode extends BlockContainerBase {

    public AquaNode() {
        super("aqua_node", Material.ROCK);
        this.setHardness(1.0f);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAquaNode();
    }
}
