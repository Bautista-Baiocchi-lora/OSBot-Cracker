package org.osbot.jailbreak.scripts;

import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.logger.Logger;

/**
 * Created by Ethan on 1/13/2018.
 */
public class SetScript {

    public SetScript() {
        if(getSelectorInstance() != null) {
            Logger.log("We found script selector instance.");
            if(getLocalScriptInstance() != null) {
                Logger.log("We found SA instance");
                if(getScriptManifestInstance() != null) {
                    Logger.log("We have the manifest.");
                } else {
                    Logger.log("No manifest.");
                }
            } else {
                Logger.log("no sa instance");
            }
        } else {
            Logger.log("no ss instance.");
        }
    }



    public Object getSelectorInstance() {
        return Engine.getReflectionEngine().getFieldValue("org.osbot.BotApplication", "IiIIIiiiIiii", Engine.getReflectionEngine().getBotAppInstance());
    }

    public Object getLocalScriptInstance() {
        return Engine.getReflectionEngine().getFieldValue("org.osbot.lPt7", "iIIIiiiiIIii", getSelectorInstance());
    }

    public Object getScriptManifestInstance() {
        return Engine.getReflectionEngine().getFieldValue("org.osbot.SA", "iIIIiiiIiiII", getLocalScriptInstance());
    }


    public Object newLocalScriptInstance(Class<?> clazz, boolean bool) { // LEAVE THIS ALONE, it's for the future.
        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            return classLoader.loadClass("").getDeclaredConstructor(Class.class, boolean.class).newInstance(clazz, bool);
        } catch(Exception e) {
            Logger.log(e.getLocalizedMessage());
        }
        return null;
    }  // LEAVE THIS ALONE, it's for the future.


}
