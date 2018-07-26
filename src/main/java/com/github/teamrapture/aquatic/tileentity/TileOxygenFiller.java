package com.github.teamrapture.aquatic.tileentity;

import com.github.teamrapture.aquatic.api.capability.oxygen.CapabilityOxygen;
import com.github.teamrapture.aquatic.api.capability.oxygen.IOxygenProvider;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.api.capability.oxygen.OxygenStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileOxygenFiller extends TileEntityInventory implements ITickable {

    public OxygenStorage oxygen = new OxygenStorage(100000);
    private boolean hasAquaController = false;
    @Nullable
    private BlockPos controllerPos = null;

    public TileOxygenFiller() {
        super(2);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        oxygen.readFromNBT(nbt);
        hasAquaController = nbt.getBoolean("hasAquaController");
        if (nbt.hasKey("x") && nbt.hasKey("y") && nbt.hasKey("z")) {
            controllerPos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        oxygen.writeToNBT(nbt);
        nbt.setBoolean("hasAquaController", hasAquaController);
        if (controllerPos != null) {
            nbt.setInteger("x", controllerPos.getX());
            nbt.setInteger("y", controllerPos.getY());
            nbt.setInteger("z", controllerPos.getZ());
        }
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        if (!hasAquaController) {
            for (BlockPos bp : BlockPos.getAllInBox(pos.getX() - 15, pos.getY() - 15, pos.getZ() - 15, pos.getX() + 15, pos.getY() + 15, pos.getZ() + 15)) {
                IBlockState state = world.getBlockState(bp);
                if (!world.isAirBlock(bp)) {
                    if (state.getBlock() == AquaticBlocks.AQUANET_CONTROLLER) {
                        controllerPos = bp;
                        hasAquaController = true;
                        this.markDirty();
                    }
                }
            }
        }
        if(controllerPos != null) {
            TileEntity controller = world.getTileEntity(controllerPos);
            if(controller != null) {
                IOxygenProvider controllerOxygen = controller.getCapability(CapabilityOxygen.OXYGEN, null);
                if(controllerOxygen != null) {
                    if(controllerOxygen.canSendOxygen(20) && oxygen.canReceiveOxygen(20)) {
                        controllerOxygen.drainOxygen(20);
                        oxygen.fillOxygen(20);
                        this.markDirty();
                        controller.markDirty();
                    }
                }
            }
        }
        hasAquaController = false;  //TODO add setter -> mark dirkty
        this.markDirty();

        if (!inventory.getStackInSlot(0).isEmpty()) {
            if (inventory.getStackInSlot(0).hasCapability(CapabilityOxygen.OXYGEN, EnumFacing.UP)) {
                IOxygenProvider oxygenProvider = inventory.getStackInSlot(0).getCapability(CapabilityOxygen.OXYGEN, EnumFacing.UP);
                if (oxygenProvider != null && oxygenProvider.canReceiveOxygen(20) && oxygen.canSendOxygen(20)) {
                    oxygenProvider.fillOxygen(20);
                    oxygen.drainOxygen(20);
                    this.markDirty();
                } else {
                    inventory.setStackInSlot(1, inventory.getStackInSlot(0));
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityOxygen.OXYGEN) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityOxygen.OXYGEN) return CapabilityOxygen.OXYGEN.cast(oxygen);
        return super.getCapability(capability, facing);
    }
}
