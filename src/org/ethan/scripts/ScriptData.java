package org.ethan.scripts;


import org.ethan.data.Engine;

import java.lang.instrument.Instrumentation;
import java.util.Collection;


/**
 * Created by Ethan on 1/12/2018.
 */
public class ScriptData {
    Collection<Class<?>> localClassCollection = null;

    public ScriptData(Instrumentation instrumentation) {

        /*localClassCollection = getLocalClassCollection();
        if(localClassCollection != null && localClassCollection.size() > 0) {
            Logger.log("Size of classes loaded:" +localClassCollection.size());
            for (Iterator<Class<?>> iterator = localClassCollection.iterator(); iterator.hasNext();) {
                Logger.log("Class Name: " + iterator.next().getName());
            }
        } else {
            Logger.log("None in collection.");
        }*/

    }

    public Collection<Class<?>> getLocalClassCollection() {
        return (Collection) Engine.getReflectionEngine().getMethodValue("org.osbot.LpT8", "IiiIIiiiIiii", 0, "public abstract interface java.util.Collection<E>", null);
    }

}
