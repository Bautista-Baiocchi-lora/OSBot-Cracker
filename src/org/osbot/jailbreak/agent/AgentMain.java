package org.osbot.jailbreak.agent;

/**
 * Created by Ethan on 1/12/2018.
 */

import org.osbot.jailbreak.botapp.BotApp;
import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.MainFrame;
import org.osbot.jailbreak.ui.logger.Logger;
import org.osbot.jailbreak.util.Utilities;
import org.osbot.jailbreak.util.injection.ASMClassLoader;
import org.osbot.jailbreak.util.injection.ClassArchive;
import org.osbot.jailbreak.util.reflection.ReflectionEngine;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class AgentMain implements ClassFileTransformer {

    public static void premain(String args, Instrumentation instrumentation) {
        agentmain(args, instrumentation);
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        Engine.setInstrumentation(instrumentation);
        Engine.getInstrumentation().addTransformer(new AgentMain(), true);
        Engine.setClassCache(Utilities.getAllClasses(instrumentation));
        new MainFrame(instrumentation);
        Logger.log("[Agent] Successfully loaded into the JVM");
        try {
            Engine.setReflectionEngine(new ReflectionEngine(ClassLoader.getSystemClassLoader()));
        } catch (IOException e) {
            Logger.log(e.getLocalizedMessage());
        }
        ClassArchive classArchive = new ClassArchive();
        classArchive.addJar("https://www.dropbox.com/s/cn3z8hziawpr8od/KhalCrafter.jar?dl=1");
        ASMClassLoader classLoader = new ASMClassLoader(classArchive);
        try {
            if (classLoader != null && classArchive.classes.size() > 0) {
                classLoader.loadAll();
        } else {
                Logger.log("Wut");
            }
        } catch (Exception e) {
            Logger.log("ERROR EX: "+e.getLocalizedMessage());
        }

        new BotApp();
    }
    public static JarFile createURLConnection(String url) {
        try {
            final URL address = new URL(url);
            JarURLConnection connection = (JarURLConnection) address.openConnection();
            connection.addRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
            connection.setConnectTimeout(5000);
            connection.setUseCaches(false);

            return connection.getJarFile();
        } catch (Exception e) {
            Logger.log(e.getLocalizedMessage());
        }
        return null;
    }
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> redefinedClass, ProtectionDomain protDomain, byte[] classBytes) {
            Logger.log("Adding: " + className + " to cache..");
            //Engine.getClasses().put(className, classBytes);
        return classBytes;
    }
}




