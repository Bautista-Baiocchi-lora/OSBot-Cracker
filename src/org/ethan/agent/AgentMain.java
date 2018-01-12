package org.ethan.agent;

/**
 * Created by Ethan on 1/12/2018.
 */

import org.ethan.data.Engine;
import org.ethan.scripts.ScriptData;
import org.ethan.ui.LogFrame;
import org.ethan.ui.logger.Logger;
import org.ethan.util.Utilities;
import org.ethan.util.reflection.ReflectionEngine;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class AgentMain implements ClassFileTransformer {

    private static String pattern = "com.ethan.fighter";
    private static Instrumentation inst;
    private static String dumpDir = "C:\\Users\\Ethan\\Desktop\\DumpDir";
    public static Map<Class<?>, byte[]> classes2 = new HashMap<>();
    public static void premain(String args, Instrumentation instrumentation) {
         agentmain(args,instrumentation);
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        System.out.println("[Agent] Load agent into running JVM using Attach API");
        inst = instrumentation;
        Engine.setClassCache(Utilities.getAllClasses(instrumentation));
        new LogFrame(instrumentation);
        try {
            Engine.setReflectionEngine(new ReflectionEngine(ClassLoader.getSystemClassLoader()));
        } catch (IOException e) {
            Logger.log(e.getLocalizedMessage());
        }
        inst.addTransformer(new AgentMain(), true);

    }

    /*public static void doDump() {
        try { deleteDirectory(new File(dumpDir)); } catch(Exception e) {}
        Class<?>[] classes = inst.getAllLoadedClasses();
        List<Class<?>> candidates = new ArrayList<>();
        for (Class<?> c : classes) {
            if (isCandidate(c.getName()) && inst.isModifiableClass(c)) {
                candidates.add(c);

            }
        }
        try {
            if (!candidates.isEmpty())
                inst.retransformClasses(candidates.toArray(new Class[0]));
        } catch (UnmodifiableClassException uce) {
        }
    }
*/
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> redefinedClass, ProtectionDomain protDomain, byte[] classBytes) {
        if (isCandidate(className)) {
            dumpClass(className, classBytes);
            classes2.put(redefinedClass, classBytes);
        }

        return null;
    }

    private static boolean isCandidate(String className) {
        className = className.replace('/', '.');
        if(pattern == null)
            return false;
        return className == null ? false : className.contains(pattern);
    }


    private static void dumpClass(String className, byte[] classBuf) {
        try {
            Logger.log("Writing: "+className);
            File dumpFile = new File(dumpDir,className+".class");
            dumpFile.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(dumpFile);
            try {
                out.write(classBuf);
            } finally {
                out.close();
            }
        } catch (IOException e) {

        }
    }

    public static String[] getLoadedClasses() {
        List<String> list = new ArrayList<String>();
        for(Class<?> clazz : inst.getAllLoadedClasses()) {
            if(clazz.getName().startsWith("java.") || clazz.getName().startsWith("sun.") ||
                    clazz.getName().startsWith("com.sun.") || clazz.getName().startsWith("[") || clazz.getName().startsWith("javax."))
                continue;
            list.add(clazz.getName());
        }
        return list.toArray(new String[list.size()]);
    }

    private static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                File f = new File(dir, children[i]);
                deleteDirectory(f);
            }
            dir.delete();
        } else if(dir.getName().endsWith("jar"))
            return;
        else
            dir.delete();
    }
}



