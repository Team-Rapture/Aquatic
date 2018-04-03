package com.github.rapture.aquatic.item.tool;

import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.init.AquaticItems;
import com.github.rapture.aquatic.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class DivingKnife extends ItemBase {
    List<Block> breakable = new ArrayList<>();

    public DivingKnife(String name) {
        super(name);
        breakable.add(AquaticBlocks.CORAL_REEF_BLUE);
        breakable.add(AquaticBlocks.CORAL_REEF_PINK);
        breakable.add(AquaticBlocks.CORAL_REEF_RED);

        breakable.add(AquaticBlocks.CORAL_REEF_YELLOW);
        breakable.add(AquaticBlocks.CORAL_REEF_GREEN);
        breakable.add(AquaticBlocks.PISTIA);
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
                                    EntityLivingBase entityLiving) {
        if (!worldIn.isRemote) {
            stack.damageItem(1, entityLiving);
        }
        if (!worldIn.isRemote) {
            Block block = state.getBlock();
            if (breakable.contains(block))
                return !worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(),
                        new ItemStack(AquaticItems.ORGANIC_MATTER))) || super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
        }
        return true;
    }

}
