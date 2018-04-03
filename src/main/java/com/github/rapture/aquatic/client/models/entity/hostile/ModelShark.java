package com.github.rapture.aquatic.client.models.entity.hostile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelShark extends ModelBase {

    ModelRenderer SharkBody;
    ModelRenderer SharkNeck;
    ModelRenderer SharkHead;
    ModelRenderer SharkMouth;
    ModelRenderer SharkBack1;
    ModelRenderer SharkTail;
    ModelRenderer SharkFin;
    ModelRenderer SharkRightHand;
    ModelRenderer SharkLeftHand;
    ModelRenderer SharkTailFin1;
    ModelRenderer SharkTailFin2;

    public ModelShark() {
        textureWidth = 128;
        textureHeight = 128;

        SharkBody = new ModelRenderer(this, 0, 81);
        SharkBody.addBox(0F, 0F, 0F, 12, 14, 20);
        SharkBody.setRotationPoint(-6F, 10F, -8F);
        SharkBody.setTextureSize(128, 128);
        SharkBody.mirror = true;
        setRotation(SharkBody, 0F, 0F, 0F);
        SharkNeck = new ModelRenderer(this, 64, 109);
        SharkNeck.addBox(0F, 0F, 0F, 10, 11, 8);
        SharkNeck.setRotationPoint(-5F, 11F, -16F);
        SharkNeck.setTextureSize(128, 128);
        SharkNeck.mirror = true;
        setRotation(SharkNeck, 0F, 0F, 0F);
        SharkHead = new ModelRenderer(this, 0, 116);
        SharkHead.addBox(0F, 0F, 0F, 8, 4, 8);
        SharkHead.setRotationPoint(-4F, 13F, -24F);
        SharkHead.setTextureSize(128, 128);
        SharkHead.mirror = true;
        setRotation(SharkHead, -0.0090881F, 0F, 0F);
        SharkMouth = new ModelRenderer(this, 33, 118);
        SharkMouth.addBox(0F, 0F, 0F, 8, 3, 7);
        SharkMouth.setRotationPoint(-4F, 18F, -23F);
        SharkMouth.setTextureSize(128, 128);
        SharkMouth.mirror = true;
        setRotation(SharkMouth, 0.1487144F, 0F, 0F);
        SharkBack1 = new ModelRenderer(this, 65, 88);
        SharkBack1.addBox(0F, 0F, 0F, 10, 12, 8);
        SharkBack1.setRotationPoint(-5F, 11F, 12F);
        SharkBack1.setTextureSize(128, 128);
        SharkBack1.mirror = true;
        setRotation(SharkBack1, 0F, 0F, 0F);
        SharkTail = new ModelRenderer(this, 0, 60);
        SharkTail.addBox(0F, 0F, 0F, 6, 8, 12);
        SharkTail.setRotationPoint(-3F, 13F, 20F);
        SharkTail.setTextureSize(128, 128);
        SharkTail.mirror = true;
        setRotation(SharkTail, 0.0174533F, 0F, 0F);
        SharkFin = new ModelRenderer(this, 0, 36);
        SharkFin.addBox(0F, 0F, 0F, 1, 8, 8);
        SharkFin.setRotationPoint(-0.4666667F, 10F, -4F);
        SharkFin.setTextureSize(128, 128);
        SharkFin.mirror = true;
        setRotation(SharkFin, 0.7853982F, 0F, 0F);
        SharkRightHand = new ModelRenderer(this, 0, 43);
        SharkRightHand.addBox(0F, 0F, 0F, 8, 1, 4);
        SharkRightHand.setRotationPoint(-5F, 22F, -9F);
        SharkRightHand.setTextureSize(128, 128);
        SharkRightHand.mirror = true;
        setRotation(SharkRightHand, 0F, 0F, 3.036873F);
        SharkLeftHand = new ModelRenderer(this, 0, 43);
        SharkLeftHand.addBox(0F, 0F, 0F, 8, 1, 4);
        SharkLeftHand.setRotationPoint(5F, 21F, -9F);
        SharkLeftHand.setTextureSize(128, 128);
        SharkLeftHand.mirror = true;
        setRotation(SharkLeftHand, 0F, 0F, 0.1396263F);
        SharkTailFin1 = new ModelRenderer(this, 0, 38);
        SharkTailFin1.addBox(0F, 0F, 0F, 1, 4, 6);
        SharkTailFin1.setRotationPoint(-0.5F, 14F, 31F);
        SharkTailFin1.setTextureSize(128, 128);
        SharkTailFin1.mirror = true;
        setRotation(SharkTailFin1, 0.6320364F, 0F, 0F);
        SharkTailFin2 = new ModelRenderer(this, 12, 36);
        SharkTailFin2.addBox(0F, 0F, 0F, 1, 3, 5);
        SharkTailFin2.setRotationPoint(-0.5F, 16F, 32F);
        SharkTailFin2.setTextureSize(128, 128);
        SharkTailFin2.mirror = true;
        setRotation(SharkTailFin2, -0.5576792F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        SharkBody.render(f5);
        SharkNeck.render(f5);
        SharkHead.render(f5);
        SharkMouth.render(f5);
        SharkBack1.render(f5);
        SharkTail.render(f5);
        SharkFin.render(f5);
        SharkRightHand.render(f5);
        SharkLeftHand.render(f5);
        SharkTailFin1.render(f5);
        SharkTailFin2.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}