package com.github.teamrapture.aquatic.client.render.entity.hostile;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.client.models.entity.hostile.ModelAnglerFish;
import com.github.teamrapture.aquatic.entity.hostile.EntityAnglerFish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAnglerFish extends RenderLiving<EntityAnglerFish> {

	private static final ResourceLocation ANGLER_TEXTURE = new ResourceLocation(Aquatic.MODID,
			"textures/entity/angler.png");

	private final ModelAnglerFish anglerModel;

	public RenderAnglerFish(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelAnglerFish(), 0.2F);
		anglerModel = (ModelAnglerFish) super.mainModel;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAnglerFish entity) {
		return ANGLER_TEXTURE;
	}

}