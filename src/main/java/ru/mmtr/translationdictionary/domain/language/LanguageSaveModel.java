package ru.mmtr.translationdictionary.domain.language;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageSaveModel {
    @Parameter(description = "Язык")
    private String languageName;
}
