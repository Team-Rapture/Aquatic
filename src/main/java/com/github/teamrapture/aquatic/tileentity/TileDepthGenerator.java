package com.github.teamrapture.aquatic.tileentity;

import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import com.github.teamrapture.aquatic.client.render.hud.IHudSupport;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileDepthGenerator extends TileEntityBase implements IHudSupport, ITickable {

    private static final List<Block> ORES = new ArrayList<>();
    private IEnergyStorage storage = new EnergyStorage(1000000, 0, 128000);
    private int timer = 0;

    /**
     * get some ore blocks to generate
     */
    public static void init() {
        for (String string : OreDictionary.getOreNames()) {
            if (string.toLowerCase().startsWith("ore")) {
                NonNullList<ItemStack> oreNames = OreDictionary.getOres(string);
                for (ItemStack stack : oreNames) {
                    Block block = Block.getBlockFromItem(stack.getItem());
                    if (block != Blocks.AIR) {
                        ORES.add(block);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        CapabilityEnergy.ENERGY.readNBT(storage, null, nbt.getTag("energy"));
        timer = nbt.getInteger("timer");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("energy", CapabilityEnergy.ENERGY.writeNBT(storage, null));
        nbt.setInteger("timer", timer);
        return nbt;
    }

    @Override
    public void update() {
        if(!world.isRemote) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }

        if (!world.isRemote && world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) {
            for (BlockPos po : BlockPos.getAllInBox(pos.getX() - 5, pos.getY(), pos.getZ() - 5, pos.getX() + 5, pos.getY() + 4, pos.getZ() + 5)) {
                if (world.isAirBlock(po)) {
                    if (storage.getEnergyStored() >= AquaticConfig.machines.depthUsage && ORES.size() > 0) {
                        timer++;
                        if (timer >= 20 * 15) {
                            storage.extractEnergy(AquaticConfig.machines.depthUsage, false);
                            world.setBlockState(po, ORES.get(world.rand.nextInt(ORES.size())).getDefaultState());
                            timer = 0;
                        }
                    }
                }
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
        if (capability == CapabilityEnergy.ENERGY) return (T) storage;
        return super.getCapability(capability, facing);
    }

    @Override
    public EnumFacing getBlockOrientation() {
        return HudRender.getOrientationHoriz(getBlockMetadata());
    }

    @Override
    public String getDisplay() {
        return storage.getEnergyStored() + " FE";
    }

    @Override
    public boolean isBlockAboveAir() {
        return getWorld().isAirBlock(pos.up());
    }

    @Override
    public BlockPos getBlockPos() {
        return getPos();
    }
}
