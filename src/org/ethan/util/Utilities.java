package org.ethan.util;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Ethan on 1/12/2018.
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


}
