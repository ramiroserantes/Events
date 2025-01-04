package com.daarthy.events.persistence.factories.messages.languages;

import com.daarthy.mini.shared.classes.yaml.AbstractMiniYaml;

public abstract class AbstractMessage extends AbstractMiniYaml {

    protected AbstractMessage(String pathToFile) {
        super(pathToFile);
    }

    protected String toMessage(String key) {
        return (String) yamlFile.get(key);
    }
}
