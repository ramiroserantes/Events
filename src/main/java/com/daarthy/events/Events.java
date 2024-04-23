package com.daarthy.events;

import com.daarthy.events.api.ApiGateway;
import com.daarthy.events.api.ApiGatewayImpl;
import com.daarthy.events.app.util.DataSourceLocatorImpl;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;
public final class Events extends JavaPlugin {

    private static final Logger logger;
    private static ApiGateway api;
    private static final Long BASIC_GUILD = 1L;

    static {
        try {
             api = new ApiGatewayImpl(DataSourceLocatorImpl.getInstance().getDataSource());
        } catch (IOException e) {
            logInfo("Not a valid state");
        }
        logger = Logger.getLogger(Events.class.getName());
    }
    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    public static void logInfo(String msg) {logger.info(msg);}

    public static Long getBasicGuildId() {return BASIC_GUILD;}

    public static ApiGateway getApi() {return api;}
}
