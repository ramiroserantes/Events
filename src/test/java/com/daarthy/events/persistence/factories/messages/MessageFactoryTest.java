package com.daarthy.events.persistence.factories.messages;

import com.daarthy.mini.shared.classes.auth.MiniAuth;
import com.daarthy.mini.shared.classes.enums.auditable.Language;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageFactoryTest {

    private final MessagesAbstractFactory messagesAbstractFactory = new MessagesAbstractFactoryImpl();
    private final String key = "d_01123";

    @Test
    public void testMessageFactoryBySpanishLanguage() {
        MiniAuth miniAuth = new MiniAuth(null, null, Language.ES);

        String message = messagesAbstractFactory.toI18Message(miniAuth, key);

        assertEquals("Fabricación en serie", message);
    }

    @Test
    public void testMessageFactoryByEnglishLanguage() {
        MiniAuth miniAuth = new MiniAuth(null, null, Language.EN);

        String message = messagesAbstractFactory.toI18Message(miniAuth, key);

        assertEquals("Crafting Challenge", message);
    }
}
