package com.github.rapture.aquatic.util;

import com.github.rapture.aquatic.Aquatic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author UpcraftLP
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RegistryCreate {

    /**
     * the type(s) of registry entries
     */
    Class[] value();

    String modid() default Aquatic.MODID;

}
