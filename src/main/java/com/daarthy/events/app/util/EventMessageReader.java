package com.daarthy.events.app.util;

import com.daarthy.events.Events;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class EventMessageReader {
    private static final String FILE_PATH = "events.yml";

    public static String getMessage(String eventKey, Object... args) {
        try (InputStream inputStream = EventMessageReader.class.getClassLoader().getResourceAsStream(FILE_PATH)) {
            Yaml yaml = new Yaml();
            Map<String, String> eventMessages = yaml.load(inputStream);
            String messageTemplate = eventMessages.get(eventKey);
            return String.format(messageTemplate, args);
        } catch (Exception e) {
            // Events.logInfo("Error on retrieve the event description");
            return null;
        }
    }
}