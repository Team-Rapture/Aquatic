package com.github.rapture.aquatic.block.machine;

import com.github.rapture.aquatic.block.BlockContainerBase;
import com.github.rapture.aquatic.tileentity.TileCreativeGenerator;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCreativeGenerator extends BlockContainerBase {

    public BlockCreativeGenerator() {
        super("creative_battery", Material.IRON);
        this.setSoundType(SoundType.METAL);
        this.setLightLevel(1f);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return super.hasTileEntity(state);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCreativeGenerator();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }
}
