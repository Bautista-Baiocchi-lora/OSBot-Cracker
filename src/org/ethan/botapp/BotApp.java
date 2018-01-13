package org.ethan.botapp;

import org.ethan.data.Engine;
import org.ethan.ui.logger.Logger;

/**
 * Created by Ethan on 1/12/2018.
 */
public class BotApp {

    public BotApp() {
        setBoolean("iIIiiiiIIIII", true); //SETTING VIP
        Logger.log("Set account VIP access.");
    }

    public static void setBoolean(String fieldName, boolean isTrue) {
        Engine.getReflectionEngine().setFieldValue("org.osbot.BotApplication", fieldName, isTrue, Engine.getReflectionEngine().getBotAppInstance());
    }


}
