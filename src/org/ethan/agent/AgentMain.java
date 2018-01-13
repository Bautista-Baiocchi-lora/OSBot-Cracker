package org.ethan.agent;

/**
 * Created by Ethan on 1/12/2018.
 */

import org.ethan.botapp.BotApp;
import org.ethan.botapp.hooks.HookCollection;
import org.ethan.data.Engine;
import org.ethan.scripts.ScriptData;
import org.ethan.ui.LogFrame;
import org.ethan.ui.logger.Logger;
import org.ethan.util.Utilities;
import org.ethan.util.reflection.ReflectionEngine;
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
        Engine.setClassCache(Utilities.getAllClasses(instrumentation));
        new LogFrame(instrumentation);
        try {
            Engine.setReflectionEngine(new ReflectionEngine(ClassLoader.getSystemClassLoader()));
        } catch (IOException e) {
            Logger.log(e.getLocalizedMessage());
        }
        new BotApp();
        new HookCollection();
        inst.addTransformer(new AgentMain(), true);

    }


    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> redefinedClass, ProtectionDomain protDomain, byte[] classBytes) {
        if (Utilities.isCandidate(className)) {
            Logger.log("Adding: "+className + " to cache..");
            Engine.getClasses().put(className, classBytes);
        }

        return classBytes;
    }
}



