package com.github.rapture.aquatic.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AquaNetController extends BlockContainerBase {

    public AquaNetController() {
        super("aquanet_controller", Material.ROCK);
        this.setHardness(1.0f);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return super.createNewTileEntity(worldIn, meta);
    }
}
