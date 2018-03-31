package com.github.rapture.aquatic.client.render;

import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.tileentity.TileAquaNode;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

//TODO Render the things for the aqua node
public class RenderAquaNode extends TileEntitySpecialRenderer<TileAquaNode> {

    @Override
    public void render(TileAquaNode te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        HudRender.renderHud(te, x, y, z);
    }
}
