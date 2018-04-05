package com.github.rapture.aquatic.item.food;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.util.NameUtil;

import net.minecraft.item.ItemFood;

public class BaseFoodItem extends ItemFood {

	public BaseFoodItem(String name, int amount, boolean isWolfFood) {
		super(amount, isWolfFood);
		NameUtil.name(this, name);
		this.setCreativeTab(Aquatic.CREATIVE_TAB);
	}

}
