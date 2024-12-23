package com.daarthy.events.persistence.factories.messages.languages;

public class SpanishMessageFactoryImpl extends AbstractMessage implements SpanishMessageFactory {

    private static final String FILE = "configuration/messages_es.yml";

    public SpanishMessageFactoryImpl() {
        super(FILE);
    }

    @Override
    public String findMessageByKey(String key) {
        return super.toMessage(key);
    }
}
