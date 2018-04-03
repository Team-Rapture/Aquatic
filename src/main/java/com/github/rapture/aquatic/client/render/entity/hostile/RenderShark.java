package com.github.rapture.aquatic.client.render.entity.hostile;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.models.entity.hostile.ModelShark;
import com.github.rapture.aquatic.entity.hostile.EntityShark;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShark extends RenderLiving<EntityShark> {

    private static final ResourceLocation ANGLER_TEXTURE = new ResourceLocation(Aquatic.MODID,
            "textures/entity/modelshark.png");

    private final ModelShark anglerModel;

    public RenderShark(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelShark(), 0.2F);
        anglerModel = (ModelShark) super.mainModel;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityShark entity) {
        return ANGLER_TEXTURE;
    }
}
