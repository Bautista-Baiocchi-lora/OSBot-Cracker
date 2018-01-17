package org.osbot.jailbreak.agent;

/**
 * Created by Ethan on 1/12/2018.
 */

import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.MainFrame;
import org.osbot.jailbreak.ui.logger.Logger;
import org.osbot.jailbreak.util.Utilities;
import org.osbot.jailbreak.util.reflection.ReflectionEngine;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;


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
        //new BotApp();


    }

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> redefinedClass, ProtectionDomain protDomain, byte[] classBytes) {
           if(Utilities.isCandidate(className)) {
               Logger.log("Adding: " + className + " to cache..");
               Engine.getClasses().put(className, classBytes);
           }
        return classBytes;
    }
}




