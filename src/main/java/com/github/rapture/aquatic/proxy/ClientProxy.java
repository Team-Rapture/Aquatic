package com.github.rapture.aquatic.proxy;

import static com.github.rapture.aquatic.Aquatic.MODID;

import com.github.rapture.aquatic.client.render.entity.RenderEntityBubble;
import com.github.rapture.aquatic.client.render.entity.boss.RenderScylla;
import com.github.rapture.aquatic.client.render.entity.hostile.RenderAnglerFish;
import com.github.rapture.aquatic.entity.ModEntities;
import com.github.rapture.aquatic.entity.boss.EntityScylla;
import com.github.rapture.aquatic.entity.hostile.EntityAnglerFish;
import com.github.rapture.aquatic.entity.misc.EntityWaterBubble;
import com.github.rapture.aquatic.init.AquaticBlocks;
import com.github.rapture.aquatic.init.AquaticTiles;
import com.github.rapture.aquatic.util.ICustomModelProvider;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author UpcraftLP
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		OBJLoader.INSTANCE.addDomain(MODID);
		AquaticTiles.bindTESR();
		ModelLoader.setCustomStateMapper(AquaticBlocks.AQUA_WATER_BLOCK,
				new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
	}

	@Override
	public void registerRender(Item item) {
		if (item instanceof ICustomModelProvider)
			((ICustomModelProvider) item).initModel();
		else
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		ModEntities.renderEntities();
	}

	@Override
	public void registerRender(Block block) {
		if (block instanceof ICustomModelProvider)
			((ICustomModelProvider) block).initModel();
		else
			registerRender(Item.getItemFromBlock(block));
	}
}
