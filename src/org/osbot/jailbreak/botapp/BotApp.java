package org.osbot.jailbreak.botapp;

import org.osbot.jailbreak.data.Engine;
import org.osbot.jailbreak.ui.logger.Logger;

/**
 * Created by Ethan on 1/12/2018.
 */
public class BotApp {

    public BotApp() {
        setBoolean("IIiIiiiiIiiI", true);
        Logger.log("Set account VIP access.");
    }

    public static void setBoolean(String fieldName, boolean isTrue) {
        Engine.getReflectionEngine().setFieldValue("org.osbot.BotApplication", fieldName, isTrue, Engine.getReflectionEngine().getBotAppInstance());
    }


}
