package org.osbot.jailbreak.scripts;

import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.logger.Logger;
import org.osbot.jailbreak.util.Utilities;

import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;


/**
 * Created by Ethan on 1/13/2018.
 */
public class SetScript {
    private Object scriptSelectorInstance;
    private Map<String, Class<?>> classMap = new HashMap<>();
    private Instrumentation instrumentation;
    public SetScript(String jarLink, Instrumentation instrumentation) {
        if (getSelectorInstance() == null) {
            setScriptSelectorInstance();
        }

       //Engine.getInstrumentation().appendToSystemClassLoaderSearch(jar);

        }

    public void setScriptSelectorInstance() {
        Object obj = Engine.getReflectionEngine().getClass("org.osbot.lPt7").getNewInstance();
        Engine.getReflectionEngine().setFieldValue("org.osbot.BotApplication", "IiIIIiiiIiii", obj);
        scriptSelectorInstance = getSelectorInstance();
    }

    public Object getSelectorInstance() {
        return Engine.getReflectionEngine().getFieldValue("org.osbot.BotApplication", "IiIIIiiiIiii", Engine.getReflectionEngine().getBotAppInstance());
    }

    public Object getLocalScriptInstance() {
        return Engine.getReflectionEngine().getFieldValue("org.osbot.lPt7", "iIIIiiiiIIii", getSelectorInstance());
    }

    public void setSA(Class<?> clazz) {
        Engine.getReflectionEngine().setFieldValue("org.osbot.BotApplication", "iiiIiiiiIIii", newLocalScriptInstance(clazz), getLocalScriptInstance());
    }


    public Object newLocalScriptInstance(Class<?> clazz) {
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            return classLoader.loadClass("org.osbot.SA").getDeclaredConstructor(Class.class, boolean.class).newInstance(clazz, true);
        } catch(Exception e) {
            Logger.log("ERROR: "+e.getLocalizedMessage());
        }
        return null;
    }


}
