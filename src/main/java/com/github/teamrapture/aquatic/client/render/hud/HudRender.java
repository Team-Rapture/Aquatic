package com.github.teamrapture.aquatic.client.render.hud;

import net.minecraft.util.EnumFacing;

public class HudRender {

    public static final int MASK_ORIENTATION_HORIZONTAL = 0x3;

    public static void renderHud(IHudSupport hudSupport, double x, double y, double z) {
        renderHud(hudSupport, x, y, z, 0.0f, false);
    }

    public static void renderHud(IHudSupport support, double x, double y, double z, float scale, boolean faceVert) {
        String display = support.getDisplay();
        EnumFacing orientation = support.getBlockOrientation();
        HudRenderHelper.HudPlacement hudPlacement = support.isBlockAboveAir() ? HudRenderHelper.HudPlacement.HUD_ABOVE : HudRenderHelper.HudPlacement.HUD_ABOVE_FRONT;
        HudRenderHelper.HudOrientation orientation1 = HudRenderHelper.HudOrientation.HUD_TOPLAYER;
        HudRenderHelper.renderHud(display, hudPlacement, orientation1, orientation, x, y - 0.5, z, 1.3f + scale);
    }

    public static EnumFacing getOrientationHoriz(int metadata) {
        return EnumFacing.VALUES[(metadata & MASK_ORIENTATION_HORIZONTAL) + 2];
    }
}