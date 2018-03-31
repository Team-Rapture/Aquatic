package com.github.rapture.aquatic.client.render.hud;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IHudSupport {

    EnumFacing getBlockOrientation();

    boolean isBlockAboveAir();

    BlockPos getBlockPos();

    String getDisplay();
}