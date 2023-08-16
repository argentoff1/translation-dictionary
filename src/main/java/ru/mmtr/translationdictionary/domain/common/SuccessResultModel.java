package ru.mmtr.translationdictionary.domain.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResultModel extends GeneralResultModel {
    private boolean success;

    //private var entity;

    public SuccessResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        success = false;
    }

    public SuccessResultModel(boolean success) {
        this.success = success;
    }
}
