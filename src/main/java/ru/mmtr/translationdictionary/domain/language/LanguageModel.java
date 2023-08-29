package ru.mmtr.translationdictionary.domain.language;

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
public class LanguageModel extends GeneralResultModel {
    @Schema(description = "Идентификатор", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID languageId;

    @Schema(description = "Язык", example = "Русский")
    private String languageName;

    @Schema(description = "Дата создания", example = "2023-08-17 11:55:24.979")
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения", example = "2023-08-17 23:00:00.000")
    private LocalDateTime modifiedAt;

    @Schema(description = "Идентификатор пользователя, который добавил язык")
    private UUID createdUserId;

    @Schema(description = "Идентификатор пользователя, который изменил язык")
    private UUID modifiedUserId;

    public LanguageModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        languageId = null;
        languageName = null;
        createdAt = null;
        modifiedAt = null;
    }
}
