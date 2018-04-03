package com.github.rapture.aquatic.block.machine;

import com.github.rapture.aquatic.block.util.BlockContainerBase;
import com.github.rapture.aquatic.tileentity.TileAquaNetController;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

//TODO Fix model rendering
public class AquaNetController extends BlockContainerBase {

    public AquaNetController() {
        super("aquanet_controller", Material.ROCK);
        this.setHardness(1.0f);
        this.setHarvestLevel("pickaxe", 1);
        this.setLightLevel(1f);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAquaNetController();
    }
}
