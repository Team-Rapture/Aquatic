package com.github.teamrapture.aquatic.tileentity;

import com.github.rapture.aquatic.proxy.CommonProxy;
import com.github.teamrapture.aquatic.proxy.CommonProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileSolutionTank extends TileEntityInventory implements ITickable {

    public FluidTank tank = new FluidTank(8000) {
        @Override
        public void onContentsChanged() {
            if (this.tile != null) {
                final IBlockState state = this.tile.getWorld().getBlockState(this.tile.getPos());
                this.tile.getWorld().notifyBlockUpdate(this.tile.getPos(), state, state, 8);
                this.tile.markDirty();
            }
        }
    };

    public TileSolutionTank() {
        super(3);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("FluidData")) {
            this.tank.setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("FluidData")));
        }

        if (tank != null) {
            this.tank.setTileEntity(this);
            if (tank.getFluid() != null) {
                tank.readFromNBT(nbt);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (this.tank != null && this.tank.getFluid() != null) {
            final NBTTagCompound tankTag = new NBTTagCompound();
            this.tank.getFluid().writeToNBT(tankTag);
            nbt.setTag("FluidData", tankTag);
            tank.writeToNBT(nbt);
        }
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        if (!inventory.getStackInSlot(1).isEmpty() && inventory.getStackInSlot(1).getItem() == Items.BUCKET) {
            if (tank.getFluidAmount() >= 1000 && tank.getFluid().getFluid() == FluidRegistry.WATER) {
                if (inventory.getStackInSlot(2).isEmpty()) {
                    decrease(1);
                    inventory.setStackInSlot(2, new ItemStack(Items.WATER_BUCKET));
                    tank.drain(1000, true);
                }
            }
        }

        if (tank.getFluidAmount() > 0 && tank.getFluid().isFluidEqual(new FluidStack(CommonProxy.AQUA_WATER, 1000))) {
            if (tank.getFluid().tag != null) {
                double randomValue = (double) Math.round(Math.sqrt(6 + (14 - 6) * world.rand.nextDouble()) * 100d) / 100d;
                tank.getFluid().tag.setDouble("ph", randomValue);
            } else {
                tank.getFluid().tag = new NBTTagCompound();
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) this.tank;
        return super.getCapability(capability, facing);
    }
}
