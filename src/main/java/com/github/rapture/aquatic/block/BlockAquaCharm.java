package com.github.rapture.aquatic.block;

import com.github.rapture.aquatic.block.util.BlockRenderInLiquidBase;
import com.github.rapture.aquatic.init.AquaticItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAquaCharm extends BlockRenderInLiquidBase {

    public BlockAquaCharm(String name, Material material) {
        super(name, material);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        super.onBlockHarvested(worldIn, pos, state, player);

    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return AquaticItems.AQUATIC_CHARM;
    }
}
