package com.github.rapture.aquatic.client.models.entity.hostile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelAnglerFish - EPIIC
 */
public class ModelAnglerFish extends ModelBase {
	public ModelRenderer headTop;
	public ModelRenderer headBot;
	public ModelRenderer anglerLine1;
	public ModelRenderer teethFrontTop;
	public ModelRenderer backFinBase;
	public ModelRenderer teethside;
	public ModelRenderer teethBottomFront;
	public ModelRenderer teethside_1;
	public ModelRenderer anglerLine2;
	public ModelRenderer anglerLine3;
	public ModelRenderer anglerLight;
	public ModelRenderer finback1;
	public ModelRenderer finback2;

	public ModelAnglerFish() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.backFinBase = new ModelRenderer(this, 41, 14);
		this.backFinBase.setRotationPoint(0.4F, 0.4F, -0.7F);
		this.backFinBase.addBox(-2.0F, -0.6F, 0.0F, 4, 1, 2, 0.0F);
		this.teethBottomFront = new ModelRenderer(this, 26, 3);
		this.teethBottomFront.setRotationPoint(0.0F, 0.0F, -11.0F);
		this.teethBottomFront.addBox(-3.0F, -2.0F, 0.0F, 7, 2, 0, 0.0F);
		this.finback2 = new ModelRenderer(this, 41, 17);
		this.finback2.setRotationPoint(-0.41F, -0.1F, 1.8F);
		this.finback2.addBox(-1.9F, -0.6F, -0.1F, 2, 1, 4, 0.0F);
		this.setRotateAngle(finback2, 0.20943951023931953F, -0.2617993877991494F, 0.06981317007977318F);
		this.anglerLight = new ModelRenderer(this, 0, 8);
		this.anglerLight.setRotationPoint(-0.6F, 3.0F, -0.2F);
		this.anglerLight.addBox(-0.5F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
		this.headBot = new ModelRenderer(this, 0, 0);
		this.headBot.setRotationPoint(0.0F, -0.4F, -1.7F);
		this.headBot.addBox(-3.0F, 0.0F, -11.0F, 7, 4, 12, 0.0F);
		this.setRotateAngle(headBot, 0.2792526803190927F, 0.0F, 0.0F);
		this.headTop = new ModelRenderer(this, 0, 16);
		this.headTop.setRotationPoint(0.0F, 19.1F, 4.0F);
		this.headTop.addBox(-3.0F, -5.0F, -11.0F, 7, 5, 11, 0.0F);
		this.setRotateAngle(headTop, -0.17453292519943295F, 0.0F, 0.0F);
		this.teethside = new ModelRenderer(this, 26, -5);
		this.teethside.setRotationPoint(3.21F, 0.0F, -9.1F);
		this.teethside.addBox(0.8F, -2.0F, -2.0F, 0, 2, 5, 0.0F);
		this.anglerLine1 = new ModelRenderer(this, 0, 16);
		this.anglerLine1.setRotationPoint(0.0F, -4.7F, -8.0F);
		this.anglerLine1.addBox(-0.2F, -5.0F, -0.6F, 1, 5, 1, 0.0F);
		this.setRotateAngle(anglerLine1, 0.18203784098300857F, 0.0F, 0.0F);
		this.finback1 = new ModelRenderer(this, 41, 9);
		this.finback1.setRotationPoint(0.6F, 0.0F, 1.6F);
		this.finback1.addBox(-0.4F, -0.6F, -0.1F, 2, 1, 4, 0.0F);
		this.setRotateAngle(finback1, 0.16126842288427606F, 0.3440043955680823F, 0.0F);
		this.teethFrontTop = new ModelRenderer(this, 26, 6);
		this.teethFrontTop.setRotationPoint(0.5F, -1.0F, -11.0F);
		this.teethFrontTop.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 0, 0.0F);
		this.anglerLine3 = new ModelRenderer(this, 0, 23);
		this.anglerLine3.setRotationPoint(0.3F, 0.2F, -4.9F);
		this.anglerLine3.addBox(-0.6F, -0.2F, -0.7F, 1, 3, 1, 0.0F);
		this.teethside_1 = new ModelRenderer(this, 37, -5);
		this.teethside_1.setRotationPoint(-3.0F, 0.0F, -8.0F);
		this.teethside_1.addBox(0.0F, -2.0F, -3.0F, 0, 2, 5, 0.0F);
		this.anglerLine2 = new ModelRenderer(this, 25, 20);
		this.anglerLine2.setRotationPoint(0.1F, -5.2F, 0.0F);
		this.anglerLine2.addBox(-0.3F, -0.8F, -5.6F, 1, 1, 6, 0.0F);
		this.headTop.addChild(this.backFinBase);
		this.headBot.addChild(this.teethBottomFront);
		this.backFinBase.addChild(this.finback2);
		this.anglerLine3.addChild(this.anglerLight);
		this.headTop.addChild(this.headBot);
		this.headBot.addChild(this.teethside);
		this.headTop.addChild(this.anglerLine1);
		this.backFinBase.addChild(this.finback1);
		this.headTop.addChild(this.teethFrontTop);
		this.anglerLine2.addChild(this.anglerLine3);
		this.headBot.addChild(this.teethside_1);
		this.anglerLine1.addChild(this.anglerLine2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.headTop.render(f5);
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
