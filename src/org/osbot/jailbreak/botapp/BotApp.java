package org.osbot.jailbreak.botapp;

import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.logger.Logger;
import org.osbot.jailbreak.util.reflection.ReflectedField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * Created by Ethan on 1/12/2018.
 */
public class BotApp {
    private HashMap<String, byte[]> classBytes = new HashMap<>();
    private HashMap<String, byte[]> resourceBytes = new HashMap<>();

    public BotApp(int id) {
        printAllStrings();
        getSDNScript(id);

    }

    public Object uAinstance() {
        return Engine.getReflectionEngine().getMethodValue3("org.osbot.BotApplication", "iiIIiiiIiIii", 0, "public class org.osbot.UA", Engine.getReflectionEngine().getBotAppInstance());
    }
    public void printAllStrings() {
        Engine.getReflectionEngine().setFieldValue("org.osbot.UA", "IIiiiiiIIIii","Alek", uAinstance());
        String s = (String) Engine.getReflectionEngine().getFieldValue("org.osbot.UA", "IIiiiiiIIIii", uAinstance());
        String s1 = (String) Engine.getReflectionEngine().getFieldValue("org.osbot.UA", "IiiIiiiIiIIi", uAinstance());
        Logger.log(s);
        Logger.log(s1);

    }
    private synchronized Object getSDNScript(int id) {
        try {
            byte[] bytes = getBytes2(getBytes(id));
            if(bytes != null && bytes.length > 0) {
                Logger.log("Bytes Length: ");
            }
            final byte[] array = new byte[1024];
            final JarInputStream jarInputStream = new JarInputStream(new ByteArrayInputStream(bytes));
            ZipEntry nextEntry;
            while ((nextEntry = jarInputStream.getNextEntry()) != null) {
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JarInputStream jarInputStream2 = jarInputStream;
                int read;
                while ((read = jarInputStream2.read(array, 0, array.length)) != -1) {
                    jarInputStream2 = jarInputStream;
                    byteArrayOutputStream.write(array, 0, read);
                }
                if (nextEntry.getName().endsWith(".class")) {
                    Logger.log(nextEntry.getName());
                    classBytes.put(nextEntry.getName(), byteArrayOutputStream.toByteArray());
                } else {
                    resourceBytes.put(nextEntry.getName(), byteArrayOutputStream.toByteArray());
                }
            }
        } catch (Exception e) {
            Logger.logException("Script injection error: " +e.getLocalizedMessage());
        }
        return null;
    }

    private byte[] getBytes(int id) {
        return (byte[]) Engine.getReflectionEngine().getScriptValue("org.osbot.S", "iiIIiiiIiIii", 1, "public abstract final class [B", null, id);
    }
    private byte[] getBytes2(Object obj) {
        byte[] bytes = (byte[]) obj;
        if(bytes != null && bytes.length > 0) {
            Logger.log("we found encrypted bytes, let's decrypt.");
        } else {
            Logger.log("NO BYTES!");
        }
        return  (byte[]) Engine.getReflectionEngine().getMethodValue2("org.osbot.MA", "iiIIiiiIiIii", 1, "public abstract final class [B", null, obj);
    }
}