package com.github.teamrapture.aquatic.block.fluid;

import com.deflatedpickle.picklelib.api.IFishable;
import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.proxy.CommonProxy;
import com.github.teamrapture.aquatic.util.ICustomModelProvider;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@Optional.Interface(modid = "picklelib", iface = "com.deflatedpickle.picklelib.api.IFishable")
@Mod.EventBusSubscriber(modid = Aquatic.MODID)
public class FluidAquaWaterBlock extends BlockFluidClassic implements ICustomModelProvider, IFishable {

    public FluidAquaWaterBlock() {
        super(CommonProxy.AQUATIC_WATER, Material.WATER);
        this.setRegistryName(fluidName);
        this.setTranslationKey(fluidName);
        this.setLightOpacity(3);
        this.setLightLevel(0.5F);
        this.disableStats();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        //NO-OP: make blocks not affected by explosions at all, yet don't block explosion power so you can go crazy in the aquatic dimension
    }

    @Override
    public boolean shouldSideBeRendered(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        IBlockState blockState = world.getBlockState(pos.offset(side));
        if(blockState.isFullBlock() && blockState.getMaterial() == Material.GLASS) return false;
        if(side == EnumFacing.UP) return (!isSourceBlock(world, pos) || !blockState.isFullBlock()) && !blockState.getMaterial().isLiquid();
        return super.shouldSideBeRendered(blockState, world, pos, side);
    }

    //ExplosiveFishing integration
    @Optional.Method(modid = "picklelib")
    @Override
    public boolean isFishable() {
        return AquaticConfig.compatibility.explosiveFishingInAquaticDimension;
    }

    @Override
    public boolean isSourceBlock(IBlockAccess world, BlockPos pos) {
        if(AquaticConfig.experimental.aquaWaterAcceptsAnyLiquidSource) {
            IBlockState state = world.getBlockState(pos);
            return state.getMaterial().isLiquid() && (!state.getPropertyKeys().contains(LEVEL) || state.getValue(LEVEL) == 0) && (!state.getPropertyKeys().contains(BlockLiquid.LEVEL) || state.getValue(BlockLiquid.LEVEL) == 0);
        }
        else return super.isSourceBlock(world, pos);
    }

    @SubscribeEvent(priority = EventPriority.HIGH) //needs high priority to not interfere with other mods, for example finite fluids
    public static void onCreateSourceBlock(BlockEvent.CreateFluidSourceEvent event) {
        if(event.getState().getBlock() == AquaticBlocks.AQUA_WATER_BLOCK) {
            event.setResult(Event.Result.ALLOW);
        }
    }
}
