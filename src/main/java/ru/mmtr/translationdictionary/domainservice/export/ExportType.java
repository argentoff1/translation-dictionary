package ru.mmtr.translationdictionary.domainservice.export;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat
public enum ExportType {
    DICTIONARY("dictionary"),
    LANGUAGE("language");

    private final String modelName;
}
