package ru.mmtr.translationdictionary.domainservice.common;

import org.apache.commons.lang3.StringUtils;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Validation {
    public static SuccessResultModel stringValidation(String str, int limitChars) {
        if (StringUtils.isBlank(str)) {
            return new SuccessResultModel("FIELD_MUST_BE_FILLED",
                    "Поле должно быть заполнено");
        }

        if (str.contains(" ")) {
            return new SuccessResultModel("FIELD_MUST_NOT_CONTAIN_SPACES",
                    "Поле не должно содержать пробелов");
        }

        if (str.length() > limitChars) {
            return new SuccessResultModel("FIELD_VALUE_OUT_OF_BOUNDS",
                    "Поле не должно быть больше 15 символов");
        }

        return new SuccessResultModel(true);
    }

    public static Boolean isValidUUID(String uuid) {
        String uuidRegex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern pattern = Pattern.compile(uuidRegex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(uuid).matches();
    }

    public static Boolean checkingForArchiving(LocalDateTime archiveDate) {
        return archiveDate == null;
    }
}
