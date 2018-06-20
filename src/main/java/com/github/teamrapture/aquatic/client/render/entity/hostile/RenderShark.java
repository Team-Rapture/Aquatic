package com.github.teamrapture.aquatic.client.render.entity.hostile;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.client.models.entity.hostile.ModelShark;
import com.github.teamrapture.aquatic.entity.hostile.EntityShark;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShark extends RenderLiving<EntityShark> {

    private static final ResourceLocation SHARK_TEXTURE = new ResourceLocation(Aquatic.MODID,
            "textures/entity/modelshark.png");

    private final ModelShark sharkModel;

    public RenderShark(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelShark(), 0.2F);
        sharkModel = (ModelShark) super.mainModel;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityShark entity) {
        return SHARK_TEXTURE;
    }
}
