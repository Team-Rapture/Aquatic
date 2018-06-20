package com.github.teamrapture.aquatic.client.render;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.client.render.hud.HudRender;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.tileentity.TileAquaNetController;
import com.github.teamrapture.aquatic.tileentity.TileAquaNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.opengl.GL11.*;

//TODO rendering of beams
public class RenderAquaNode extends TileEntitySpecialRenderer<TileAquaNode> {

    private static final ResourceLocation OXYGEN_BEAM = new ResourceLocation(Aquatic.MODID, "textures/entity/oxygen_beam.png");
    private static final ResourceLocation BUBBLE = new ResourceLocation(Aquatic.MODID, "textures/entity/bubble.png");
    private Minecraft mc = Minecraft.getMinecraft();


    @Override
    public void render(TileAquaNode te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        BlockPos blockPos = te.getPos();

        HudRender.renderHud(te, x, y, z);
        if (te.controllerPos != null && te.hasAquaController()) {
            TileEntity controllerTe = te.getWorld().getTileEntity(te.controllerPos);
            if (controllerTe != null && controllerTe instanceof TileAquaNetController) {
                TileAquaNetController controller = (TileAquaNetController) controllerTe;

                double sX = te.controllerPos.getX() - blockPos.getX() + x + 0.5;
                double sY = te.controllerPos.getY() - blockPos.getY() + y + 0.5;
                double sZ = te.controllerPos.getZ() - blockPos.getZ() + z + 0.5;

                this.renderBeam(sX, sY, sZ, x + 0.5, y + 0.625, z + 0.5, te.beamRenderTicks, partialTicks,
                        te.oxygen.getOxygenStored() != te.oxygen.getMaxOxygenStorage() && controller.oxygen.canSendOxygen(20));
            }
        }
        //System.out.printf("%s %s%n", partialTicks, alpha);
        //System.out.printf("%s %s %s%n", blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (AquaticConfig.machines.aquaNodeBeam && te.oxygen.getOxygenStored() != 0) {
            for (EntityPlayer player : te.playersInRange()) {
                if (te.hasFullArmor(player)) {
                    Vec3d pPos = player.getPositionVector();
                    double dX = player.posX - player.lastTickPosX;
                    double dY = player.posY - player.lastTickPosY;
                    double dZ = player.posZ - player.lastTickPosZ;
                    float invAlpha = (1f - partialTicks);
                    double pX = blockPos.getX() - pPos.x - x + dX * invAlpha;
                    double pY = blockPos.getY() - pPos.y - y + dY * invAlpha - (player.height / 2f);
                    double pZ = blockPos.getZ() - pPos.z - z + dZ * invAlpha;
                    renderBeam(x + 0.5, y + 0.625, z + 0.5, -pX, -pY, -pZ, te.beamRenderTicks, partialTicks, true);
                }
            }
        }
    }

    private void renderBeam(double x1, double y1, double z1, double x2, double y2, double z2, int ticks, double partialTicks, boolean renderBubbles) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.bindTexture(OXYGEN_BEAM);
        GlStateManager.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        GlStateManager.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        float f1 = 240.0F;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        float timing = (float) (ticks + partialTicks);
        //System.out.printf("%s %s %s%n", timing, ticks, partialTicks);
        float f3 = timing * 0.3F % 1.0F;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x2, (float) y2, (float) z2);
        Vec3d vec3d = new Vec3d(x1, y1, z1);
        Vec3d vec3d1 = new Vec3d(x2, y2, z2);
        Vec3d beamDirection = vec3d.subtract(vec3d1);
        double beamLength = beamDirection.lengthVector();
        beamDirection = beamDirection.normalize();
        float f5 = (float) Math.acos(beamDirection.y);
        float f6 = (float) Math.atan2(beamDirection.z, beamDirection.x);
        GlStateManager.rotate((((float) Math.PI / 2F) + -f6) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f5 * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
        double d1 = (double) timing * 0.05D * -1.5D;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int r1 = 104;
        int g1 = 217;
        int b1 = 255;
        int r2 = 8;
        int g2 = 193;
        int b2 = 255;
        double d2 = 0.2D;
        double d3 = 0.282D;
        double d4 = 0.0D + Math.cos(d1 + 2.356194490192345D) * 0.282D;
        double d5 = 0.0D + Math.sin(d1 + 2.356194490192345D) * 0.282D;
        double d6 = 0.0D + Math.cos(d1 + (Math.PI / 4D)) * 0.282D;
        double d7 = 0.0D + Math.sin(d1 + (Math.PI / 4D)) * 0.282D;
        double d8 = 0.0D + Math.cos(d1 + 3.9269908169872414D) * 0.282D;
        double d9 = 0.0D + Math.sin(d1 + 3.9269908169872414D) * 0.282D;
        double d10 = 0.0D + Math.cos(d1 + 5.497787143782138D) * 0.282D;
        double d11 = 0.0D + Math.sin(d1 + 5.497787143782138D) * 0.282D;
        double d12 = 0.0D + Math.cos(d1 + Math.PI) * 0.2D;
        double d13 = 0.0D + Math.sin(d1 + Math.PI) * 0.2D;
        double d14 = 0.0D + Math.cos(d1 + 0.0D) * 0.2D;
        double d15 = 0.0D + Math.sin(d1 + 0.0D) * 0.2D;
        double d16 = 0.0D + Math.cos(d1 + (Math.PI / 2D)) * 0.2D;
        double d17 = 0.0D + Math.sin(d1 + (Math.PI / 2D)) * 0.2D;
        double d18 = 0.0D + Math.cos(d1 + (Math.PI * 3D / 2D)) * 0.2D;
        double d19 = 0.0D + Math.sin(d1 + (Math.PI * 3D / 2D)) * 0.2D;
        double d20 = 0.0D;
        double d21 = 0.4999D;
        double d22 = (double) (-1.0F + f3);
        double d23 = beamLength * 2.5D + d22;
        bufferbuilder.pos(d12, beamLength, d13).tex(0.4999D, d23).color(r1, g1, b1, 255).endVertex();
        bufferbuilder.pos(d12, 0.0D, d13).tex(0.4999D, d22).color(r2, g2, b2, 255).endVertex();
        bufferbuilder.pos(d14, 0.0D, d15).tex(0.0D, d22).color(r2, g2, b2, 255).endVertex();
        bufferbuilder.pos(d14, beamLength, d15).tex(0.0D, d23).color(r2, g2, b2, 255).endVertex();
        bufferbuilder.pos(d16, beamLength, d17).tex(0.4999D, d23).color(r2, g2, b2, 255).endVertex();
        bufferbuilder.pos(d16, 0.0D, d17).tex(0.4999D, d22).color(r2, g2, b2, 255).endVertex();
        bufferbuilder.pos(d18, 0.0D, d19).tex(0.0D, d22).color(r2, g2, b2, 255).endVertex();
        bufferbuilder.pos(d18, beamLength, d19).tex(0.0D, d23).color(r1, g1, b1, 255).endVertex();
        double d24 = 0.0D;

        /*if (entity.ticksExisted % 2 == 0)
        {
            d24 = 0.5D;
        }*/

        bufferbuilder.pos(d4, beamLength, d5).tex(0.5D, d24 + 0.5D).color(r1, g1, b1, 255).endVertex();
        bufferbuilder.pos(d6, beamLength, d7).tex(1.0D, d24 + 0.5D).color(r1, g1, b1, 255).endVertex();
        bufferbuilder.pos(d10, beamLength, d11).tex(1.0D, d24).color(r1, g1, b1, 255).endVertex();
        bufferbuilder.pos(d8, beamLength, d9).tex(0.5D, d24).color(r1, g1, b1, 255).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();

        if (!renderBubbles) return;
        this.bindTexture(BUBBLE);
        // DO RENDERS FOR BUBBLES
        /*Vec3d pos1 = new Vec3d(x1,y1,z1);
        Vec3d pos2 = new Vec3d(x2,y2,z2);
        Vec3d halfWay = pos1.subtract(pos2).scale(0.5f).add(pos2).add(beamDirection.scale(-timing * 0.10 % 2));
        this.renderBubble(halfWay.x,halfWay.y,halfWay.z);*/
        Vec3d origin = new Vec3d(x1, y1, z1);
        float bubbleDist = 1;
        for (int i = 0; i <= beamLength / bubbleDist; i++) {
            double bubbleProg = (timing * 0.10) % bubbleDist + i * bubbleDist;
            if (bubbleProg > beamLength) break;
            Vec3d bubbleLoc = origin.add(beamDirection.scale(-bubbleProg));
            this.renderBubble(bubbleLoc.x, bubbleLoc.y, bubbleLoc.z);
        }
    }

    public void renderBubble(double x, double y, double z) {

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableBlend();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.3F);
        GlStateManager.rotate(180.0F - mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (mc.getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * -mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.16F, 0.16F, 0.16F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(-0.5D, -0.5D, 0.0D).tex(0D, 0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, -0.5D, 0.0D).tex(0D, 1D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, 0.5D, 0.0D).tex(1D, 1D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-0.5D, 0.5D, 0.0D).tex(1D, 0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.enableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    @Override
    public boolean isGlobalRenderer(TileAquaNode te) {
        return true;
    }
}
