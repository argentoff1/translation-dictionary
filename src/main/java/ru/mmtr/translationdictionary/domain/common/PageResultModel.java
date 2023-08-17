package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageResultModel<T> {
    @Schema(description = "Общее количество элементов", example = "100")
    private Integer totalCount;

    @Schema(description = "Искомые данные")
    private Collection<T> resultList;
}
