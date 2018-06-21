package com.github.teamrapture.aquatic.util;

import com.github.teamrapture.aquatic.Aquatic;
import com.github.teamrapture.aquatic.config.AquaticConfig;
import com.github.teamrapture.aquatic.item.ItemBlockBase;
import com.google.common.collect.ObjectArrays;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
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
        for (Class clazz : REGISTRY_CLASSES.keySet()) {
            RegistryCreate classAnnotation = (RegistryCreate) clazz.getAnnotation(RegistryCreate.class);
            for (Class type : classAnnotation.value()) {
                if (registry.getRegistrySuperType().isAssignableFrom(type)) {
                    String modid = REGISTRY_CLASSES.get(clazz);
                    Loader.instance().setActiveModContainer(FMLCommonHandler.instance().findContainerFor(modid));
                    count += createRegistryEntries(registry, clazz, modid);
                    break;
                }
            }
        }
        Loader.instance().setActiveModContainer(currentModContainer);
        if (AquaticConfig.experimental.debugMode) {
            Aquatic.getLogger().info("Active mod container restored.");
            Aquatic.getLogger().info("successfully registered {} objects for event {}", count, registry.getRegistrySuperType().getSimpleName().toUpperCase(Locale.ROOT));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static <T extends IForgeRegistryEntry<T>> void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        FluidUtils.addBuckets();
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
        if (AquaticConfig.experimental.debugMode)
            Aquatic.getLogger().debug("Mod container restored. The following classes were determined for registry entry examination: {}", list.toString());
    }

    private static <T extends IForgeRegistryEntry<T>> int createRegistryEntries(IForgeRegistry<T> registry, Class clazz, String modid) {
        int count = 0;
        Class<T> type = registry.getRegistrySuperType();
        for (Field f : clazz.getDeclaredFields()) {
            if (type.isAssignableFrom(f.getType())) {
                try {
                    @SuppressWarnings("unchecked")
                    T entry = (T) f.get(null);
                    if (entry.getRegistryName() == null) {
                        Aquatic.getLogger().warn("No registry name set for {}:{}, substituting field name", clazz.getName(), f.getName());
                        entry.setRegistryName(new ResourceLocation(modid, f.getName().toLowerCase()));
                    }
                    registry.register(entry);
                    count++;
                    if (Item.class.isAssignableFrom(type)) {
                        Aquatic.proxy.registerRender((Item) entry);
                    } else if (Block.class.isAssignableFrom(type)) {
                        Block block = (Block) entry;
                        Class<? extends ItemBlock> itemBlockClass = ItemBlockBase.class;
                        Object[] cArgs = new Object[0];
                        if (block instanceof IHasItemBlock) {
                            IHasItemBlock block1 = (IHasItemBlock) block;
                            itemBlockClass = block1.getItemBlockClass();
                            cArgs = block1.getAdditionalItemBlockConstructorArguments();
                        }

                        if (itemBlockClass != null) { //register itemblock
                            Class<?>[] ctorArgClasses = new Class<?>[cArgs.length + 1];
                            ctorArgClasses[0] = Block.class;
                            for (int idx = 1; idx < ctorArgClasses.length; idx++) {
                                ctorArgClasses[idx] = cArgs[idx - 1].getClass();
                            }
                            Constructor<? extends ItemBlock> itemCtor = itemBlockClass.getConstructor(ctorArgClasses);
                            final Item itemBlock = itemCtor.newInstance(ObjectArrays.concat(block, cArgs));
                            if (itemBlock.getRegistryName() == null) itemBlock.setRegistryName(block.getRegistryName());
                            ITEM_REGISTRY.register(itemBlock);
                            count++;
                            Aquatic.proxy.registerRender(block);
                        }

                    }
                } catch (Exception e) {
                    Aquatic.getLogger().error("Exception thrown during registration step!", e);
                }
                if (!Modifier.isFinal(f.getModifiers()))
                    Aquatic.getLogger().warn("{}:{} has no final modifier! Please change this!", clazz.getName(), f.getName());
            }
        }
        return count;
    }
}
