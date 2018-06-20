package com.github.teamrapture.aquatic.client;

import com.github.teamrapture.aquatic.client.gui.GuiScreenGuide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber
public class KeyHandler {

    public static final KeyBinding KEY_GUIDE = ClientUtil.getKeyBinding("guide", Keyboard.KEY_I);
    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (mc.currentScreen == null) {
            //query key bindings here
            if (KEY_GUIDE.isPressed()) GuiScreenGuide.openPage(null);
        }
    }
}
