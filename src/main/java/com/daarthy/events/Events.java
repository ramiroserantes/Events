package com.daarthy.events;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;
public final class Events extends JavaPlugin {

    private static final Logger logger;
    private static final Long BASIC_GUILD = 1L;

    static {
        logger = Logger.getLogger(Events.class.getName());
    }
    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    public static void logInfo(String msg) {logger.info(msg);}

    public static Long getBasicGuildId() {return BASIC_GUILD;}
}
