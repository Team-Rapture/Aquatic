package com.github.rapture.aquatic.block.machine;

import com.github.rapture.aquatic.block.util.BlockContainerBase;
import com.github.rapture.aquatic.tileentity.TileDepthGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDepthGenerator extends BlockContainerBase {

    public BlockDepthGenerator() {
        super("depth_generator", Material.ROCK);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDepthGenerator();
    }
}
