package ru.mmtr.translationdictionary.domain.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserArchiveModel {
    @Parameter(description = "Идентификатор")
    private UUID id;

    @Parameter(description = "Дата архивации")
    private LocalDateTime archiveDate;
}
