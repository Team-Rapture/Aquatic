package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.util.CustomEnergyStorage;
import com.github.rapture.aquatic.util.TileEntityBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

//TODO make oxygen generation smoother, maybe a better way the Aqua Controllers are founds?
public class TileAquaNode extends TileEntityBase {

    public CustomEnergyStorage storage = new CustomEnergyStorage(50000);
    public int oxygenlevels = 0;
    public int maxOxygen = 10000;
    public boolean hasAquaController = false;
    public BlockPos controllerPos = null;

    public TileAquaNode() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        oxygenlevels = nbt.getInteger("oxygenlevels");
        hasAquaController = nbt.getBoolean("hasAquaController");
        if(nbt.hasKey("x") && nbt.hasKey("y") && nbt.hasKey("z")) {
            controllerPos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        storage.writeToNBT(nbt);
        nbt.setInteger("oxygenlevels", oxygenlevels);
        nbt.setBoolean("hasAquaController", hasAquaController);
        if(controllerPos != null) {
            nbt.setInteger("x", controllerPos.getX());
            nbt.setInteger("y", controllerPos.getY());
            nbt.setInteger("z", controllerPos.getZ());
        }
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        super.update();
        if(!hasAquaController) {
            for (BlockPos bp : BlockPos.getAllInBox(pos.getX() - 15, pos.getY() - 15, pos.getZ() - 15, pos.getX() + 15, pos.getY() + 15, pos.getZ() + 15)) {
                IBlockState state = world.getBlockState(bp);
                if (!world.isAirBlock(bp)) {
                    if (state.getBlock() == AquaticBlocks.aquanet_controller) {
                        TileAquaNetController controller = (TileAquaNetController) world.getTileEntity(bp);
                        if (controller.storage.getEnergyStored() >= 500) {
                            if (storage.canReceiveEnergy(500)) {
                                controller.storage.extractEnergy(500, false);
                                storage.receiveEnergy(500, false);
                            }
                        }
                    }
                }
            }
        }else {
            if(controllerPos != null) {
                if(world.getTileEntity(controllerPos) != null) {
                    TileAquaNetController controller = (TileAquaNetController) world.getTileEntity(controllerPos);
                    if(!(world.getTileEntity(controllerPos) instanceof TileAquaNetController)) {
                        hasAquaController = false;
                        return;
                    }
                    if (controller.storage.getEnergyStored() >= 500) {
                        if (storage.canReceiveEnergy(500)) {
                            controller.storage.extractEnergy(500, false);
                            storage.receiveEnergy(500, false);
                        }
                    }
                }else {
                    hasAquaController = false;
                }
            }else {
                hasAquaController = false;
            }
        }

        if(storage.getEnergyStored() > 500) {
            if(maxOxygen - oxygenlevels >= 50) {
                storage.extractEnergy(500, false);
                oxygenlevels+=50;
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return (T) this.storage;
        return super.getCapability(capability, facing);
    }
}
