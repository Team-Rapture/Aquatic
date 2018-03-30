package com.github.rapture.aquatic.creativetab;

import com.github.rapture.aquatic.Aquatic;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs {

    protected static final String BACKGROUND_IMAGE_SEARCHBAR = "item_search.png";

    private ItemStack icon = ItemStack.EMPTY;
    private boolean hasSearchBar = false;
    private boolean displayRandom = true;
    private NonNullList<ItemStack> displayStacks = NonNullList.create();

    /**
     * @param label the lang key for this tab, the final key will be {@code itemGroup.label.name}
     * @param searchBarEnabled if the tab should have a search bar;<br/>this will also automatically set the background texture to <b>{@code item_search.png}</b>
     */
    public CreativeTab(String label, boolean searchBarEnabled) {
        super(label + ".name");
        this.hasSearchBar = searchBarEnabled;
        if (searchBarEnabled) this.setBackgroundImageName(BACKGROUND_IMAGE_SEARCHBAR);
    }

    public void setHasSearchBar(boolean hasSearchBar) {
        this.hasSearchBar = hasSearchBar;
    }

    /**
     * @param label the lang key for this tab, the final key will be {@code itemGroup.label.name}
     */
    public CreativeTab(String label) {
        this(label, false);
    }

    public void setDisplayFromList(NonNullList<ItemStack> displayList) {
        Preconditions.checkNotNull(displayList);
        this.displayStacks = displayList;
        this.displayRandom = true;
    }

    /**
     * Used to set a CreativeTab's display icon. use {@link ItemStack#EMPTY} to display a random Item from
     * the Tab's item list.
     */
    public void setIcon(ItemStack icon) {
        if (icon.isEmpty()) this.displayRandom = true;
        else {
            this.displayRandom = false;
            icon.setCount(1);
        }
        this.icon = icon;
    }

    public void setIcon(Item item) {
        setIcon(new ItemStack(item));
    }

    public void setIcon(Block block) {
        setIcon(new ItemStack(block));
    }

    @Override
    public boolean hasSearchBar() {
        return this.hasSearchBar;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getIconItemStack() {
        this.updateDisplayStack();
        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    private void updateDisplayStack() {
        if (this.icon.isEmpty() || this.displayRandom && Minecraft.getSystemTime() % 120 == 0) {
            if(this.displayStacks.isEmpty()) {
                this.displayAllRelevantItems(displayStacks);
            }
            this.icon = ItemStack.EMPTY;
            if(!displayStacks.isEmpty()) try {
                for(int i = 0; i < displayStacks.size(); i++) {
                    ItemStack listStack = displayStacks.get(i);
                    if(!listStack.isEmpty() && listStack.isItemEqual(this.icon)) {
                        int nextIndex = i + 1;
                        if(nextIndex > displayStacks.size()) nextIndex = 0;
                        this.icon = displayStacks.get(nextIndex);
                    }
                }
            }
            catch (Exception e) {
                //NO-OP
            }
            if(this.icon.isEmpty()) {
                Aquatic.getLogger().warn("found empty Itemstack for CreativeTab " + this.getTabLabel() + ", defaulting to " + Items.DIAMOND.getRegistryName());
                this.icon = new ItemStack(Items.DIAMOND);
                this.displayRandom = false;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem() {
        return this.getIconItemStack();
    }
}
