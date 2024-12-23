package com.daarthy.events.persistence.factories.messages.languages;

public interface SpanishMessageFactory {

    /**
     * Finds a message in Spanish by its key.
     *
     * @param key The key associated with the message in Spanish.
     * @return The message corresponding to the provided key in Spanish or null.
     */
    String findMessageByKey(String key);
}
