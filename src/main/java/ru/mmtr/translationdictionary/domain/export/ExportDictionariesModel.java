package ru.mmtr.translationdictionary.domain.export;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportDictionariesModel {
    @Schema(description = "Язык исходного слова", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID fromLanguage;

    @Schema(description = "Язык переведенного слова", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID toLanguage;

    @Schema(description = "Слово для перевода")
    private String word;

    @Schema(description = "Перевод слова")
    private String translation;

    @Schema(description = "ФИО пользователя")
    private String fullName;

    @Schema(description = "Электронная почта пользователя")
    private String email;

    @Schema(description = "Идентификатор пользователя, который добавил пару слов")
    private UUID createdUserId;

    @Schema(description = "Дата добавления пары слов")
    private LocalDateTime createdAt;

    @Schema(description = "Идентификатор пользователя, который изменил пару слов")
    private UUID modifiedUserId;

    @Schema(description = "Дата изменения пары слов")
    private LocalDateTime modifiedAt;
}
