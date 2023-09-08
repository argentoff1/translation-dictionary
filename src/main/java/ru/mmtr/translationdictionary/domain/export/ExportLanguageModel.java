package ru.mmtr.translationdictionary.domain.export;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/8/2023
 * \* Description:
 * \
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportLanguageModel {
    @Schema(description = "Идентификатор языка", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID languageId;

    @Schema(description = "Наименование языка")
    private String languageName;

    @Schema(description = "ФИО пользователя")
    private String fullName;

    @Schema(description = "Дата добавления пары слов")
    private LocalDateTime createdAt;

    @Schema(description = "Дата изменения пары слов")
    private LocalDateTime modifiedAt;

    @Schema(description = "ФИО пользователя, создавшего запись")
    private UUID createdUserId;

    @Schema(description = "ФИО пользователя, изменившего запись")
    private UUID modifiedUserId;
}
