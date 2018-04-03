package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.api.oxygen.IOxygenProvider;
import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.api.oxygen.capability.CapabilityOxygen;
import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.util.TileEntityInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileOxygenFiller extends TileEntityInventory implements ITickable {

    public OxygenHandler oxygen = new OxygenHandler(100000);
    public boolean hasAquaController = false;
    public BlockPos controllerPos = null;

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
                    }
                }
            }
        } else {
            if (controllerPos != null && world.getTileEntity(controllerPos) != null) {
                if (!(world.getTileEntity(controllerPos) instanceof TileAquaNetController)) {
                    hasAquaController = false;
                    return;
                }
                TileAquaNetController controller = (TileAquaNetController) world.getTileEntity(controllerPos);
                if (controller.oxygen.canSendOxygen(20)) {
                    if (oxygen.canReceiveOxygen(20)) {
                        controller.oxygen.drainOxygen(20);
                        oxygen.fillOxygen(20);
                    }
                }
            } else {
                hasAquaController = false;
            }
        }

        if (!inventory.getStackInSlot(0).isEmpty()) {
            if (inventory.getStackInSlot(0).hasCapability(CapabilityOxygen.OXYGEN_CAPABILITY, EnumFacing.UP)) {
                IOxygenProvider oxygenProvider = inventory.getStackInSlot(0).getCapability(CapabilityOxygen.OXYGEN_CAPABILITY, EnumFacing.UP);
                if (oxygenProvider.getOxygenStored() < oxygenProvider.getMaxOxygenStorage()) {
                    if (oxygenProvider.canReceiveOxygen(20) && oxygen.canSendOxygen(20)) {
                        oxygenProvider.fillOxygen(20);
                        oxygen.drainOxygen(20);
                    }
                } else {
                    inventory.setStackInSlot(1, inventory.getStackInSlot(0));
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return (T) oxygen;
        return super.getCapability(capability, facing);
    }
}
