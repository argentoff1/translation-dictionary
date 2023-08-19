package ru.mmtr.translationdictionary.domain.dictionary;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictionaryWordAndTranslationModel {
    @Parameter(description = "Слово для перевода")
    private String word;

    @Parameter(description = "Перевод слова")
    private String translation;
}
