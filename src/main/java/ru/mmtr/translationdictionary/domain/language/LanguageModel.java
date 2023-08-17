package ru.mmtr.translationdictionary.domain.language;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageModel {
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID languageId;

    @Schema(description = "Язык", example = "Русский")
    private String languageName;

    @Schema(description = "Дата создания", example = "2023-08-17 11:55:24.979")
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения", example = "2023-08-17 23:00:00.000")
    private LocalDateTime modifiedAt;

    public void setLanguageId(UUID languageId) {
        this.languageId = languageId;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
