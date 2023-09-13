package ru.mmtr.translationdictionary.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileStoreService {
    private static final String FILE_PATH = "C:\\Users\\Maxim Parinos\\IdeaProjects\\2023" +
            "\\translation-dictionary\\src\\main\\resources\\export\\";
    /*private static final String FILE_PATH = "C:\\Users\\parinos.ma.kst\\IdeaProjects\\" +
            "translation-dictionary\\src\\main\\resources\\export\\";*/

    public GUIDResultModel createFile(Workbook workbook) {
        return new GUIDResultModel(WriteListToFile.writeInFile(FILE_PATH, workbook));
    }

    public MultipartFile getFile(UUID id) {
        try {
            File file = new File(FILE_PATH + id + ".xlsx");
            if (!file.exists()) {
                log.error("Файла по указанному пути не существует");
            }
            FileInputStream input = new FileInputStream(file);

            return new MockMultipartFile("C:/Users/Maxim Parinos/IdeaProjects/2023" +
                    "translation-dictionary/src/main/resources/export/", file.getName(),
                    "multipart/form-data", IOUtils.toByteArray(input));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public InputStreamResource getInputStreamResource(MultipartFile multipartFile) {
        InputStreamResource inputStreamResource;
        try {
            inputStreamResource = new InputStreamResource(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return inputStreamResource;
    }
}
