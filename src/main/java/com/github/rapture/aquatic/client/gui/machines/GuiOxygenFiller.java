package com.github.rapture.aquatic.client.gui.machines;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.gui.container.ContainerOxygenFiller;
import com.github.rapture.aquatic.tileentity.TileOxygenFiller;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class GuiOxygenFiller extends GuiContainer {

    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    public static ResourceLocation background = new ResourceLocation(Aquatic.MODID, "textures/gui/gui_oxygen_filler.png");
    public TileOxygenFiller tile;

    public GuiOxygenFiller(ContainerOxygenFiller containerOxygenFiller, TileOxygenFiller te) {
        super(containerOxygenFiller);
        tile = (TileOxygenFiller) te;
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

        renderOxygen();

        if (this.isPointInRegion(24, 12, 18, 50, mouseX, mouseY)) {
            List<String> fluid = new ArrayList<String>();
            fluid.add(tile.oxygen.getOxygenStored() + " / " + tile.oxygen.getMaxOxygenStorage() + "  Oxygen");
            GuiUtils.drawHoveringText(fluid, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
    }

    public void renderOxygen() {
        if (tile.oxygen.getOxygenStored() > 0) {
            int i = 48;
            int j = tile.oxygen.getOxygenStored() * i / tile.oxygen.getMaxOxygenStorage();
            drawTexturedModalRect(guiLeft + 25, guiTop + 61 - j, 179, 51 - j, 16, j);
        }
    }
}
