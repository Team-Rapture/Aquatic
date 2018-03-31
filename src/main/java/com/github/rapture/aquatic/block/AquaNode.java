package com.github.rapture.aquatic.block;

import com.github.rapture.aquatic.tileentity.TileAquaNetController;
import com.github.rapture.aquatic.tileentity.TileAquaNode;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AquaNode extends BlockContainerBase {

    public AquaNode() {
        super("aqua_node", Material.ROCK);
        this.setHardness(1.0f);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileAquaNode ctrl = (TileAquaNode) worldIn.getTileEntity(pos);
        if(!worldIn.isRemote) {
            playerIn.sendMessage(new TextComponentString(ctrl.storage.getEnergyStored() + " FE"));
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAquaNode();
    }
}
