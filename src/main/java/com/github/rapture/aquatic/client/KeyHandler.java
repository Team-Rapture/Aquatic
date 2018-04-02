package com.github.rapture.aquatic.client;

import com.github.rapture.aquatic.client.gui.GuiScreenGuide;
import com.github.rapture.aquatic.client.guide.GuideReader;
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
            if (KEY_GUIDE.isPressed()) GuiScreenGuide.openPage(GuideReader.INDEX);
        }
    }
}
