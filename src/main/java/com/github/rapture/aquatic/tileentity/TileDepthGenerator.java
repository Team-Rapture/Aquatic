package com.github.rapture.aquatic.tileentity;

import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.client.render.hud.IHudSupport;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.util.CustomEnergyStorage;
import com.github.rapture.aquatic.util.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileDepthGenerator extends TileEntityBase implements IHudSupport {

    private CustomEnergyStorage storage = new CustomEnergyStorage(1000000);
    private static List<Block> ores = new ArrayList<>();
    private int timer = 0;

    public static void init() {
        for (String string : OreDictionary.getOreNames()) {
            if (string.toLowerCase().startsWith("ore")) {
                ores.add(Block.getBlockFromItem(OreDictionary.getOres(string).get(0).getItem()));
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        timer = nbt.getInteger("timer");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        storage.writeToNBT(nbt);
        nbt.setInteger("timer", timer);
        return super.writeToNBT(nbt);
    }

    @Override
    public void update() {
        super.update();
        if (world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) {
            for (BlockPos po : BlockPos.getAllInBox(pos.getX() - 5, pos.getY(), pos.getZ() - 5, pos.getX() + 5, pos.getY(), pos.getZ() + 5)) {
                if (world.isAirBlock(po)) {
                    if (storage.getEnergyStored() >= AquaticConfig.depthUsage && ores.size() > 0) {
                        timer++;
                        if (timer >= 20 * 15) {
                            storage.extractEnergy(AquaticConfig.depthUsage, false);
                            world.setBlockState(po, ores.get(world.rand.nextInt(ores.size())).getDefaultState());
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
