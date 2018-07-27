package com.github.teamrapture.aquatic.tileentity;

import com.github.teamrapture.aquatic.api.IAquaNetworkNode;
import com.github.teamrapture.aquatic.api.capability.oxygen.CapabilityOxygen;
import com.github.teamrapture.aquatic.api.capability.oxygen.IOxygenProvider;
import com.github.teamrapture.aquatic.api.capability.oxygen.OxygenStorage;
import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import com.github.teamrapture.aquatic.client.render.hud.IHudSupport;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TileAquaNode extends TileEntityBase implements IHudSupport, ITickable, IAquaNetworkNode {

    private static final int sqRange = 30 * 30;
    private static final int CHECK_RADIUS = 15;

    private IOxygenProvider oxygen = new OxygenStorage(512);
    public BlockPos controllerPos = null;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        CapabilityOxygen.OXYGEN.readNBT(this.oxygen, null, nbt.getTag("oxygen"));
        if(nbt.hasKey("controllerPos")) this.controllerPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("controllerPos"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("oxygen", CapabilityOxygen.OXYGEN.writeNBT(this.oxygen, null));
        nbt.setBoolean("hasAquaController", hasAquaController());
        if (controllerPos != null) nbt.setTag("controllerPos", NBTUtil.createPosTag(controllerPos));
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        if(world.isRemote) {
            BlockPos pos = this.getPos();
            IBlockState currentState = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, currentState, currentState, 3);
            if(!hasAquaController() && world.getTotalWorldTime() % 20 == 0) { //TODO improve heavy check and pass load onto another thread? (also keep in mind this is updated for ALL nodes in the world simultaneously)
                List<BlockPos> controllerPoses = StreamSupport.stream(BlockPos.getAllInBox(pos.add(-CHECK_RADIUS, -CHECK_RADIUS, -CHECK_RADIUS), pos.add(CHECK_RADIUS, CHECK_RADIUS, CHECK_RADIUS)).spliterator(), false).filter(pos1 -> !pos1.equals(pos) && world.getTileEntity(pos1) instanceof IAquaNetworkNode).sorted(Comparator.comparingDouble(pos1 -> pos.getDistance(pos1.getX(), pos1.getY(), pos1.getZ()))).collect(Collectors.toList());
                if(controllerPoses.size() > 0) this.setAquaController(controllerPoses.get(0));
            }
            else {
                if(hasAquaController() && world.getTileEntity(controllerPos) instanceof IAquaNetworkNode) {
                    IAquaNetworkNode controller = (IAquaNetworkNode) world.getTileEntity(controllerPos);
                    IOxygenProvider oxygenController = controller.getCapability(CapabilityOxygen.OXYGEN, null);
                    if(oxygenController != null) {
                        if(oxygenController.extractOxygen(oxygen.receiveOxygen(oxygenController.extractOxygen(TileAquaNetController.MAX_NETWORK_TRANSFER_AMOUNT, true), false), false) > 0) {
                            this.markDirty();
                            controller.setDirty();
                        }
                    }
                }
                else this.setAquaController(null);
            }

            if (world.getTotalWorldTime() % 10 == 0) playersInRange().forEach(this::sendPlayerAir);
        }
    }

    @Override
    public void setDirty() {
        this.markDirty();
    }

    public List<EntityPlayer> playersInRange() {
        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, getRadius(getPos(), 60, 60));
        List<EntityPlayer> rangePlayer = new ArrayList<>();
        for (EntityPlayer player : players) {
            if (player.getDistanceSq(this.pos) <= sqRange) {
                rangePlayer.add(player);
            }
        }
        return rangePlayer;
    }

    private void sendPlayerAir(EntityPlayer player) {
        int transferred = 0;
        for(ItemStack stack : player.getArmorInventoryList()) {
            IOxygenProvider suitOxygen = stack.getCapability(CapabilityOxygen.OXYGEN, null);
            if(suitOxygen != null) transferred += oxygen.extractOxygen(suitOxygen.receiveOxygen(oxygen.extractOxygen(TileAquaNetController.MAX_ARMOR_TRANSFER_AMOUNT, true), false), false);
        }
        if(transferred > 0) this.markDirty();
    }

    @Override
    public EnumFacing getBlockOrientation() {
        return HudRender.getOrientationHoriz(getBlockMetadata());
    }

    @Override
    public String getDisplay() {
        return "Oxygen: " + oxygen.getOxygenStored();
    }

    @Override
    public boolean isBlockAboveAir() {
        return getWorld().isAirBlock(pos.up());
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityOxygen.OXYGEN) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityOxygen.OXYGEN) return (T) this.oxygen;
        return super.getCapability(capability, facing);
    }

    public boolean hasAquaController() {
        return this.controllerPos != null;
    }

    public void setAquaController(BlockPos pos) {
        this.controllerPos = pos;
        markDirty();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
