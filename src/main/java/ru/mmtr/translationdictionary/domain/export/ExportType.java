package ru.mmtr.translationdictionary.domain.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExportType {
    DICTIONARY("dictionary"),
    LANGUAGE("language"),
    USER("user");
    private final String modelName;
}
