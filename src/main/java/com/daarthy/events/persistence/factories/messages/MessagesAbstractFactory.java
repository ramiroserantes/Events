package com.daarthy.events.persistence.factories.messages;

import com.daarthy.mini.shared.classes.auth.MiniAuth;

public interface MessagesAbstractFactory {

    /**
     * Translates a given key into an internationalized message according to the user's language.
     *
     * @param miniAuth the authentication object containing the user's language preference.
     * @param key      the key identifying the message to translate.
     * @return the internationalized message corresponding to the given key, or null if not found.
     */
    String toI18Message(MiniAuth miniAuth, String key);
}
