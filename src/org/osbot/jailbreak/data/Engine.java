package org.osbot.jailbreak.data;

import org.osbot.jailbreak.botapp.hooks.HookCollection;
import org.osbot.jailbreak.util.reflection.ReflectionEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 1/12/2018.
 */
public class Engine {
    private static ReflectionEngine reflectionEngine;
    private static Collection<Class<?>> classCache;
    private static Map<String, byte[]> classes = new HashMap<>();
    private static HookCollection hookCollection;

    public static HookCollection getHookCollection() {
        return hookCollection;
    }

    public static void setHookCollection(final HookCollection hookCollection) {
        Engine.hookCollection = hookCollection;
    }

    public static ReflectionEngine getReflectionEngine() {
        return reflectionEngine;
    }

    public static void setReflectionEngine(ReflectionEngine reflectionEngine) {
        Engine.reflectionEngine = reflectionEngine;
    }

    public static Collection<Class<?>> getClassCache() {
        return classCache;
    }

    public static void setClassCache(Collection<Class<?>> classCache) {
        Engine.classCache = classCache;
    }

    public static Map<String, byte[]> getClasses() {
        return classes;
    }

}
