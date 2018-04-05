package com.github.rapture.aquatic.network;

import com.github.rapture.aquatic.init.AquaticItems;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {

    }

    @SubscribeEvent
    public void waterOverlayRender(RenderBlockOverlayEvent event) {
        if(event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER
                && event.getPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == AquaticItems.SCUBA_HELEMT) {
            event.setCanceled(true);
        }
    }
}
