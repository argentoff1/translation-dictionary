package ru.mmtr.translationdictionary.domain.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResultModel {
    private String errorCode;

    private String errorMessage;
}
