package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.api.oxygen.capability.CapabilityOxygen;
import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.client.render.hud.IHudSupport;
import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.util.CustomEnergyStorage;
import com.github.rapture.aquatic.util.TileEntityBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.List;

public class TileAquaNode extends TileEntityBase implements IHudSupport {

    public CustomEnergyStorage storage = new CustomEnergyStorage(50000);
    public OxygenHandler oxygenStorage = new OxygenHandler(10000);
    public boolean hasAquaController = false;
    public BlockPos controllerPos = null;
    public int timer = 0;
    public int oxygenTimer = 0;

    public TileAquaNode() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        oxygenStorage.readFromNBT(nbt);
        hasAquaController = nbt.getBoolean("hasAquaController");
        this.timer = nbt.getInteger("timer");
        this.oxygenTimer = nbt.getInteger("oxygenTimer");
        if (nbt.hasKey("x") && nbt.hasKey("y") && nbt.hasKey("z")) {
            controllerPos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        storage.writeToNBT(nbt);
        oxygenStorage.writeToNBT(nbt);
        nbt.setBoolean("hasAquaController", hasAquaController);
        nbt.setInteger("timer", timer);
        nbt.setInteger("oxygenTimer", oxygenTimer);
        if (controllerPos != null) {
            nbt.setInteger("x", controllerPos.getX());
            nbt.setInteger("y", controllerPos.getY());
            nbt.setInteger("z", controllerPos.getZ());
        }
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        super.update();
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
                if (controller.storage.getEnergyStored() >= 20) {
                    if (storage.canReceiveEnergy(20)) {
                        controller.storage.extractEnergy(20, false);
                        storage.receiveEnergy(20, false);
                    }
                }
            } else {
                hasAquaController = false;
            }
        }

        if (oxygenStorage.canReceiveOxygen(50)) {
            if (storage.getEnergyStored() > 500) {
                timer++;
                if(timer >= 20) {
                    storage.extractEnergy(500, false);
                    oxygenStorage.fillOxygen(50);
                    timer = 0;
                }
            }else if (timer > 0) {
                timer = 0;
            }
        }

        oxygenTimer++;
        if(oxygenTimer >= 18) {
            for (EntityPlayer player : playersInRange()) {
                if (player.isInWater() && !player.capabilities.isCreativeMode) {
                    sendPlayerAir(player);
                }
            }
            oxygenTimer = 0;
        }
    }

    public List<EntityPlayer> playersInRange() {
        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, getRadius(getPos(), 60, 60));
        if(players != null) {
            return players;
        }
        return null;
    }

    public void sendPlayerAir(EntityPlayer player) {
        if (player.getAir() < 300) {
            if (oxygenStorage.canSendOxygen(300)) {
                oxygenStorage.drainOxygen(300);
                player.setAir(player.getAir() + 30);
            }
        }
    }

    @Override
    public EnumFacing getBlockOrientation() {
        return HudRender.getOrientationHoriz(getBlockMetadata());
    }

    @Override
    public String getDisplay() {
        return "FE: " + storage.getEnergyStored() + " Oxygen: " + oxygenStorage.getOxygenStored();
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
        if (capability == CapabilityEnergy.ENERGY) return true;
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return (T) this.storage;
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return (T) this.oxygenStorage;
        return super.getCapability(capability, facing);
    }
}
