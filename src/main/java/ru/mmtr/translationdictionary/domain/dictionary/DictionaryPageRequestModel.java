package ru.mmtr.translationdictionary.domain.dictionary;

import lombok.Getter;
import lombok.Setter;
import ru.mmtr.translationdictionary.domain.common.PageModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class DictionaryPageRequestModel extends PageModel {
    private String wordFilter;

    private String translationFilter;

    private LocalDateTime createDateFromFilter;

    private LocalDateTime createDateToFilter;

    private LocalDateTime modifyDateFromFilter;

    private LocalDateTime modifyDateToFilter;
}
