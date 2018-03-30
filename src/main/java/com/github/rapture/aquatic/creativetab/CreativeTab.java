package com.github.rapture.aquatic.creativetab;

import com.github.upcraftlp.foolslib.FoolsLib;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CreativeTab extends CreativeTabs {

    protected static final String BACKGROUND_IMAGE_SEARCHBAR = "item_search.png";

    private ItemStack icon = null;
    private boolean hasSearchBar = false;
    private boolean displayRandom = true;
    private List<ItemStack> displayStacks = new ArrayList<>();

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

    public void setDisplayFromList(List<ItemStack> displayList) {
        Preconditions.checkNotNull(displayList);
        this.displayStacks = displayList;
        this.displayRandom = true;
    }

    /**
     * Used to set a CreativeTab's display icon. use {@code null} to display a random Item from
     * the Tab's item list.
     */
    public void setIcon(@Nullable ItemStack icon) {
        if (icon == null) this.displayRandom = true;
		else {
			this.displayRandom = false;
			icon.stackSize = 1;
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
        if (this.icon == null || this.displayRandom && Minecraft.getSystemTime() % 120 == 0) {
            if(this.displayStacks.isEmpty()) {
                this.displayAllReleventItems(displayStacks);
            }
            this.icon = null;
            if(!displayStacks.isEmpty()) try {
                for(int i = 0; i < displayStacks.size(); i++) {
                    ItemStack listStack = displayStacks.get(i);
                    if(listStack != null && listStack.isItemEqual(this.icon)) {
                        int nextIndex = i + 1;
                        if(nextIndex > displayStacks.size()) nextIndex = 0;
                        this.icon = displayStacks.get(nextIndex);
                    }
                }
            }
            catch (Exception e) {
                //NO-OP
            }
            if(this.icon == null) {
                FoolsLib.getLogger().warn("found no display ItemStack for CreativeTab {}, defaulting to {}", this.getTabLabel(), Items.diamond.getUnlocalizedName().substring(5));
                this.icon = new ItemStack(Items.diamond);
                this.displayRandom = false;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getTabIconItem() {
        return this.getIconItemStack().getItem();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getIconItemDamage() {
        return this.getIconItemStack().getItemDamage();
    }
}
