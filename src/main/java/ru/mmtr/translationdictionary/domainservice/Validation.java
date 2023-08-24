package ru.mmtr.translationdictionary.domainservice;

import org.apache.commons.lang3.StringUtils;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;

public abstract class Validation {
    public static SuccessResultModel stringValidation(String str, int countChars) {
        if (StringUtils.isBlank(str)) {
            return new SuccessResultModel("FIELD_MUST_BE_FILLED",
                    "Поле должно быть заполнено");
        }

        if (str.contains(" ")) {
            return new SuccessResultModel("FIELD_MUST_NOT_CONTAIN_SPACES",
                    "Поле не должно содержать пробелов");
        }

        int count = 0;
        for (char element : str.toCharArray()) {
            count++;
        }
        if (count > countChars) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS",
                    "Поле не должно быть больше 15 символов");
        }

        return new SuccessResultModel(true);
    }
}
