package com.github.rapture.aquatic.client.models.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class AnimatingUtils {

    public void swing(ModelRenderer box, float speed, float degree, float offset, float weight, float f, float f1) {
        box.rotateAngleY = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
    }

    public void flap(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float f,
                     float f1) {

        box.rotateAngleZ = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
    }

    public void bob(ModelRenderer box, float speed, float degree, float offset, float weight, float f, float f1) {
        box.rotationPointY = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
    }

    public void walk(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float f,
                     float f1) {

        box.rotateAngleX = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
    }
}
