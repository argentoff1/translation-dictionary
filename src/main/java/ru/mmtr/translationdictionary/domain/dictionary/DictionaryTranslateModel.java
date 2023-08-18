package ru.mmtr.translationdictionary.domain.dictionary;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DictionaryTranslateModel {

    @Parameter(description = "Слово для перевода")
    private String word;
    @Parameter(description = "Идентификатор языка исходного слова")
    private UUID fromLanguage;
    @Parameter(description = "Идентификатор языка переведенного слова")
    private UUID toLanguage;

    public void setWord(String word) {
        this.word = word;
    }

    public void setFromLanguage(UUID fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public void setToLanguage(UUID toLanguage) {
        this.toLanguage = toLanguage;
    }
}
