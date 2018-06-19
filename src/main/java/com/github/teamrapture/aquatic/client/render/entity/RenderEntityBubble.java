package com.github.teamrapture.aquatic.client.render.entity;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.entity.EntityWaterBubble;
import com.github.teamrapture.aquatic.entity.EntityWaterBubble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class RenderEntityBubble extends Render<EntityWaterBubble> {

    public ResourceLocation bubble = new ResourceLocation(Aquatic.MODID, "textures/entity/bubble.png");

    public RenderEntityBubble(RenderManager manager) {
        super(manager);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(EntityWaterBubble entity) {
        return bubble;
    }

    @Override
    public void doRender(EntityWaterBubble entity, double x, double y, double z, float entityYaw, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        this.bindTexture(bubble);
        RenderHelper.enableStandardItemLighting();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.3F);
        GlStateManager.rotate(180.0F - mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) (mc.getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * -mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(1F, 1F, 1F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0D, 0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(0D, 1D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1D, 1D).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(1D, 0D).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.enableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }
}
