package com.github.teamrapture.aquatic.util;

import net.minecraft.item.ItemBlock;

import javax.annotation.Nullable;

public interface IHasItemBlock {

    /**
     * the custom itemblock class, null for no itemblock
     */
    @Nullable
    default Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlock.class;
    }

    default Object[] getAdditionalItemBlockConstructorArguments() {
        return new Object[0];
    }
}
