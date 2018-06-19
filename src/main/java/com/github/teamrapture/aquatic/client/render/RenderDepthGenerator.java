package com.github.teamrapture.aquatic.client.render;

import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.tileentity.TileDepthGenerator;
import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderDepthGenerator extends TileEntitySpecialRenderer<TileDepthGenerator> {

    @Override
    public void render(TileDepthGenerator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        HudRender.renderHud(te, x, y, z);
    }
}
