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

    //TODO -> getter query pos != null
    private boolean hasAquaController = false;
    @Nullable
    private BlockPos controllerPos = null;

    public TileOxygenFiller() {
        super(2);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        CapabilityOxygen.OXYGEN.readNBT(oxygen, null, nbt.getTag("oxygen"));
        hasAquaController = nbt.getBoolean("hasAquaController");
        if (nbt.hasKey("x") && nbt.hasKey("y") && nbt.hasKey("z")) {
            controllerPos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("oxygen", CapabilityOxygen.OXYGEN.writeNBT(oxygen, null));
        nbt.setBoolean("hasAquaController", hasAquaController);
        if (controllerPos != null) {
            nbt.setInteger("x", controllerPos.getX());
            nbt.setInteger("y", controllerPos.getY());
            nbt.setInteger("z", controllerPos.getZ());
        }
        return super.writeToNBT(nbt);
    }

    public void setHasAquaController(boolean hasAquaController) {
        this.hasAquaController = hasAquaController;
        this.markDirty();
    }

    @Override
    public void update() {
        if (!hasAquaController) {
            for (BlockPos bp : BlockPos.getAllInBox(pos.getX() - 15, pos.getY() - 15, pos.getZ() - 15, pos.getX() + 15, pos.getY() + 15, pos.getZ() + 15)) {
                IBlockState state = world.getBlockState(bp);
                if (!world.isAirBlock(bp)) {
                    if (state.getBlock() == AquaticBlocks.AQUANET_CONTROLLER) {
                        controllerPos = bp;
                        this.setHasAquaController(true);
                    }
                }
            }
        }
        if(controllerPos != null) {
            TileEntity controller = world.getTileEntity(controllerPos);
            if(controller != null) {
                IOxygenProvider oxygenController = controller.getCapability(CapabilityOxygen.OXYGEN, null);
                if(oxygenController != null) {
                    if(oxygenController.extractOxygen(oxygen.receiveOxygen(oxygenController.extractOxygen(TileAquaNetController.MAX_NETWORK_TRANSFER_AMOUNT, true), false), false) > 0) {
                        this.markDirty();
                        controller.markDirty();
                    }
                }
            }
            else this.setHasAquaController(false);
        }

        ItemStack stack = inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            IOxygenProvider oxygenProvider = stack.getCapability(CapabilityOxygen.OXYGEN, null);
            boolean flag = true;
            if(oxygenProvider != null) {
                flag = oxygen.extractOxygen(oxygenProvider.receiveOxygen(oxygen.extractOxygen(TileAquaNetController.MAX_NETWORK_TRANSFER_AMOUNT, true), false), false) == 0;
            }
            if(flag) {
                inventory.setStackInSlot(1, stack);
                inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
            this.markDirty();
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
