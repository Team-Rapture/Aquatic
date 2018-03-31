package com.github.rapture.aquatic.block.plants;

import com.github.rapture.aquatic.init.AquaticBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemBlockPistia extends ItemBlock {

    public ItemBlockPistia(Block block) {
        super(block);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        return new ActionResult<>(tryPlaceBlock(worldIn, playerIn, handIn, stack), stack);
    }

    public EnumActionResult tryPlaceBlock(World worldIn, EntityPlayer playerIn, EnumHand handIn, ItemStack stack) {
        if(worldIn.isRemote) return EnumActionResult.SUCCESS;
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
        if (raytraceresult == null) return EnumActionResult.PASS;
        if (!stack.isEmpty() && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockPos = raytraceresult.getBlockPos();
            BlockPos targetPos = blockPos.offset(raytraceresult.sideHit);
            if (worldIn.isBlockModifiable(playerIn, blockPos) && playerIn.canPlayerEdit(targetPos, raytraceresult.sideHit, stack) && AquaticBlocks.PISTIA.canPlaceBlockAt(worldIn, targetPos)) {
                if (worldIn.setBlockState(targetPos, this.block.getDefaultState(), 11)) {
                    worldIn.playSound(playerIn, blockPos, SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    playerIn.addStat(StatList.getObjectUseStats(this));
                    if (!playerIn.isCreative()) stack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return tryPlaceBlock(worldIn, player, hand, player.getHeldItem(hand));
    }
}
