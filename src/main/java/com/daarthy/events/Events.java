package com.daarthy.events;

import com.daarthy.events.api.ApiGateway;
import org.bukkit.plugin.java.JavaPlugin;

public final class Events extends JavaPlugin {

    public static final String MICRO_NAME = "[FESTIVALS_MICROSERVICE] ";
    public static final Long BASIC_GUILD = 1L;
    //private static final Logger logger;
    private static ApiGateway api;

    static {
       /* try {
            // api = new ApiGatewayImpl(DataSourceLocatorImpl.getInstance().getDataSource());
        } catch (IOException e) {
            logInfo("Not a valid state");
        }
        logger = Logger.getLogger(Events.class.getName());*/
    }

    public static Long getBasicGuildId() {
        return BASIC_GUILD;
    }

    public static ApiGateway getApi() {
        return api;
    }

    //public static void logInfo(String msg) {logger.info(msg);}

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}
