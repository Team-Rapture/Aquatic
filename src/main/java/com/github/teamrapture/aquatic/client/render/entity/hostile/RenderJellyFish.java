package com.github.teamrapture.aquatic.client.render.entity.hostile;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.models.entity.hostile.ModelJellyFish;
import com.github.rapture.aquatic.entity.passive.EntityJellyFish;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderJellyFish extends RenderLiving<EntityJellyFish> {

	private static final ResourceLocation JELLY_TEXTURE = new ResourceLocation(Aquatic.MODID,
			"textures/entity/jelly_fish.png");

	private final ModelJellyFish anglerModel;

	public RenderJellyFish(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelJellyFish(), 0.2F);
		anglerModel = (ModelJellyFish) super.mainModel;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityJellyFish entity) {
		return JELLY_TEXTURE;
	}

}