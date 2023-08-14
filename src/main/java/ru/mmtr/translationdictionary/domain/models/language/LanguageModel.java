package ru.mmtr.translationdictionary.domain.models.language;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.UUID;

public class LanguageModel {
    private UUID languageId;

    private String languageName;

    public LanguageModel() {}

    public LanguageModel(UUID languageId, String languageName) {
        this.languageId = languageId;
        this.languageName = languageName;
    }

    public UUID getLanguageId() {
        return languageId;
    }

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
