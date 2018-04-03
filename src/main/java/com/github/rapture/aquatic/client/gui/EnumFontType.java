package com.github.rapture.aquatic.client.gui;

import java.awt.*;

public enum EnumFontType {

    DEFAULT(9, Color.WHITE.getRGB()),
    HEADING_1(14, Color.LIGHT_GRAY.getRGB()),
    HEADING_6(9, Color.GRAY.getRGB());

    private int fontHeight, fontColor;

    EnumFontType(int fontHeight, int fontColor) {
        this.fontHeight = fontHeight;
        this.fontColor = fontColor;
    }

    EnumFontType(int fontHeight) {
        this(fontHeight, Color.WHITE.getRGB());
    }

    public static EnumFontType byIndex(int index) {
        return index <= 0 || index >= values().length ? DEFAULT : values()[index];
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public int getFontColor() {
        return fontColor;
    }
}
