package org.osbot.jailbreak.util;

import org.osbot.jailbreak.data.Constants;
import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.logger.Logger;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;


/**
 * Created by Ethan on 1/12/2018.scripts.exrunecrafter
 */
public class Utilities {

    public static Collection<Class<?>> getAllClasses(Instrumentation instrumentation) {
        Collection<Class<?>> classes = new ArrayList<>();
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            classes.add(clazz);
        }
        return classes;
    }

    public static Collection<Class<?>> getDifference(Collection a, Collection b) {
        b.removeAll(a);
        return b;
    }

    public static boolean isCandidate(String className) {
        className = className.replace('/', '.');
        if(Utilities.stringContainsItemFromList(className, Constants.ignore))
            return false;
        return className.length() > 0;
    }

    public static void dumpJar(final File file) {
        try {
            dumpJar(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            Logger.log(e.getLocalizedMessage());
        }
    }


    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }

    public static void dumpJar(final FileOutputStream stream) {
        try {
            JarOutputStream out = new JarOutputStream(stream);
            for (Map.Entry<String, byte[]> entry : Engine.getClasses().entrySet()) {
                Logger.log(entry.getKey());
                JarEntry je = new JarEntry(entry.getKey() + ".class");
                out.putNextEntry(je);
                out.write(entry.getValue());
            }
            out.close();
            stream.close();
            Engine.getClasses().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
