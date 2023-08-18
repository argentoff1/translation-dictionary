package ru.mmtr.translationdictionary.domain.dictionary;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DictionaryUpdateModel {
    @Parameter(description = "Идентификатор словаря")
    private UUID id;

    @Parameter(description = "Слово для перевода")
    private String word;

    @Parameter(description = "Перевод слова")
    private String translation;
}
