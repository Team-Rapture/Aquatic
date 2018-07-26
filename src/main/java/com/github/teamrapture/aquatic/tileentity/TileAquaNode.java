package com.github.teamrapture.aquatic.tileentity;

import com.github.teamrapture.aquatic.api.capability.oxygen.CapabilityOxygen;
import com.github.teamrapture.aquatic.api.capability.oxygen.IOxygenProvider;
import com.github.teamrapture.aquatic.api.capability.oxygen.OxygenStorage;
import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import com.github.teamrapture.aquatic.client.render.hud.IHudSupport;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileAquaNode extends TileEntityBase implements IHudSupport, ITickable {

    private static final int sqRange = 30 * 30;

    //FIXME merge into one variable!
    private static final int NETWORK_TRANSFER_AMOUNT = 20;
    private static final int SUIT_TRANSFER_AMOUNT = 300;
    private IOxygenProvider oxygen = new OxygenStorage(10000);
    public BlockPos controllerPos = null;
    private boolean hasAquaController = false;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        CapabilityOxygen.OXYGEN.readNBT(this.oxygen, null, nbt.getTag("oxygen"));
        hasAquaController = nbt.getBoolean("hasAquaController");
        if (nbt.hasKey("contx") && nbt.hasKey("conty") && nbt.hasKey("contz")) {
            controllerPos = new BlockPos(nbt.getInteger("contx"), nbt.getInteger("conty"), nbt.getInteger("contz"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("oxygen", CapabilityOxygen.OXYGEN.writeNBT(this.oxygen, null));
        nbt.setBoolean("hasAquaController", hasAquaController());
        if (controllerPos != null) {
            nbt.setInteger("contx", controllerPos.getX());
            nbt.setInteger("conty", controllerPos.getY());
            nbt.setInteger("contz", controllerPos.getZ());
        }
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        if(world.isRemote) {
            BlockPos pos = this.getPos();
            IBlockState cuurentState = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, cuurentState, cuurentState, 3);

            if(!hasAquaController()) {
                if(world.getTotalWorldTime() % 20 == 0) for(BlockPos bp : BlockPos.getAllInBox(pos.add(-15, -15, -15), pos.add(15, 15, 15))) {
                    if (!world.isAirBlock(bp) && world.getBlockState(bp) == AquaticBlocks.AQUANET_CONTROLLER && world.getTileEntity(bp) instanceof TileAquaNetController) {
                        controllerPos = bp;
                        setHasAquaController(true);
                        break;
                    }
                }
            }
            else {

                //TODO make nodes connect to each other!

                if(controllerPos != null && world.getTileEntity(controllerPos) instanceof TileAquaNetController) {
                    TileAquaNetController controller = (TileAquaNetController) world.getTileEntity(controllerPos);
                    IOxygenProvider oxygenController = controller.getCapability(CapabilityOxygen.OXYGEN, null);
                    if(oxygenController != null) {
                        if(oxygen.canReceiveOxygen(NETWORK_TRANSFER_AMOUNT) && oxygenController.canSendOxygen(NETWORK_TRANSFER_AMOUNT)) {
                            oxygenController.drainOxygen(NETWORK_TRANSFER_AMOUNT);
                            oxygen.fillOxygen(NETWORK_TRANSFER_AMOUNT);
                            this.markDirty();
                            controller.markDirty();
                        }
                    }
                }
                else setHasAquaController(false);
            }

            if (world.getTotalWorldTime() % 10 == 0) playersInRange().forEach(this::sendPlayerAir);
        }
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
        player.getArmorInventoryList().forEach(stack -> {
            if(!this.oxygen.canSendOxygen(SUIT_TRANSFER_AMOUNT)) return;
            IOxygenProvider suitOxygen = stack.getCapability(CapabilityOxygen.OXYGEN, null);
            if(suitOxygen != null) {
                if(suitOxygen.canReceiveOxygen(SUIT_TRANSFER_AMOUNT)) {
                    suitOxygen.fillOxygen(SUIT_TRANSFER_AMOUNT);
                    this.oxygen.drainOxygen(SUIT_TRANSFER_AMOUNT);
                    markDirty();
                }
            }
        });
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
        return hasAquaController;
    }

    public void setHasAquaController(boolean hasAquaController) {
        this.hasAquaController = hasAquaController;
        markDirty();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
