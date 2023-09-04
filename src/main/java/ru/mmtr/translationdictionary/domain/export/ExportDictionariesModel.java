package ru.mmtr.translationdictionary.domain.export;

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
public class ExportDictionariesModel extends GeneralResultModel {
    @Schema(description = "Наименование языка исходного слова", accessMode = Schema.AccessMode.READ_ONLY)
    private String fromLanguageName;

    @Schema(description = "Идентификатор языка исходного слова")
    private UUID fromLanguageUUID;

    @Schema(description = "Наименование языка переведенного слова", accessMode = Schema.AccessMode.READ_ONLY)
    private String toLanguageName;

    @Schema(description = "Идентификатор языка переведенного слова")
    private UUID toLanguageUUID;

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

    public ExportDictionariesModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        fromLanguageName = null;
        fromLanguageUUID = null;
        toLanguageName = null;
        toLanguageUUID = null;
        word = null;
        translation = null;
        fullName = null;
        email = null;
        createdUserId = null;
        createdAt = null;
        modifiedUserId = null;
        modifiedAt = null;
    }
}
