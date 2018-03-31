package com.github.rapture.aquatic.proxy;

import com.github.rapture.aquatic.init.AquaticTiles;
import com.github.rapture.aquatic.util.ICustomModelProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.github.rapture.aquatic.Aquatic.MODID;

/**
 * @author UpcraftLP
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(MODID);
        AquaticTiles.bindTESR();
    }

    @Override
    public void registerRender(Item item) {
        if(item instanceof ICustomModelProvider) ((ICustomModelProvider) item).initModel();
        else ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
