package com.github.rapture.aquatic.client.gui.machines;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.gui.container.ContainerSolutionTank;
import com.github.rapture.aquatic.proxy.CommonProxy;
import com.github.rapture.aquatic.tileentity.TileSolutionTank;
import com.github.rapture.aquatic.util.FluidUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiSolutionTank extends GuiContainer {

    public static ResourceLocation background = new ResourceLocation(Aquatic.MODID, "textures/gui/solution_tank_gui.png");

    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    public TileSolutionTank tile;

    public GuiSolutionTank(ContainerSolutionTank containerSolutionTank, TileSolutionTank te) {
        super(containerSolutionTank);
        tile = (TileSolutionTank) te;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        renderFluid();

        if (this.isPointInRegion(82, 6, 14, 50, mouseX, mouseY)) {
            List<String> fluid = new ArrayList<String>();
            fluid.add(tile.tank.getFluidAmount() + " / " + tile.tank.getCapacity() + "  MB");
            if(tile.tank.getFluidAmount() > 0 && tile.tank.getFluid().isFluidEqual(new FluidStack(CommonProxy.AQUA_WATER, 1000))) {
                if (tile.tank.getFluid().tag != null) {
                    fluid.add(tile.tank.getFluid().tag.getDouble("ph") + " PH Level");
                }
            }
            GuiUtils.drawHoveringText(fluid, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
    }

    public void renderFluid() {
        if(tile.tank != null && tile.tank.getFluidAmount() > 0) {
            int i = tile.tank.getFluidAmount() * 48 / tile.tank.getCapacity();
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            final int color = tile.tank.getFluid().getFluid().getColor();
            final int brightness = mc.world.getCombinedLight(new BlockPos(guiLeft + 10, guiTop + 7, 0), tile.tank.getFluid().getFluid().getLuminosity());
            final Minecraft mc = Minecraft.getMinecraft();
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder buffer = tessellator.getBuffer();

            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            FluidUtils.setupRenderState();

            final TextureAtlasSprite still = mc.getTextureMapBlocks().getTextureExtry(tile.tank.getFluid().getFluid().getStill().toString());
            FluidUtils.addTexturedQuad(buffer, still, guiLeft + 83, guiTop + 55 - i, 0, 12, i, 0, EnumFacing.NORTH, color, brightness);

            tessellator.draw();

            FluidUtils.cleanupRenderState();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
}
