package org.ethan.agent;

/**
 * Created by Ethan on 1/12/2018.
 */

import org.ethan.data.Engine;
import org.ethan.ui.MainFrame;
import org.ethan.ui.logger.Logger;
import org.ethan.util.Utilities;
import org.ethan.util.reflection.ReflectionEngine;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class AgentMain {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("[Agent] Start agent during JVM startup using argument '-javaagent'");
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        System.out.println("[Agent] Load agent into running JVM using Attach API");
        Engine.setClassCache(Utilities.getAllClasses(instrumentation));
        new MainFrame(instrumentation);
        try {
            Engine.setReflectionEngine(new ReflectionEngine(ClassLoader.getSystemClassLoader()));
        } catch (IOException e) {
            Logger.log(e.getLocalizedMessage());
        }

    }

}



