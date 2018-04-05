package com.github.rapture.aquatic.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity {

    public TileEntityBase() {
    }

    @Override
    public void markDirty() {
        super.markDirty();
        IBlockState state = world.getBlockState(pos);
        world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), state, state, 255);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return super.writeToNBT(nbt);
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    protected void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, getState(), getState(), 3);
        world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
        markDirty();
    }

    public IBlockState getState() {
        return world.getBlockState(pos);
    }

    protected AxisAlignedBB getRadius(BlockPos blockPos, int size, int height) {
        double hs = size / 2;
        double hh = height / 2;
        return new AxisAlignedBB(blockPos.getX() - hs, blockPos.getY() - hh, blockPos.getZ() - hs,
                blockPos.getX() + hs, blockPos.getY() + hh, blockPos.getZ() + hs);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}