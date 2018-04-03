package com.github.rapture.aquatic.client.render;

import com.github.rapture.aquatic.tileentity.TileSolutionTank;
import com.github.rapture.aquatic.util.FluidUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidTank;

public class RenderSolutionTank extends TileEntitySpecialRenderer<TileSolutionTank> {

    @Override
    public void render(TileSolutionTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        if (te != null) {
            final FluidTank fluid = te.tank;
            if (fluid != null && fluid.getFluid() != null && fluid.getFluidAmount() > 0) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();

                FluidUtils.translateAgainstPlayer(te.getPos(), false);
                FluidUtils.renderFluid(fluid.getFluid(), te.getPos(), 0.06d, 0.08d, 0.06d, 0.130d, 0.0d, 0.130d, 0.75d, (double) fluid.getFluidAmount() / (double) fluid.getCapacity() * 0.84d, 0.75d);

                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
}
