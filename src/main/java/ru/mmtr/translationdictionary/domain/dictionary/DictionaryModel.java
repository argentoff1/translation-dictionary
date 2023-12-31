package ru.mmtr.translationdictionary.domain.dictionary;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mmtr.translationdictionary.domain.common.GeneralResultModel;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryModel extends GeneralResultModel {
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
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения", example = "2023-08-17 23:00:00.000")
    private LocalDateTime modifiedAt;

    @Schema(description = "Идентификатор пользователя, который добавил пару слов")
    private UUID createdUserId;

    @Schema(description = "Идентификатор пользователя, который изменил пару слов")
    private UUID modifiedUserId;

    public DictionaryModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
