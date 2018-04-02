package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.api.oxygen.OxygenHandler;
import com.github.rapture.aquatic.api.oxygen.capability.CapabilityOxygen;
import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.client.render.hud.IHudSupport;
import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.init.AquaticItems;
import com.github.rapture.aquatic.item.armor.ScubaSuit;
import com.github.rapture.aquatic.util.TileEntityBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.List;

public class TileAquaNode extends TileEntityBase implements IHudSupport {

    public OxygenHandler oxygen = new OxygenHandler(10000);
    public boolean hasAquaController = false;
    public BlockPos controllerPos = null;
    public int oxygenTimer = 0;

    public TileAquaNode() {
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        oxygen.readFromNBT(nbt);
        hasAquaController = nbt.getBoolean("hasAquaController");
        this.oxygenTimer = nbt.getInteger("oxygenTimer");
        if (nbt.hasKey("x") && nbt.hasKey("y") && nbt.hasKey("z")) {
            controllerPos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        oxygen.writeToNBT(nbt);
        nbt.setBoolean("hasAquaController", hasAquaController);
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

        oxygenTimer++;
        if (oxygenTimer >= 10) {
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
        if (players != null) {
            return players;
        }
        return null;
    }

    public void sendPlayerAir(EntityPlayer player) {
        if (player.getAir() < 300) {
            if (oxygen.canSendOxygen(300)) {
                if (hasFullArmor(player)) {
                    ScubaSuit suit = (ScubaSuit) player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
                    suit.oxygenStorage.fillOxygen(300);
                    oxygen.drainOxygen(300);
                    player.setAir(player.getAir() + 30);
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
}
