package com.github.rapture.aquatic.client.gui.machines;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.gui.container.ContainerEnergyFiller;
import com.github.rapture.aquatic.tileentity.TileEnergyFiller;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class GuiEnergyFiller extends GuiContainer {

    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    public static ResourceLocation background = new ResourceLocation(Aquatic.MODID, "textures/gui/gui_energy_filler.png");
    public TileEnergyFiller tile;

    public GuiEnergyFiller(ContainerEnergyFiller containerEnergyFiller, TileEnergyFiller te) {
        super(containerEnergyFiller);
        tile = (TileEnergyFiller) te;
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

        if (this.isPointInRegion(82, 6, 14, 50, mouseX, mouseY)) {
            List<String> fluid = new ArrayList<String>();
            fluid.add(tile.storage.getEnergyStored() + " / " + tile.storage.getMaxEnergyStored() + "  FE");
            GuiUtils.drawHoveringText(fluid, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
    }

    public void renderOxygen() {
        if (tile.storage.getEnergyStored() > 0) {
            int i = 48;
            int j = tile.storage.getEnergyStored() * i / tile.storage.getMaxEnergyStored();
            drawTexturedModalRect(guiLeft + 33, guiTop + 64 - j, 180, 49 - j, 12, j);
        }
    }
}
