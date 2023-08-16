package ru.mmtr.translationdictionary.domain.language;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class LanguageModel {
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID languageId;

    @Schema(description = "Язык", example = "Русский")
    @NotBlank(message = "Язык не должен быть пустым или содержать пробелы")
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
