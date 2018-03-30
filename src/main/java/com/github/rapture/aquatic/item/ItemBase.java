package com.github.rapture.aquatic.item;

import com.github.rapture.aquatic.Aquatic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBase extends Item {

    private int subItemCount = 0;

    public ItemBase(String name) {
        super();
        this.setRegistryName(name);
        this.setUnlocalizedName(this.getRegistryName().getResourceDomain() + "." + name);
        this.setCreativeTab(Aquatic.CREATIVE_TAB);
    }

    public void setSubItemCount(int count) {
        this.subItemCount = count;
        this.setHasSubtypes(count > 0);
    }

    public int getSubItemCount() {
        return this.subItemCount;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(!this.isInCreativeTab(tab)) return;
        if (this.getSubItemCount() > 0) {
            for ( int i = 0; i < this.getSubItemCount(); i++ )
                items.add(new ItemStack(this, 1, i));
        }
        else {
            super.getSubItems(tab, items);
        }
    }

}
