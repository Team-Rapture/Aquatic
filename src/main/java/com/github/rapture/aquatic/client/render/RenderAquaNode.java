package com.github.rapture.aquatic.client.render;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.tileentity.TileAquaNode;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

//TODO rendering of beams
public class RenderAquaNode extends TileEntitySpecialRenderer<TileAquaNode> {

    public ResourceLocation loc = new ResourceLocation(Aquatic.MODID + "textures/blocks/coral_reef.png");

    @Override
    public void render(TileAquaNode te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        HudRender.renderHud(te, x, y, z);
        if (te.controllerPos != null && te.hasAquaController) {

        }

        if (AquaticConfig.aquaNodeBeam) {
            if (te.playersInRange() != null) {
                for (EntityPlayer player : te.playersInRange()) {
                    if (te.hasFullArmor(player)) {

                    }
                }
            }
        }
    }

    @Override
    public boolean isGlobalRenderer(TileAquaNode te) {
        return true;
    }
}
