package org.osbot.jailbreak.data;

import java.io.File;

/**
 * Created by Ethan on 1/12/2018.
 */
public class Constants {
    public static String dumpDir = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Dump" + File.separator;
    public static String[] ignore = {"org.osbot", "java.util", "sun/reflect/", "java.",
            "sun.", "javax."};
}
