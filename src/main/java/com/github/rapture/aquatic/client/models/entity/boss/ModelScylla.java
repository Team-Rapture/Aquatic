package com.github.rapture.aquatic.client.models.entity.boss;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelScylla - EPIIC
 */
public class ModelScylla extends ModelBase {
	public ModelRenderer mainBody;
	public ModelRenderer bodyBottom;
	public ModelRenderer tentacleBase1;
	public ModelRenderer neck1;
	public ModelRenderer tentacleBase4;
	public ModelRenderer tailStart;
	public ModelRenderer tentacleBase2;
	public ModelRenderer tentacleBase3;
	public ModelRenderer tailPiece2;
	public ModelRenderer tailPiece3;
	public ModelRenderer tailFin;
	public ModelRenderer tentacleMiddle2;
	public ModelRenderer nailTentacle2nail;
	public ModelRenderer nailTentacle2nail3;
	public ModelRenderer nailTentacle2nail2;
	public ModelRenderer tentacleMiddle3;
	public ModelRenderer diamondEnd1;
	public ModelRenderer tentacleMiddle1;
	public ModelRenderer nailTentacle1nail;
	public ModelRenderer nailTentacle1nail3;
	public ModelRenderer nailTentacle1nail2;
	public ModelRenderer neck2;
	public ModelRenderer headBottom;
	public ModelRenderer mainHead;
	public ModelRenderer tentacleMiddle4;
	public ModelRenderer diamondEnd2;

