package com.github.rapture.aquatic.block.plants;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.util.IHasItemBlock;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//TODO get a better texture/model made
public class BlockCoralReef extends BlockBush implements IHasItemBlock {

    public BlockCoralReef(String name) {
        super(Material.ROCK);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canSustainBush(IBlockState state) {
        return true;
    }
}
