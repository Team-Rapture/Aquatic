package com.github.rapture.aquatic.proxy;

import com.github.rapture.aquatic.util.ICustomModelProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

/**
 * @author UpcraftLP
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRender(Item item) {
        if(item instanceof ICustomModelProvider) ((ICustomModelProvider) item).initModel();
        else ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
