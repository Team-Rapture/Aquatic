package com.github.rapture.aquatic.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAquaNetController extends ModelBase {

  public static ModelAquaNetController INSTANCE = new ModelAquaNetController();

  public ModelRenderer BaseLayer2;
  public ModelRenderer Base;
  public ModelRenderer shaft;
  public ModelRenderer topconnector;
  public ModelRenderer top;

  public ModelAquaNetController() {
    textureWidth = 64;
    textureHeight = 64;

    BaseLayer2 = new ModelRenderer(this, 0, 0);
    BaseLayer2.addBox(0F, 0F, 0F, 16, 1, 16);
    BaseLayer2.setRotationPoint(-8F, 23F, -8F);
    BaseLayer2.setTextureSize(64, 64);
    BaseLayer2.mirror = true;
    setRotation(BaseLayer2, 0F, 0F, 0F);
    Base = new ModelRenderer(this, 0, 30);
    Base.addBox(0F, 0F, 0F, 12, 1, 12);
    Base.setRotationPoint(-6F, 22F, -6F);
    Base.setTextureSize(64, 64);
    Base.mirror = true;
    setRotation(Base, 0F, 0F, 0F);
    shaft = new ModelRenderer(this, 0, 44);
    shaft.addBox(0F, 0F, 0F, 8, 12, 8);
    shaft.setRotationPoint(-4F, 10F, -4F);
    shaft.setTextureSize(64, 64);
    shaft.mirror = true;
    setRotation(shaft, 0F, 0F, 0F);
    topconnector = new ModelRenderer(this, 0, 20);
    topconnector.addBox(0F, 0F, 0F, 6, 1, 6);
    topconnector.setRotationPoint(-3F, 9F, -3F);
    topconnector.setTextureSize(64, 64);
    topconnector.mirror = true;
    setRotation(topconnector, 0F, 0F, 0F);
    top = new ModelRenderer(this, 0, 30);
    top.addBox(0F, 0F, 0F, 12, 1, 12);
    top.setRotationPoint(-6F, 8F, -6F);
    top.setTextureSize(64, 64);
    top.mirror = true;
    setRotation(top, 0F, 0F, 0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    BaseLayer2.render(f5);
    Base.render(f5);
    shaft.render(f5);
    topconnector.render(f5);
    top.render(f5);
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
