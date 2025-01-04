package com.daarthy.events.persistence.factories.messages;

import com.daarthy.events.Events;
import com.daarthy.events.persistence.factories.messages.languages.EnglishMessageFactory;
import com.daarthy.events.persistence.factories.messages.languages.EnglishMessageFactoryImpl;
import com.daarthy.events.persistence.factories.messages.languages.SpanishMessageFactory;
import com.daarthy.events.persistence.factories.messages.languages.SpanishMessageFactoryImpl;
import com.daarthy.mini.shared.classes.auth.MiniAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagesAbstractFactoryImpl implements MessagesAbstractFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagesAbstractFactoryImpl.class);
    private final EnglishMessageFactory englishMessageFactory;
    private final SpanishMessageFactory spanishMessageFactory;

    public MessagesAbstractFactoryImpl() {
        this.englishMessageFactory = new EnglishMessageFactoryImpl();
        this.spanishMessageFactory = new SpanishMessageFactoryImpl();
    }

    @Override
    public String toI18Message(MiniAuth miniAuth, String key) {

        switch (miniAuth.getLanguage()) {
            case EN -> {
                return englishMessageFactory.findMessageByKey(key);
            }
            case ES -> {
                return spanishMessageFactory.findMessageByKey(key);
            }
            default -> {
                LOGGER.error(Events.MICRO_NAME + "- Couldn't find the language: {}",
                        miniAuth.getLanguage());
                return null;
            }

        }
    }
}
