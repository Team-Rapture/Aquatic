package com.github.rapture.aquatic.client.render;

import com.github.rapture.aquatic.client.render.hud.HudRender;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.tileentity.TileAquaNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderAquaNode extends TileEntitySpecialRenderer<TileAquaNode> {

    public static final int MAX_LIGHT_X = 0xF000F0;
    public static final int MAX_LIGHT_Y = 0xF000F0;

    @Override
    public void render(TileAquaNode te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        HudRender.renderHud(te, x, y, z);
        if(te.controllerPos != null && te.hasAquaController) {
            renderLaser(te.getPos().getX() + 0.5, te.getPos().getY() + 0.5, te.getPos().getZ() + 0.5, te.controllerPos.getX() + 0.5, te.controllerPos.getY() + 0.5, te.controllerPos.getZ() + 0.5, 200, 0.5f, 0.15, new float[] {0.255f, 0.412f, 0.88f});
        }

        if(AquaticConfig.aquaNodeBeam) {
            if (te.playersInRange() != null) {
                for (EntityPlayer player : te.playersInRange()) {
                    renderLaser(te.getPos().getX() + 0.5, te.getPos().getY() + 0.5, te.getPos().getZ() + 0.5, player.getPosition().getX(), player.getPosition().getY() + 0.5, player.getPosition().getZ(), 240, 0.65f, 0.085, new float[]{0.000f, 1.000f, 1.000f});
                }
            }
        }
    }

    public static void renderLaser(double x1, double y1, double z1, double x2, double y2, double z2, double rotation, float alpha, double beamWidth, float[] rgb) {
        Tessellator tessy = Tessellator.getInstance();
        BufferBuilder render = tessy.getBuffer();
        World world = Minecraft.getMinecraft().world;

        float r = rgb[0];
        float g = rgb[1];
        float b = rgb[2];

        Vec3d vec1 = new Vec3d(x1, y1, z1);
        Vec3d vec2 = new Vec3d(x2, y2, z2);
        Vec3d combinedVec = vec2.subtract(vec1);

        double rot = rotation > 0 ? (360D * ((world.getTotalWorldTime() % rotation) / rotation)) : 0;
        double pitch = Math.atan2(combinedVec.y, Math.sqrt(combinedVec.x * combinedVec.x + combinedVec.z * combinedVec.z));
        double yaw = Math.atan2(-combinedVec.z, combinedVec.x);

        double length = combinedVec.lengthVector();

        GlStateManager.pushMatrix();

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        int func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
        float ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
        GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
        GlStateManager.translate(x1 - TileEntityRendererDispatcher.staticPlayerX, y1 - TileEntityRendererDispatcher.staticPlayerY, z1 - TileEntityRendererDispatcher.staticPlayerZ);
        GlStateManager.rotate((float) (180 * yaw / Math.PI), 0, 1, 0);
        GlStateManager.rotate((float) (180 * pitch / Math.PI), 0, 0, 1);
        GlStateManager.rotate((float) rot, 1, 0, 0);

        GlStateManager.disableTexture2D();
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        double width = beamWidth * 1;
        render.pos(length, width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(0, width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(0, -width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(length, -width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

        render.pos(length, -width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(0, -width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(0, width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(length, width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

        render.pos(length, width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(0, width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(0, width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(length, width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();

        render.pos(length, -width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(0, -width, width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(0, -width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        render.pos(length, -width, -width).tex(0, 0).lightmap(MAX_LIGHT_X, MAX_LIGHT_Y).color(r, g, b, alpha).endVertex();
        tessy.draw();

        GlStateManager.enableTexture2D();

        GlStateManager.alphaFunc(func, ref);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
