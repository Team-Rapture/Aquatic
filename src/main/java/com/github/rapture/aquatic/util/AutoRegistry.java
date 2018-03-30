package com.github.rapture.aquatic.util;

import com.github.rapture.aquatic.Aquatic;
import com.github.rapture.aquatic.config.AquaticConfig;
import com.github.rapture.aquatic.item.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author UpcraftLP
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Aquatic.MODID)
public class AutoRegistry {

    private static final IForgeRegistry<Item> ITEM_REGISTRY = GameRegistry.findRegistry(Item.class);
    private static final Map<Class, String> REGISTRY_CLASSES = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static <T extends IForgeRegistryEntry<T>> void onRegisterStuff(RegistryEvent.Register event) {
        ModContainer currentModContainer = Loader.instance().activeModContainer();
        IForgeRegistry<T> registry = event.getRegistry();
        Aquatic.getLogger().debug("Registering type {}", registry.getRegistrySuperType().getSimpleName());
        int count = 0;
        for(Class clazz : REGISTRY_CLASSES.keySet()) {
            RegistryCreate classAnnotation = (RegistryCreate) clazz.getAnnotation(RegistryCreate.class);
            for(Class type : classAnnotation.value()) {
                if(registry.getRegistrySuperType().isAssignableFrom(type)) {
                    String modid = REGISTRY_CLASSES.get(clazz);
                    Loader.instance().setActiveModContainer(FMLCommonHandler.instance().findContainerFor(modid));
                    count += createRegistryEntries(registry, clazz, modid);
                    break;
                }
            }
        }
        Loader.instance().setActiveModContainer(currentModContainer);
        if(AquaticConfig.debugMode) {
            Aquatic.getLogger().info("Active mod container restored.");
            Aquatic.getLogger().info("successfully registered {} objects.", count);
        }
    }

    /**
     * determine all registry classes for registering
     */
    public static void findRegistryEntries(FMLPreInitializationEvent event) {
        Set<ASMDataTable.ASMData> dataTable = event.getAsmData().getAll(RegistryCreate.class.getCanonicalName());
        final ModContainer originalModContainer = Loader.instance().activeModContainer();
        StringBuilder list = new StringBuilder();
        dataTable.forEach(asmData -> {
            try {
                //replace the current mod container so Forge defaults to the correct modid
                String modid = (String) asmData.getAnnotationInfo().get("modid");
                Loader.instance().setActiveModContainer(FMLCommonHandler.instance().findContainerFor(modid));

                String className = asmData.getClassName();
                Class clazz = Class.forName(className); //load the class
                REGISTRY_CLASSES.put(clazz, modid);
                list.append("\n\t").append(className);
            } catch (ClassNotFoundException e) { //this should NEVER happen
                e.printStackTrace();
                FMLCommonHandler.instance().exitJava(1, true);
            }
        });
        Loader.instance().setActiveModContainer(originalModContainer); //restore the active mod container
        if(AquaticConfig.debugMode) Aquatic.getLogger().debug("Mod container restored. The following classes were determined for registry entry examination: {}", list.toString());
    }

    private static <T extends IForgeRegistryEntry<T>> int createRegistryEntries(IForgeRegistry<T> registry, Class clazz, String modid) {
        int count = 0;
        Class<T> type = registry.getRegistrySuperType();
        for (Field f : clazz.getDeclaredFields()) {
            if (type.isAssignableFrom(f.getType())) {
                try {
                    @SuppressWarnings("unchecked")
                    T entry = (T) f.get(null);
                    if(entry.getRegistryName() == null) {
                        Aquatic.getLogger().warn("No registry name set for {}:{}, substituting field name", clazz.getName(), f.getName());
                        entry.setRegistryName(new ResourceLocation(modid, f.getName().toLowerCase()));
                    }
                    registry.register(entry);
                    count++;
                    if (Item.class.isAssignableFrom(type)) {
                        Aquatic.proxy.registerRender((Item) entry);
                    } else if (Block.class.isAssignableFrom(type)) {
                        if(entry instanceof IHasItemBlock) {
                            Block block = (Block) entry;
                            final Item itemBlock = new ItemBlockBase(block).setCreativeTab(block.getCreativeTabToDisplayOn());
                            ITEM_REGISTRY.register(itemBlock);
                            count++;
                            Aquatic.proxy.registerRender(itemBlock);
                        }
                    }
                } catch (Exception e) {
                    Aquatic.getLogger().error("Exception thrown during registration step!", e);
                }
                if (!Modifier.isFinal(f.getModifiers())) Aquatic.getLogger().warn("{}:{} has no final modifier! Please change this!", clazz.getName(), f.getName());
            }
        }
        return count;
    }
}
