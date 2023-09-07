package ru.mmtr.translationdictionary.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: parinos.ma.kst
 * \* Date: 9/7/2023
 * \* Description:
 * \*  Результирующая модель для файлов
 */

@Getter
@Setter
@NoArgsConstructor
public class MultipartFileResultModel extends GeneralResultModel {
    @Schema(name = "Результирующий файл")
    private MultipartFile multipartFile;

    public MultipartFileResultModel(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
        multipartFile = null;
    }

    public MultipartFileResultModel(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
