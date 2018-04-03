package com.github.rapture.aquatic.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;

public class GuiScrollableList extends Gui {

    public static final int SCROLL_BAR_BG_COLOR = new Color(128, 128, 128).getRGB();
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final int BOUNDARY_COLOR = new Color(50, 230, 230).getRGB();
    private static final int BACKGROUND_COLOR = new Color(0, 0, 0).getRGB();
    private static final int SCROLL_BAR_COLOR = new Color(120, 200, 200).getRGB();
    private static final int SCROLL_FACTOR = 3;
    private final List<String> textLines = Lists.newArrayList();
    private int TEXT_MARGIN = 4;
    private int TEXT_HEIGHT = 2;
    private int SCROLLBAR_WIDTH = 10;
    private int x, y, width, height, currentIndex, maxIndex, maxLinesOnScreen;
    private FontRenderer fontRenderer = mc.fontRenderer;
    private int timePressed = 0;
    private boolean isMouseDraggingScrollBar;
    private int prevMouseY;

    public GuiScrollableList(int x, int y, int width, int height, List<String> text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //TODO remove dirty fix (after modjam)
        text.forEach(s -> {
            int spaces = 0;
            while (s.startsWith(">")) {
                s = s.substring(1);
                spaces += 2;
            }
            addLines(spaces, s);
        });

        currentIndex = 0;
        final int tempHeadingHeightOffset = EnumFontType.HEADING_1.getFontHeight() - EnumFontType.DEFAULT.getFontHeight(); //TODO remove in later version and parse markdown better!
        maxLinesOnScreen = (height - TEXT_MARGIN * 2 - tempHeadingHeightOffset) / (fontRenderer.FONT_HEIGHT + TEXT_HEIGHT);

        maxIndex = Math.max(0, textLines.size() - maxLinesOnScreen);
    }

    /**
     * @param marginSpaces useful for paragraphs etc
     */
    public void addLines(int marginSpaces, String... text) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < marginSpaces; i++) builder.append(" ");
        final String indent = builder.toString();
        int maxWidth = width - TEXT_MARGIN * 2 - fontRenderer.getStringWidth(indent) - SCROLLBAR_WIDTH - 1;
        for (String line : text) {
            fontRenderer.listFormattedStringToWidth(line, maxWidth).forEach(s -> textLines.add(indent + s));
        }
        maxIndex = Math.max(0, textLines.size() - maxLinesOnScreen);
        this.currentIndex = MathHelper.clamp(currentIndex, 0, maxIndex);
    }

    public void handleMouseInput() {
        int amount = Mouse.getEventDWheel();
        if (amount != 0) {
            if (amount > 0) amount = -1;
            else amount = 1;
            scroll(amount);
        }
    }

    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (isMouseDraggingScrollBar) {
            int diff = mouseY - prevMouseY;

            int scrollbarHeight = (int) (((float) maxLinesOnScreen) / textLines.size() * height);
            int maxDiff = scrollbarHeight / (textLines.size() - maxLinesOnScreen);

            if (diff > maxDiff) {
                scroll(1);
            } else if (diff < -maxDiff) {
                scroll(-1);
            }
            prevMouseY = mouseY;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.isMouseDraggingScrollBar) this.isMouseDraggingScrollBar = false;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (textLines.size() > maxLinesOnScreen) {
            int scrollbarX = x + width - SCROLLBAR_WIDTH - 1;
            int scrollbarHeight = (int) (((float) maxLinesOnScreen) / textLines.size() * height) - 2;
            int scrollOffset = (int) (((float) currentIndex) / (textLines.size() - maxLinesOnScreen) * (height - scrollbarHeight));
            int scrollbarY = y + scrollOffset;
            if (mouseX >= scrollbarX && x <= scrollbarX + SCROLLBAR_WIDTH - 1) {
                if (mouseY >= scrollbarY && mouseY <= scrollbarY + scrollbarHeight) {
                    isMouseDraggingScrollBar = true;
                    prevMouseY = mouseY;
                } else {
                    //TODO insta-scroll
                }
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        int scrollAmount = 0;
        if (keyCode == Keyboard.KEY_DOWN) scrollAmount = 1;
        else if (keyCode == Keyboard.KEY_UP) scrollAmount = -1;
        scroll(scrollAmount);
    }

    protected void scroll(int rawAmount) {
        if (textLines.size() > maxLinesOnScreen)
            this.currentIndex = MathHelper.clamp(currentIndex + (rawAmount * SCROLL_FACTOR), 0, maxIndex);
    }

    public void draw() {
        //background
        drawRect(x, y, x + width, y + height, BOUNDARY_COLOR);
        drawRect(x + 1, y + 1, x + width - 1, y + height - 1, BACKGROUND_COLOR);

        //text
        //int drawnTextHeight = 0;
        for (int i = currentIndex; i < Math.min(maxLinesOnScreen + currentIndex, textLines.size()); i++) {
            /*
            String line = textLines.get(i);
            int fontSize = fontRenderer.FONT_HEIGHT;
            int fontTypeIndex = 0;
            while (line.startsWith("#")) {
                line = line.substring(1);
                fontTypeIndex++;
            }
            EnumFontType fontType = EnumFontType.values()[fontTypeIndex];
            fontRenderer.FONT_HEIGHT = fontType.getFontColor();
            fontRenderer.drawString(line, x + TEXT_MARGIN, y + TEXT_MARGIN + drawnTextHeight, fontType.getFontColor());
            drawnTextHeight += (fontType.getFontHeight() + TEXT_HEIGHT);
            fontRenderer.FONT_HEIGHT = fontSize; //restore old size
            */
            int index = (i - currentIndex);
            EnumFontType fontType = EnumFontType.DEFAULT;
            if (index == 0) fontType = EnumFontType.HEADING_1;
            fontRenderer.FONT_HEIGHT = fontType.getFontHeight();
            fontRenderer.drawString(textLines.get(i), x + TEXT_MARGIN, y + TEXT_MARGIN + (fontRenderer.FONT_HEIGHT + TEXT_HEIGHT) * index, fontType.getFontColor());
            fontRenderer.FONT_HEIGHT = 9;
        }

        if (textLines.size() > maxLinesOnScreen) { //draw scroll bar
            int scrollbarHeight = (int) (((float) maxLinesOnScreen) / textLines.size() * height) - 2;
            int scrollOffset = Math.max(0, (int) (((float) currentIndex) / (textLines.size() - maxLinesOnScreen) * (height - scrollbarHeight)));
            drawRect(x + width - SCROLLBAR_WIDTH - 1, y + 1, x + width - 1, y + height - 1, SCROLL_BAR_BG_COLOR);
            drawRect(x + width - SCROLLBAR_WIDTH - 1, y + scrollOffset + 1, x + width - 1, y + scrollbarHeight + scrollOffset - 1, SCROLL_BAR_COLOR);
        }
    }

    public void update() {
        boolean key_down = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
        if (key_down || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            if (timePressed > 10) {
                if (key_down) scroll(1);
                else scroll(-1);
            }
            timePressed++;
        } else timePressed = 0;
    }
}
