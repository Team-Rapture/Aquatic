package com.github.rapture.aquatic.client;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.init.AquaticItems;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.EntityEquipmentSlot;
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
            //GlStateManager.setFog(GlStateManager.FogMode.EXP);
            GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
            e.setDensity(0.06f);
            GlStateManager.setFogStart(30f);
            GlStateManager.setFogEnd(70f);
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.RenderFogEvent e) {
        /*if (e.getState().getBlock() == AquaticBlocks.AQUA_WATER_BLOCK) {
            GlStateManager.setFogStart(20.0f);
            GlStateManager.setFogEnd(30.0f);
            //e.setCanceled(true);
        }*/
    }

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.FogColors e) {
        if (e.getState().getBlock() == AquaticBlocks.AQUA_WATER_BLOCK) {
            e.setRed(1f / 255f);
            e.setGreen(36f / 255f);
            e.setBlue(92f / 255f);
            //e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void waterOverlayRender(RenderBlockOverlayEvent event) {
        if(event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER
                && event.getPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == AquaticItems.SCUBA_HELEMT) {
            event.setCanceled(true);
        }
    }
}
