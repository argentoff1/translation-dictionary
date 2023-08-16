package ru.mmtr.translationdictionary.domain.dictionary;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class DictionaryModel {
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID dictionaryId;

    @Schema(description = "Слово для перевода", example = "Окно")
    private String word;

    @Schema(description = "Перевод слова", example = "Window")
    private String translation;

    @Schema(description = "Идентификатор языка исходного слова", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID fromLanguage;

    @Schema(description = "Идентификатор языка переведенного слова", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID toLanguage;

    public DictionaryModel() {
    }

    public DictionaryModel(UUID dictionaryId, String word, String translation, UUID fromLanguage, UUID toLanguage) {
        this.dictionaryId = dictionaryId;
        this.word = word;
        this.translation = translation;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
    }

    public UUID getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(UUID dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public UUID getFromLanguage() {
        return fromLanguage;
    }

    public void setFromLanguage(UUID fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public UUID getToLanguage() {
        return toLanguage;
    }

    public void setToLanguage(UUID toLanguage) {
        this.toLanguage = toLanguage;
    }
}
