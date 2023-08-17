package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class CollectionResultModel<T> extends GeneralResultModel {
    @Schema(description = "Искомые данные")
    private Collection<T> resultList;

    public CollectionResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        resultList = null;
    }

    public CollectionResultModel(Collection<T> resultList) {
        this.resultList = resultList;
    }
}
