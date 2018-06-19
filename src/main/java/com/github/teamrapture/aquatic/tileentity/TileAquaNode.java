package com.github.teamrapture.aquatic.tileentity;

import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.api.oxygen.capability.CapabilityOxygen;
import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.client.render.hud.IHudSupport;
import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.init.AquaticItems;
import com.github.rapture.aquatic.item.armor.ScubaSuit;
import com.github.teamrapture.aquatic.api.oxygen.OxygenHandler;
import com.github.teamrapture.aquatic.api.oxygen.capability.CapabilityOxygen;
import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import com.github.teamrapture.aquatic.client.render.hud.IHudSupport;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.init.AquaticItems;
import com.github.teamrapture.aquatic.item.armor.ScubaSuit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileAquaNode extends TileEntityBase implements IHudSupport, ITickable {

    public static final int sqRange = 30 * 30;
    public OxygenHandler oxygen = new OxygenHandler(10000);
    public BlockPos controllerPos = null;
    public int beamRenderTicks;
    private boolean hasAquaController = false;

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        oxygen.readFromNBT(nbt);
        hasAquaController = nbt.getBoolean("hasAquaController");
        if (nbt.hasKey("contx") && nbt.hasKey("conty") && nbt.hasKey("contz")) {
            controllerPos = new BlockPos(nbt.getInteger("contx"), nbt.getInteger("conty"), nbt.getInteger("contz"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        oxygen.writeToNBT(nbt);
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
        this.beamRenderTicks++;
        if (world.isRemote) return;

        IBlockState cuurentState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, cuurentState, cuurentState, 3);

        if (!hasAquaController()) {
            if (world.getTotalWorldTime() % 20 != 0) return;
            for (BlockPos bp : BlockPos.getAllInBox(pos.getX() - 15, pos.getY() - 15, pos.getZ() - 15, pos.getX() + 15, pos.getY() + 15, pos.getZ() + 15)) {
                IBlockState state = world.getBlockState(bp);
                if (!world.isAirBlock(bp)) {
                    if (state.getBlock() == AquaticBlocks.AQUANET_CONTROLLER) {
                        controllerPos = bp;
                        setHasAquaController(true);
                    }
                }
            }
        } else {
            if (controllerPos != null && world.getTileEntity(controllerPos) != null) {
                if (!(world.getTileEntity(controllerPos) instanceof TileAquaNetController)) {
                    setHasAquaController(false);
                    return;
                }
                TileAquaNetController controller = (TileAquaNetController) world.getTileEntity(controllerPos);
                if (controller.oxygen.canSendOxygen(20)) {
                    if (oxygen.canReceiveOxygen(20)) {
                        controller.oxygen.drainOxygen(20);
                        oxygen.fillOxygen(20);
                        markDirty();
                        controller.markDirty();
                    }
                }
            } else {
                setHasAquaController(false);
            }
        }

        if (world.getTotalWorldTime() % 10 == 0) {
            List<EntityPlayer> playerList = playersInRange();
            if (playerList.size() > 0) {
            }
            for (EntityPlayer player : playerList) {
                if (player.isInWater() && !player.capabilities.isCreativeMode) {
                    sendPlayerAir(player);
                }
            }
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

    public void sendPlayerAir(EntityPlayer player) {
        if (player.getAir() < 300) {
            if (oxygen.canSendOxygen(300)) {
                if (hasFullArmor(player)) {
                    ScubaSuit suit = (ScubaSuit) player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
                    suit.oxygenStorage.fillOxygen(300);
                    oxygen.drainOxygen(300);
                    player.setAir(player.getAir() + 30);
                    markDirty();
                }
            }
        }
    }

    public boolean hasFullArmor(EntityPlayer player) {
        return player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == AquaticItems.SCUBA_HELEMT
                && player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == AquaticItems.SCUBA_CHEST
                && player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == AquaticItems.SCUBA_LEGGINGS
                && (player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == AquaticItems.SCUBA_FEET
                || player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == AquaticItems.HEAVY_IRON_BOOTS);

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
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityOxygen.OXYGEN_CAPABILITY) return (T) this.oxygen;
        return super.getCapability(capability, facing);
    }

    public boolean hasAquaController() {
        return hasAquaController;
    }

    public void setHasAquaController(boolean hasAquaController) {
        this.hasAquaController = hasAquaController;
        markDirty();
    }
}