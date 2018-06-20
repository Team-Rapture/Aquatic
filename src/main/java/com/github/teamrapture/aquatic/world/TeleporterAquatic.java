package com.github.teamrapture.aquatic.world;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TeleporterAquatic extends Teleporter {
    public TeleporterAquatic(WorldServer world) {
        super(world);
    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }

    public static boolean isPosClear(Entity ent, World world) {
        AxisAlignedBB box = ent.getEntityBoundingBox();
        return StreamSupport.stream(BlockPos.getAllInBox(new BlockPos(box.minX, box.minY, box.minZ), new BlockPos(box.maxX, box.maxY, box.maxZ)).spliterator(), false).filter(pos -> {
            IBlockState blockState = world.getBlockState(pos);
            return blockState.getMaterial().isSolid() || blockState.getMaterial().getMobilityFlag() == EnumPushReaction.BLOCK;
        }).collect(Collectors.toList()).size() > 0;
    }
}
