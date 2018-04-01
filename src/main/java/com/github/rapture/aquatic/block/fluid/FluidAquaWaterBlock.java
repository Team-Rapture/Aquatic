package com.github.rapture.aquatic.block.fluid;

import com.github.rapture.aquatic.proxy.CommonProxy;
import com.github.rapture.aquatic.util.ICustomModelProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;

public class FluidAquaWaterBlock extends BlockFluidClassic implements ICustomModelProvider {

    public FluidAquaWaterBlock() {
        super(CommonProxy.AQUA_WATER, Material.WATER);
        this.setRegistryName(CommonProxy.AQUA_WATER.getName());
        this.setUnlocalizedName(fluidName);
    }

    @Override
    public void initModel() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }
}
