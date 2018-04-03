package com.github.rapture.aquatic.client.gui;

import com.github.rapture.aquatic.client.guide.GuideReader;
import jline.internal.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GuiScreenGuide extends GuiScreen {

    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final int
            MARGIN_TOP = 20,
            MARGIN_BOTTOM = 40,
            MARGIN_SIDE = 30,
            INDEX_WIDTH = 160,
            INDEX_HEIGHT = 18,
            INDEX_MARGIN = 2,
            ENTRY_MARGIN = 4,
            CYAN = 0x30E6E4FF,
            BLACK = 0x000000FF,
            LIGHT_GRAY = 0x808080FF;

    private static String lastEntry = null;
    private static String lastChild = null;

    private GuiScrollableList textBox;
    /**
     * what page we are displaying
     */
    private String selectedEntry;
    private String selectedChild = null;

    private GuiScreenGuide(@Nullable ResourceLocation selectedPage) {
        if (selectedPage != null) this.selectedEntry = selectedPage.toString();
        else {
            this.selectedEntry = lastEntry;
            this.selectedChild = lastChild;
        }
    }

    public static void openPage(@Nullable ResourceLocation page) {
        if (MC.currentScreen instanceof GuiScreenGuide)
            ((GuiScreenGuide) MC.currentScreen).setSelected(page != null ? page.toString() : null);
        else MC.displayGuiScreen(new GuiScreenGuide(page));
    }

    @Override
    public void onGuiClosed() {
        lastEntry = this.selectedEntry;
        lastChild = this.selectedChild;
    }

    public void setSelected(@Nullable String entry) {
        if (entry == null || GuideReader.GUIDE_INDEX.containsKey(entry)) {
            if (entry != null && entry.equals(this.selectedEntry)) { //unselect
                this.selectedEntry = null;
            } else this.selectedEntry = entry;
            this.selectedChild = null;
        } else {
            boolean hasKey = false;
            keys:
            for (String key : GuideReader.GUIDE_INDEX.keySet()) {
                if (GuideReader.CHILD_COUNT.get(key) > 0) {
                    NBTTagList tagList = GuideReader.GUIDE_INDEX.get(key).getTagList("child_elements", Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        if (entry.equals(tagList.getCompoundTagAt(i).getString("id"))) {
                            this.selectedEntry = key;
                            this.selectedChild = entry;
                            hasKey = true;
                            break keys;
                        }
                    }
                }
            }
            if (!hasKey) {
                this.selectedEntry = null;
                this.selectedChild = null;
            }
        }
        this.initGui();
    }

    @Override
    public void initGui() {
        GuideReader.init();
        List<String> lines = GuideReader.readPage(this.selectedChild != null ? this.selectedChild : this.selectedEntry);
        this.textBox = new GuiScrollableList(MARGIN_SIDE + INDEX_WIDTH + INDEX_MARGIN * 2 + ENTRY_MARGIN, MARGIN_TOP + ENTRY_MARGIN, width - (MARGIN_SIDE * 2) - ENTRY_MARGIN - INDEX_WIDTH - 12, height - MARGIN_TOP - MARGIN_BOTTOM - 16, lines);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        textBox.update();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawWorldBackground(0);
        drawRect(MARGIN_SIDE - 1, MARGIN_TOP - 1, width - MARGIN_SIDE + 1, height - MARGIN_BOTTOM + 1, CYAN);
        drawRect(MARGIN_SIDE, MARGIN_TOP, width - MARGIN_SIDE, height - MARGIN_BOTTOM, BLACK);

        int x = MARGIN_SIDE + INDEX_MARGIN + 1;
        int y = MARGIN_TOP + ENTRY_MARGIN + 1;
        drawRect(x - 1, y - 1, x + INDEX_WIDTH, y, BLACK); //separator
        Iterator<String> iterator = GuideReader.GUIDE_INDEX.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            NBTTagCompound entryNBT = GuideReader.GUIDE_INDEX.get(key);
            drawEntry(x, y, INDEX_WIDTH, INDEX_HEIGHT, entryNBT);
            y += INDEX_HEIGHT + 1;
            if (key.equals(selectedEntry) && entryNBT.hasKey("child_elements", Constants.NBT.TAG_LIST)) {
                NBTTagList childElements = entryNBT.getTagList("child_elements", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < childElements.tagCount(); i++) {
                    NBTTagCompound childEntryNBT = childElements.getCompoundTagAt(i);
                    drawEntry(x + INDEX_MARGIN, y, INDEX_WIDTH - INDEX_MARGIN, INDEX_HEIGHT, childEntryNBT);
                    y += INDEX_HEIGHT + 1;
                }
            }
        }

        this.textBox.draw();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawEntry(int x, int y, int width, int height, NBTTagCompound entry) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawRect(x, y, x + width, y + height, LIGHT_GRAY);
        drawRect(x, y + height, x + width, y + height + 1, BLACK); //separator

        if (entry.hasKey("icon", Constants.NBT.TAG_STRING)) {
            try {
                RenderHelper.enableGUIStandardItemLighting();
                Item item = Item.getByNameOrId(entry.getString("icon"));
                if (item != null) MC.getRenderItem().renderItemIntoGUI(new ItemStack(item), x, y);
                RenderHelper.disableStandardItemLighting();
            } catch (Exception e) {
                //ignore
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        MC.fontRenderer.drawString(I18n.format("entry." + entry.getString("id").replace(":", ".") + ".caption"), x + 20, y + (INDEX_HEIGHT - MC.fontRenderer.FONT_HEIGHT) / 2 + 1, BLACK);
        //if(entry.getBoolean("tooltip") && [mouse over entry]) drawHoveringText(); TODO tooltip
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.textBox.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textBox.mouseClicked(mouseX, mouseY, mouseButton);
        int xPos = MARGIN_SIDE + INDEX_MARGIN + 1;
        if (mouseX >= xPos && mouseX <= xPos + INDEX_WIDTH) {
            this.setSelected(this.getIndexAtPos(mouseY));
        }
    }

    @Nullable
    private String getIndexAtPos(int yPos) {
        int y = MARGIN_TOP + ENTRY_MARGIN + 1;
        if (yPos >= y) {
            for (String key : GuideReader.GUIDE_INDEX.keySet()) {
                y += INDEX_HEIGHT + 1;
                if (y > yPos) return key;
                else if (key.equals(selectedEntry)) {
                    for (int i = 0; i < GuideReader.CHILD_COUNT.get(key); i++) {
                        y += INDEX_HEIGHT + 1;
                        if (y > yPos)
                            return GuideReader.GUIDE_INDEX.get(key).getTagList("child_elements", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(i).getString("id");
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.textBox.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.textBox.mouseReleased(mouseX, mouseY, state);
    }
}
