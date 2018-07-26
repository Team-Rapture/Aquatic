package com.github.teamrapture.aquatic.client.guide;

import com.github.teamrapture.aquatic.Aquatic;
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

    public static final TreeMap<String, NBTTagCompound> GUIDE_INDEX = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    public static final Map<String, Integer> CHILD_COUNT = new HashMap<>();

    private static final ResourceLocation INDEX_PAGE = new ResourceLocation(Aquatic.MODID, "index.json");
    private static final Map<String, String> PAGES = new HashMap<>();

    private static NBTTagCompound readJsonToNbt(@Nullable ResourceLocation location) {
        if (location != null) {
            String path = ("/assets/" + location.getNamespace() + "/pages/" + location.getPath()).toLowerCase(Locale.ROOT);
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
        if (page != null && PAGES.containsKey(page)) {
            ResourceLocation location = new ResourceLocation(PAGES.get(page));
            String path = ("/assets/" + location.getNamespace() + "/pages/" + Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode() + "/" + location.getPath()).toLowerCase(Locale.ROOT);
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
                e.printStackTrace();
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
            String id = compound.getString("id");
            GUIDE_INDEX.put(id, compound);
            PAGES.put(id, compound.getString("page"));
            if (compound.hasKey("child_elements", Constants.NBT.TAG_LIST)) {
                NBTTagList tagList = compound.getTagList("child_elements", Constants.NBT.TAG_COMPOUND);
                CHILD_COUNT.put(id, tagList.tagCount());
                for (int j = 0; j < tagList.tagCount(); j++) {
                    NBTTagCompound child = tagList.getCompoundTagAt(j);
                    PAGES.put(child.getString("id"), child.getString("page"));
                }
            } else CHILD_COUNT.put(id, 0);
        }
    }
}