package com.github.rapture.aquatic.item.tool;

import com.github.rapture.aquatic.block.plants.BlockPlantBase;
import com.github.rapture.aquatic.init.AquaticItems;
import com.github.rapture.aquatic.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class DivingKnife extends ItemBase {
    public DivingKnife() {
        super("diving_knife");
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote)
        {
            stack.damageItem(1, entityLiving);
        }

        Block block = state.getBlock();
        if (block instanceof BlockPlantBase) return true;
        worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(),pos.getY(),pos.getZ(),new ItemStack(AquaticItems.ORGANIC_MATTER)));
        return true;
    }
}
