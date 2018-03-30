package com.github.rapture.aquatic.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomModelProvider {

    //do NOT make the class client only, that will result in loading errors!
    @SideOnly(Side.CLIENT)
    void initModel();
}
