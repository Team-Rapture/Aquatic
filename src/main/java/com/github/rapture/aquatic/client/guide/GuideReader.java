package com.github.rapture.aquatic.client.guide;

import com.github.rapture.aquatic.Aquatic;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@SideOnly(Side.CLIENT)
public class GuideReader {

    public static final ResourceLocation INDEX = new ResourceLocation(Aquatic.MODID, "index.json");
    public static final List<NBTTagCompound> PAGES = new ArrayList<>();

    public static NBTTagCompound readJsonToNbt(@Nullable ResourceLocation location) {
        if (location != null) {
            String path = ("/assets/" + location.getResourceDomain() + "/pages/" + location.getResourcePath()).toLowerCase(Locale.ROOT);
            if (!path.endsWith(".json")) path += ".json";
            try (Scanner sc = new Scanner(new BufferedInputStream(MinecraftServer.class.getResourceAsStream(path)))) {
                StringBuilder builder = new StringBuilder();
                while (sc.hasNext()) builder.append(sc.next());
                return JsonToNBT.getTagFromJson(builder.toString());
            } catch (Exception e) {
                Aquatic.getLogger().error("Exception reading " + location + ", defaulting to empty page!");
            }
        }
        return new NBTTagCompound();
    }

    /**
     * read the index file
     * TODO unlock entries via capability
     */
    public static void init() {
        NBTTagCompound indexNBT = readJsonToNbt(INDEX);
        NBTTagList topics = indexNBT.getTagList("index", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < topics.tagCount(); i++) {
            NBTTagCompound compound = topics.getCompoundTagAt(i);
            PAGES.add(compound);
        }
    }
}