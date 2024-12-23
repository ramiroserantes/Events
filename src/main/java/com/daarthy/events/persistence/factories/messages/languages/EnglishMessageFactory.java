package com.daarthy.events.persistence.factories.messages.languages;

public interface EnglishMessageFactory {

    /**
     * Finds a message in English by its key.
     *
     * @param key The key associated with the message in English.
     * @return The message corresponding to the provided key in English or null.
     */
    String findMessageByKey(String key);
}
