package ru.mmtr.translationdictionary.domainservice.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExportModelsEnum {
    Dictionary("Dictionary"),
    Language("Language");

    private final String modelName;
}
