package org.osbot.jailbreak.agent;

/**
 * Created by Ethan on 1/12/2018.
 */

import org.osbot.jailbreak.botapp.BotApp;
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

    private static Instrumentation inst;

    public static void premain(String args, Instrumentation instrumentation) {
        agentmain(args, instrumentation);
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        System.out.println("[Agent] Load agent into running JVM using Attach API");
        inst = instrumentation;
        inst.addTransformer(new AgentMain(), true);
        Engine.setClassCache(Utilities.getAllClasses(instrumentation));
        new MainFrame(instrumentation);
        try {
            Engine.setReflectionEngine(new ReflectionEngine(ClassLoader.getSystemClassLoader()));
        } catch (IOException e) {
            Logger.log(e.getLocalizedMessage());
        }
        new BotApp();
    }


    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> redefinedClass, ProtectionDomain protDomain, byte[] classBytes) {
        if (Utilities.isCandidate(className)) {
            Logger.log("Adding: " + className + " to cache..");
            Engine.getClasses().put(className, classBytes);
        }

        return classBytes;
    }
}



