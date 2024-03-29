package com.daarthy.events;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;
public final class Events extends JavaPlugin {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(Events.class.getName());
    }
    @Override
    public void onEnable() {



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void logInfo(String msg) {logger.info(msg);}
}
