package com.github.teamrapture.aquatic.client;

import com.github.teamrapture.aquatic.Aquatic;
import com.google.common.base.Preconditions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.image.BufferedImage;
import java.net.URI;

@SideOnly(Side.CLIENT)
public class ClientUtil {

    @SuppressWarnings("unchecked")
    public static void openLink(String url) {
        try {
            URI uri = new URI(url);
            Class oclass = Class.forName("java.awt.Desktop");
            boolean flag = (Boolean) oclass.getMethod("isDesktopSupported").invoke(null);
            if (!flag) throw new UnsupportedOperationException("no desktop supported!");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, uri);
        } catch (Throwable throwable1) {
            Throwable throwable = throwable1.getCause();
            Aquatic.getLogger().error("Couldn\'t open link: {}", new Object[]{throwable == null ? (StringUtils.isNullOrEmpty(url) ? "<UNKNOWN>" : url) : throwable.getMessage()});
        }
    }

    public static ResourceLocation loadTexture(ResourceLocation location, int[] data) {
        Preconditions.checkArgument(data.length == 2, "Data pointer must have an array size of 2");
        ResourceLocation dest;
        try {
            BufferedImage img = TextureUtil.readBufferedImage(ClientUtil.class.getResourceAsStream("/assets/" + location.getNamespace() + "/" + location.getPath()));
            data[0] = img.getWidth();
            data[1] = img.getHeight();
            dest = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(location.getNamespace(), new DynamicTexture(img));
        } catch (Exception e) {
            dest = TextureMap.LOCATION_MISSING_TEXTURE;
            Aquatic.getLogger().warn("Image not found:" + location);
        }
        return dest;
    }

    public static KeyBinding getKeyBinding(String name, int keyCode) {
        KeyBinding keyBinding = new KeyBinding("key.aquatic." + name, keyCode, "key.category.aquatic");
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }
}
