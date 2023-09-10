package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResultModel<T> extends GeneralResultModel{
    @Schema(description = "Общее количество элементов", example = "100")
    private Integer totalCount;

    @Schema(description = "Искомые данные")
    private Collection<T> resultList;

    public PageResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        totalCount = null;
        resultList = null;
    }
}
