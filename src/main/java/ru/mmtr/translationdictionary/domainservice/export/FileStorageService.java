package ru.mmtr.translationdictionary.domainservice.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {
    /*private LanguageService languageService = null;
    private DictionaryService dictionaryService = null;
    private UserService userService = null;*/
    private static final String FILE_PATH = "C:\\Users\\parinos.ma.kst\\IdeaProjects\\" +
            "translation-dictionary\\src\\main\\resources\\export\\";

    ExportMethods exportMethods;

    public void setExportMethods(ExportMethods exportMethods) {
        this.exportMethods = exportMethods;
    }

    public GUIDResultModel createExport() {
        return exportMethods.createExport();
    }

    public ExportType getType() {
        return exportMethods.getType();
    }

    public MultipartFile getFile(UUID id) {
        try {
            File file = new File(FILE_PATH + id + ".xlsx");
            if (!file.exists()) {
                log.error("Файла по указанному пути не существует");
            }
            FileInputStream input = new FileInputStream(file);

            return new MockMultipartFile("C:/Users/parinos.ma.kst/IdeaProjects/" +
                    "translation-dictionary/src/main/resources/export/", file.getName(),
                    "multipart/form-data", IOUtils.toByteArray(input));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /*public FileStorageService(LanguageService languageService, DictionaryService dictionaryService, UserService userService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    ExportLanguageStrategy exportLanguage = new ExportLanguageStrategy(languageService, userService);

    ExportDictionaryStrategy exportDictionary = new ExportDictionaryStrategy(languageService, dictionaryService, userService);*/
}
