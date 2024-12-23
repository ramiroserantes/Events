package com.daarthy.events.persistence.factories.messages.languages;

public class EnglishMessageFactoryImpl extends AbstractMessage implements EnglishMessageFactory {

    private static final String FILE = "configuration/messages_en.yml";

    public EnglishMessageFactoryImpl() {
        super(FILE);
    }

    @Override
    public String findMessageByKey(String key) {
        return super.toMessage(key);
    }
}
