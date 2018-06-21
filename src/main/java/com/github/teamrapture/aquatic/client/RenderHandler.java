package com.github.teamrapture.aquatic.client;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.init.AquaticItems;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Aquatic.MODID, value = {Side.CLIENT})
public class RenderHandler {

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.FogDensity e) {
        if (e.getState().getBlock() == AquaticBlocks.AQUA_WATER_BLOCK) {
            GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
            double partial = MathHelper.clamp(e.getEntity().posY / (e.getEntity().world.getHeight() * 0.7D), 0.0D, 1.0D);
            e.setDensity((float) (0.06D * (1.0D - partial)));
            GlStateManager.setFogStart((float) Math.max(60.0D * (partial * partial), 0.7D));
            GlStateManager.setFogEnd((float) Math.max(250.0D * (partial * partial), 2.2D));
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.FogColors e) {
        if (e.getState().getBlock() == AquaticBlocks.AQUA_WATER_BLOCK) {
            e.setRed(1f / 255f);
            e.setGreen(36f / 255f);
            e.setBlue(92f / 255f);
        }
    }

    @SubscribeEvent
    public static void waterOverlayRender(RenderBlockOverlayEvent event) {
        if(event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER && event.getPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == AquaticItems.SCUBA_HELEMT) {
            event.setCanceled(true);
        }
    }
}
