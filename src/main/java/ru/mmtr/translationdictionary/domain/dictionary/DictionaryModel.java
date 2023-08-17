package ru.mmtr.translationdictionary.domain.dictionary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Schema(description = "Дата создания", example = "2023-08-17 11:55:24.979")
    private Timestamp createdAt;

    @Schema(description = "Дата изменения", example = "2023-08-17 23:00:00.000")
    private Timestamp modifiedAt;

    public void setDictionaryId(UUID dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setFromLanguage(UUID fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public void setToLanguage(UUID toLanguage) {
        this.toLanguage = toLanguage;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
