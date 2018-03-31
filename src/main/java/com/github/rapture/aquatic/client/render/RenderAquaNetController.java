package com.github.rapture.aquatic.client.render;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.models.ModelAquaNetController;
import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.tileentity.TileAquaNetController;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderAquaNetController extends TileEntitySpecialRenderer<TileAquaNetController> {

    private static final ResourceLocation modelTexture = new ResourceLocation(Aquatic.MODID, "textures/blocks/aquanet_core.png");

    @Override
    public void render(TileAquaNetController te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glScalef(-1F, -1F, 1f);
        GL11.glTranslatef(-.5F, -1.5F, .5F);
        GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        bindTexture(modelTexture);
        ModelAquaNetController.INSTANCE.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        HudRender.renderHud(te, x, y, z);
    }
}
