package com.github.rapture.aquatic.client.guide;

import com.github.rapture.aquatic.Aquatic;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;

@SideOnly(Side.CLIENT)
public class GuideReader {

    public static final ResourceLocation INDEX_PAGE = new ResourceLocation(Aquatic.MODID, "index.json");
    public static final TreeMap<String, NBTTagCompound> GUIDE_INDEX = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private static NBTTagCompound readJsonToNbt(@Nullable ResourceLocation location) {
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

    public static List<String> readPage(String page) {
        if (page != null && GUIDE_INDEX.containsKey(page)) {
            NBTTagCompound nbt = GUIDE_INDEX.get(page);
            ResourceLocation location = new ResourceLocation(nbt.getString("page"));
            String path = ("/assets/" + location.getResourceDomain() + "/pages/" + Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode() + "/" + location.getResourcePath()).toLowerCase(Locale.ROOT);
            if (!path.endsWith(".md")) path += ".md";
            try (InputStream stream = new BufferedInputStream(MinecraftServer.class.getResourceAsStream(path))) {
                String output = IOUtils.toString(stream, Charsets.UTF_8);
                return Arrays.asList(output.split("\r\n"));
            } catch (Exception e) {
                e.printStackTrace();
            }


            try (Scanner sc = new Scanner(new BufferedInputStream(MinecraftServer.class.getResourceAsStream(path)))) {
                StringBuilder builder = new StringBuilder();
                while (sc.hasNext()) {
                    builder.append(sc.next());
                }
                return Lists.newArrayList(builder.toString());
                //return Arrays.asList(builder.toString().split("\n"));
            } catch (Exception e) {
            }
        }
        Aquatic.getLogger().error("Exception reading {}, defaulting to empty page!", page);
        return Collections.emptyList();
    }

    /**
     * read the index file
     * TODO unlock entries via capability
     */
    public static void init() {
        NBTTagCompound indexNBT = readJsonToNbt(INDEX_PAGE);
        NBTTagList topics = indexNBT.getTagList("index", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < topics.tagCount(); i++) {
            NBTTagCompound compound = topics.getCompoundTagAt(i);
            GUIDE_INDEX.put(compound.getString("id"), compound);
        }
    }
}