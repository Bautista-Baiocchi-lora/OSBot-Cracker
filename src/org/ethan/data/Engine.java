package org.ethan.data;

import org.ethan.util.reflection.ReflectionEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 1/12/2018.
 */
public class Engine {
    private static String pattern;
    private static ReflectionEngine reflectionEngine;
    private static Collection<Class<?>> classCache;
    private static Map<String, byte[]> classes = new HashMap<>();
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

    public static String getPattern() {
        return pattern;
    }

    public static void setPattern(String pattern) {
        Engine.pattern = pattern;
    }
}
