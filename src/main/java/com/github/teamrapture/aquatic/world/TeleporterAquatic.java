package com.github.teamrapture.aquatic.world;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class TeleporterAquatic implements ITeleporter {

    public TeleporterAquatic() {
        //NO-OP
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        while (!isPosClear(entity.getPosition(), world) || !isPosClear(entity.getPosition().add(0, entity.getEyeHeight(), 0), world)) {
            entity.posY += 1; //TODO better algorithm for finding a suitable spawn position
        }
        BlockPos pos = entity.getPosition();
        entity.setPositionAndUpdate(pos.getX(), pos.getY() + 0.1D, pos.getZ());
    }

    public static boolean isPosClear(BlockPos pos, World world) {
        IBlockState state = world.getBlockState(pos);
        return (!state.getMaterial().isSolid() && state.getMaterial().getMobilityFlag() != EnumPushReaction.BLOCK) || pos.getY() > world.getHeight();
    }
}
