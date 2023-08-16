package ru.mmtr.translationdictionary.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Фильтры по подстроке, по дате добавления (добавить колонку даты добавления, дату модификации)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageModel {
    private Integer pageNum;

    private Integer pageSize;
}
