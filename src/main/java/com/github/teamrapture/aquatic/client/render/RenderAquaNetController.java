package com.github.teamrapture.aquatic.client.render;

import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.tileentity.TileAquaNetController;
import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderAquaNetController extends TileEntitySpecialRenderer<TileAquaNetController> {

    @Override
    public void render(TileAquaNetController te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        HudRender.renderHud(te, x, y, z);
    }
}