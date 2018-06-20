package com.github.teamrapture.aquatic.block.plants;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.util.IHasItemBlock;
import com.github.teamrapture.aquatic.util.NameUtil;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

//TODO get a better texture/model made
public class BlockPistia extends BlockBush implements IHasItemBlock {

    public BlockPistia() {
        super(Material.ROCK);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
        NameUtil.name(this, "pistia");
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
        IBlockState soil = world.getBlockState(pos.down());
        return world.isAirBlock(pos) && soil.getMaterial() == Material.WATER && (!soil.getPropertyKeys().contains(BlockLiquid.LEVEL) || soil.getValue(BlockLiquid.LEVEL) == 0);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return canSustainBush(worldIn.getBlockState(pos.down()));
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Water;
    }

    @Override
    public boolean canSustainBush(IBlockState state) {
        return state.getMaterial() == Material.ICE || state.getMaterial() == Material.WATER && (!state.getPropertyKeys().contains(BlockLiquid.LEVEL) || state.getValue(BlockLiquid.LEVEL) == 0);
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockPistia.class;
    }
}
