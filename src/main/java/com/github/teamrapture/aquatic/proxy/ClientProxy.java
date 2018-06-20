package com.github.teamrapture.aquatic.proxy;

import com.github.teamrapture.aquatic.client.guide.GuideReader;
import com.github.teamrapture.aquatic.client.render.entity.RenderEntityBubble;
import com.github.teamrapture.aquatic.client.render.entity.boss.RenderScylla;
import com.github.teamrapture.aquatic.client.render.entity.hostile.RenderAnglerFish;
import com.github.teamrapture.aquatic.client.render.entity.hostile.RenderJellyFish;
import com.github.teamrapture.aquatic.client.render.entity.hostile.RenderShark;
import com.github.teamrapture.aquatic.entity.EntityWaterBubble;
import com.github.teamrapture.aquatic.entity.boss.EntityScylla;
import com.github.teamrapture.aquatic.entity.hostile.EntityAnglerFish;
import com.github.teamrapture.aquatic.entity.hostile.EntityShark;
import com.github.teamrapture.aquatic.entity.passive.EntityJellyFish;
import com.github.teamrapture.aquatic.init.AquaticBlocks;
import com.github.teamrapture.aquatic.util.ICustomModelProvider;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.github.teamrapture.aquatic.Aquatic.*;

/**
 * @author UpcraftLP
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		OBJLoader.INSTANCE.addDomain(MODID);
		AquaticBlocks.bindTESR();
		ModelLoader.setCustomStateMapper(AquaticBlocks.AQUA_WATER_BLOCK,
				new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
		RenderingRegistry.registerEntityRenderingHandler(EntityAnglerFish.class, RenderAnglerFish::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityJellyFish.class, RenderJellyFish::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityScylla.class, RenderScylla::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterBubble.class, RenderEntityBubble::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShark.class, RenderShark::new);
		GuideReader.init();
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
	}

	@Override
	public void registerRender(Block block) {
		if (block instanceof ICustomModelProvider)
			((ICustomModelProvider) block).initModel();
		else
			registerRender(Item.getItemFromBlock(block));
	}
}
