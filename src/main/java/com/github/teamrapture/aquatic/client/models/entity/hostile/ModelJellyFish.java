package com.github.teamrapture.aquatic.client.models.entity.hostile;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * JellyFish - EPIIC Created using Tabula 7.0.0
 */
public class ModelJellyFish extends ModelBase {
	public ModelRenderer shape1;
	public ModelRenderer jellySide2;
	public ModelRenderer longJelly;
	public ModelRenderer jellySide;
	public ModelRenderer longJelly_1;
	public ModelRenderer jellySide_1;
	public ModelRenderer jellySide_2;

	public ModelJellyFish() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.jellySide_2 = new ModelRenderer(this, 0, 0);
		this.jellySide_2.setRotationPoint(1.5F, 5.0F, 9.0F);
		this.jellySide_2.addBox(-8.5F, 0.0F, 0.0F, 17, 10, 0, 0.0F);
		this.longJelly = new ModelRenderer(this, 107, -9);
		this.longJelly.setRotationPoint(2.0F, 5.0F, 1.0F);
		this.longJelly.addBox(0.0F, 0.0F, -5.0F, 0, 30, 10, 0.0F);
		this.longJelly_1 = new ModelRenderer(this, 85, 1);
		this.longJelly_1.setRotationPoint(2.0F, 5.0F, 0.0F);
		this.longJelly_1.addBox(-5.0F, 0.0F, 0.0F, 10, 30, 0, 0.0F);
		this.jellySide_1 = new ModelRenderer(this, 35, 0);
		this.jellySide_1.setRotationPoint(1.5F, 5.0F, -8.0F);
		this.jellySide_1.addBox(-8.5F, 0.0F, 0.0F, 17, 10, 0, 0.0F);
		this.shape1 = new ModelRenderer(this, 0, 40);
		this.shape1.setRotationPoint(-2.0F, -11.0F, -1.0F);
		this.shape1.addBox(-7.0F, -1.0F, -8.0F, 17, 6, 17, 0.0F);
		this.jellySide = new ModelRenderer(this, 0, 0);
		this.jellySide.setRotationPoint(10.0F, 5.0F, 1.0F);
		this.jellySide.addBox(0.0F, 0.0F, -9.0F, 0, 10, 17, 0.0F);
		this.jellySide2 = new ModelRenderer(this, 0, 11);
		this.jellySide2.setRotationPoint(-7.0F, 9.0F, 0.0F);
		this.jellySide2.addBox(0.0F, -4.0F, -8.0F, 0, 10, 17, 0.0F);
		this.shape1.addChild(this.jellySide_2);
		this.shape1.addChild(this.longJelly);
		this.shape1.addChild(this.longJelly_1);
		this.shape1.addChild(this.jellySide_1);
		this.shape1.addChild(this.jellySide);
		this.shape1.addChild(this.jellySide2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.shape1.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