	public ModelScylla() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.headBottom = new ModelRenderer(this, 80, 56);
		this.headBottom.setRotationPoint(-1.0F, 0.0F, 1.0F);
		this.headBottom.addBox(-2.0F, -4.0F, -3.0F, 5, 4, 4, 0.0F);
		this.tentacleMiddle3 = new ModelRenderer(this, 112, 11);
		this.tentacleMiddle3.setRotationPoint(0.1F, -6.4F, -0.2F);
		this.tentacleMiddle3.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(tentacleMiddle3, 0.2617993877991494F, 0.0F, 0.0F);
		this.tentacleBase2 = new ModelRenderer(this, 93, 0);
		this.tentacleBase2.setRotationPoint(-1.5F, -5.0F, 0.0F);
		this.tentacleBase2.addBox(-2.0F, -7.0F, -3.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(tentacleBase2, 0.5462880558742251F, 0.136659280431156F, -2.0488420089161434F);
		this.tentacleMiddle2 = new ModelRenderer(this, 93, 11);
		this.tentacleMiddle2.setRotationPoint(0.0F, -6.4F, -0.9F);
		this.tentacleMiddle2.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(tentacleMiddle2, 0.47123889803846897F, 0.0F, 0.0F);
		this.tailPiece2 = new ModelRenderer(this, 33, 0);
		this.tailPiece2.setRotationPoint(-3.0F, 0.0F, -2.0F);
		this.tailPiece2.addBox(0.0F, 0.0F, 0.0F, 6, 9, 5, 0.0F);
		this.setRotateAngle(tailPiece2, 0.18203784098300857F, 0.0F, 0.0F);
		this.tailFin = new ModelRenderer(this, 37, 45);
		this.tailFin.setRotationPoint(2.0F, 2.0F, 1.0F);
		this.tailFin.addBox(-3.0F, 0.0F, 0.0F, 6, 4, 0, 0.0F);
		this.setRotateAngle(tailFin, 0.29443704481144345F, 0.0F, 0.0F);
		this.mainHead = new ModelRenderer(this, 99, 50);
		this.mainHead.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.mainHead.addBox(-3.0F, -5.0F, -7.0F, 6, 5, 8, 0.0F);
		this.setRotateAngle(mainHead, -0.22759093446006054F, 0.0F, 0.0F);
		this.tentacleBase3 = new ModelRenderer(this, 112, 0);
		this.tentacleBase3.setRotationPoint(6.0F, -4.0F, -1.0F);
		this.tentacleBase3.addBox(-2.0F, -7.0F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(tentacleBase3, 0.7382742735936013F, 0.3817035074111599F, 2.262121243509851F);
		this.nailTentacle2nail3 = new ModelRenderer(this, 96, 27);
		this.nailTentacle2nail3.setRotationPoint(-0.9F, -11.6F, -0.4F);
		this.nailTentacle2nail3.addBox(-1.0F, -6.0F, -0.9F, 2, 6, 2, 0.0F);
		this.setRotateAngle(nailTentacle2nail3, 0.22689280275926282F, 0.0F, -0.17453292519943295F);
		this.neck2 = new ModelRenderer(this, 53, 55);
		this.neck2.setRotationPoint(2.0F, -6.0F, 0.0F);
		this.neck2.addBox(-4.0F, 0.0F, -3.0F, 7, 3, 6, 0.0F);
		this.setRotateAngle(neck2, 0.13962634015954636F, 0.0F, 0.0F);
		this.mainBody = new ModelRenderer(this, 0, 39);
		this.mainBody.setRotationPoint(0.0F, -3.0F, 0.0F);
		this.mainBody.addBox(-6.0F, 0.0F, -5.0F, 12, 14, 11, 0.0F);
		this.bodyBottom = new ModelRenderer(this, 1, 25);
		this.bodyBottom.setRotationPoint(-2.0F, 14.0F, -1.0F);
		this.bodyBottom.addBox(-3.0F, 0.0F, -3.0F, 10, 2, 9, 0.0F);
		this.tentacleBase4 = new ModelRenderer(this, 112, 0);
		this.tentacleBase4.setRotationPoint(-5.0F, 5.0F, -2.0F);
		this.tentacleBase4.addBox(-2.0F, -7.0F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(tentacleBase4, 0.7375761418928036F, -0.49026298688520714F, -1.0471975511965976F);
		this.tentacleMiddle1 = new ModelRenderer(this, 93, 11);
		this.tentacleMiddle1.setRotationPoint(1.1F, -1.4F, 0.0F);
		this.tentacleMiddle1.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(tentacleMiddle1, 0.2617993877991494F, 0.0F, 0.0F);
		this.neck1 = new ModelRenderer(this, 47, 44);
		this.neck1.setRotationPoint(-1.5F, 1.0F, -0.5F);
		this.neck1.addBox(-3.0F, -4.0F, -3.0F, 9, 3, 8, 0.0F);
		this.tailPiece3 = new ModelRenderer(this, 40, 38);
		this.tailPiece3.setRotationPoint(1.0F, 9.0F, 1.0F);
		this.tailPiece3.addBox(0.0F, 0.0F, 0.0F, 4, 2, 3, 0.0F);
		this.tentacleMiddle4 = new ModelRenderer(this, 112, 11);
		this.tentacleMiddle4.setRotationPoint(0.0F, -6.6F, 0.1F);
		this.tentacleMiddle4.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, 0.0F);
		this.setRotateAngle(tentacleMiddle4, 0.2617993877991494F, 0.0F, 0.0F);
		this.nailTentacle2nail2 = new ModelRenderer(this, 88, 27);
		this.nailTentacle2nail2.setRotationPoint(0.8F, -11.0F, -1.0F);
		this.nailTentacle2nail2.addBox(-1.0F, -6.0F, -0.9F, 2, 6, 2, 0.0F);
		this.setRotateAngle(nailTentacle2nail2, 0.19198621771937624F, 0.0F, 0.3490658503988659F);
		this.nailTentacle1nail = new ModelRenderer(this, 104, 27);
		this.nailTentacle1nail.setRotationPoint(0.4F, -11.0F, 0.3F);
		this.nailTentacle1nail.addBox(-0.9F, -6.0F, -0.4F, 2, 6, 2, 0.0F);
		this.setRotateAngle(nailTentacle1nail, -0.2565634000431664F, 0.0F, 0.45378560551852565F);
		this.tentacleBase1 = new ModelRenderer(this, 93, 0);
		this.tentacleBase1.setRotationPoint(6.6F, 2.4F, -6.4F);
		this.tentacleBase1.addBox(-1.0F, -2.0F, -2.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(tentacleBase1, 0.8196066167365371F, -0.5572836301617894F, 0.40980330836826856F);
		this.tailStart = new ModelRenderer(this, 0, 0);
		this.tailStart.setRotationPoint(2.0F, 0.0F, 1.0F);
		this.tailStart.addBox(-4.0F, 0.0F, -3.0F, 8, 4, 7, 0.0F);
		this.setRotateAngle(tailStart, 0.18203784098300857F, 0.0F, 0.0F);
		this.nailTentacle1nail3 = new ModelRenderer(this, 96, 27);
		this.nailTentacle1nail3.setRotationPoint(-0.9F, -11.6F, -0.4F);
		this.nailTentacle1nail3.addBox(-1.0F, -6.0F, -0.9F, 2, 6, 2, 0.0F);
		this.setRotateAngle(nailTentacle1nail3, 0.22689280275926282F, 0.0F, -0.17453292519943295F);
		this.nailTentacle1nail2 = new ModelRenderer(this, 88, 27);
		this.nailTentacle1nail2.setRotationPoint(0.8F, -11.0F, -1.0F);
		this.nailTentacle1nail2.addBox(-1.0F, -6.0F, -0.9F, 2, 6, 2, 0.0F);
		this.setRotateAngle(nailTentacle1nail2, 0.19198621771937624F, 0.0F, 0.3490658503988659F);
		this.nailTentacle2nail = new ModelRenderer(this, 104, 27);
		this.nailTentacle2nail.setRotationPoint(0.4F, -11.0F, 0.3F);
		this.nailTentacle2nail.addBox(-0.9F, -6.0F, -0.4F, 2, 6, 2, 0.0F);
		this.setRotateAngle(nailTentacle2nail, -0.2565634000431664F, 0.0F, 0.45378560551852565F);
		this.diamondEnd1 = new ModelRenderer(this, 116, 27);
		this.diamondEnd1.setRotationPoint(0.5F, -12.0F, 0.7F);
		this.diamondEnd1.addBox(-2.0F, -2.0F, -2.0F, 3, 3, 3, 0.0F);
		this.setRotateAngle(diamondEnd1, 0.7853981633974483F, 0.0F, 0.0F);
		this.diamondEnd2 = new ModelRenderer(this, 116, 27);
		this.diamondEnd2.setRotationPoint(0.5F, -12.0F, 0.7F);
		this.diamondEnd2.addBox(-2.0F, -2.0F, -2.0F, 3, 3, 3, 0.0F);
		this.setRotateAngle(diamondEnd2, 0.7853981633974483F, 0.0F, 0.0F);
		this.neck2.addChild(this.headBottom);
		this.tentacleBase3.addChild(this.tentacleMiddle3);
		this.bodyBottom.addChild(this.tentacleBase2);
		this.tentacleBase2.addChild(this.tentacleMiddle2);
		this.tailStart.addChild(this.tailPiece2);
		this.tailPiece3.addChild(this.tailFin);
		this.headBottom.addChild(this.mainHead);
		this.bodyBottom.addChild(this.tentacleBase3);
		this.tentacleMiddle2.addChild(this.nailTentacle2nail3);
		this.neck1.addChild(this.neck2);
		this.mainBody.addChild(this.bodyBottom);
		this.mainBody.addChild(this.tentacleBase4);
		this.tentacleBase1.addChild(this.tentacleMiddle1);
		this.mainBody.addChild(this.neck1);
		this.tailPiece2.addChild(this.tailPiece3);
		this.tentacleBase4.addChild(this.tentacleMiddle4);
		this.tentacleMiddle2.addChild(this.nailTentacle2nail2);
		this.tentacleMiddle1.addChild(this.nailTentacle1nail);
		this.mainBody.addChild(this.tentacleBase1);
		this.bodyBottom.addChild(this.tailStart);
		this.tentacleMiddle1.addChild(this.nailTentacle1nail3);
		this.tentacleMiddle1.addChild(this.nailTentacle1nail2);
		this.tentacleMiddle2.addChild(this.nailTentacle2nail);
		this.tentacleMiddle3.addChild(this.diamondEnd1);
		this.tentacleMiddle4.addChild(this.diamondEnd2);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.mainBody.render(f5);
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
