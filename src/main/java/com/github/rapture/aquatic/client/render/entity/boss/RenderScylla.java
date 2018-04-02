package com.github.rapture.aquatic.client.render.entity.boss;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.client.models.entity.boss.ModelScylla;
import com.github.rapture.aquatic.entity.boss.EntityScylla;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderScylla extends RenderLiving<EntityScylla> {

    private static final ResourceLocation SCYLLA_TEXTURE = new ResourceLocation(Aquatic.MODID,
            "textures/entity/scylla.png");

    private final ModelScylla scyllaModel;

    public RenderScylla(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelScylla(), 0.2F);
        scyllaModel = (ModelScylla) super.mainModel;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityScylla entity) {
        return SCYLLA_TEXTURE;
    }
}