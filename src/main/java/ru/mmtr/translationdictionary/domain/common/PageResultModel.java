package ru.mmtr.translationdictionary.domain.common;

import lombok.*;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageResultModel<T> {
    private Integer totalCount;

    private Collection<T> resultList;
}
