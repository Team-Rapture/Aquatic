package com.github.teamrapture.aquatic.item.food;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.util.NameUtil;
import net.minecraft.item.ItemFood;

public class BaseFoodItem extends ItemFood {

	public BaseFoodItem(String name, int amount, boolean isWolfFood) {
		super(amount, isWolfFood);
		NameUtil.name(this, name);
		this.setCreativeTab(Aquatic.CREATIVE_TAB);
	}

}
