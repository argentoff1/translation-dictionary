package ru.mmtr.translationdictionary.domain.language;

import lombok.Getter;
import lombok.Setter;
import ru.mmtr.translationdictionary.domain.common.PageModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class LanguagePageRequestModel extends PageModel {
    private String languageFilter;

    private LocalDateTime createDateFromFilter;

    private LocalDateTime createDateToFilter;

    private LocalDateTime modifyDateFromFilter;

    private LocalDateTime modifyDateToFilter;
}
