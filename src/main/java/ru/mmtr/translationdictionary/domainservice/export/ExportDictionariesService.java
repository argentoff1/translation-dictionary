package ru.mmtr.translationdictionary.domainservice.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.mockito.Mock;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.MultipartFileResultModel;
import ru.mmtr.translationdictionary.domain.common.GUIDResultModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.StringResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;
import ru.mmtr.translationdictionary.domain.language.LanguageModel;
import ru.mmtr.translationdictionary.domain.user.UserModel;
import ru.mmtr.translationdictionary.domainservice.common.WriteListToFile;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;
import ru.mmtr.translationdictionary.domainservice.user.UserService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static ru.mmtr.translationdictionary.domainservice.common.Validation.isValidUUID;

@Service
@Slf4j
public class ExportDictionariesService {
    private final LanguageService languageService;
    private final DictionaryService dictionaryService;
    private final UserService userService;
    private List<ExportDictionariesModel> exportDictionariesModels;

    public ExportDictionariesService(LanguageService languageService, DictionaryService dictionaryService, UserService userService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    public GUIDResultModel exportDictionary() {
        MultipartFile multipartFile = new MockMu();

        var dictionaryCriteria = new DictionaryPageRequestModel();
        dictionaryCriteria.setPageNum(0);
        Integer PAGE_SIZE = 100;
        dictionaryCriteria.setPageSize(PAGE_SIZE);
        PageResultModel<DictionaryModel> page;
        UUID dictionaryExportFileUUID;

        // Создание файла. Создание нужных колонок для данных
        dictionaryExportFileUUID = UUID.randomUUID();

        var createdFile = WriteListToFile.createFile("C:\\Users\\parinos.ma.kst\\" +
                "IdeaProjects\\" + "translation-dictionary\\src\\main\\resources\\export\\"
                + dictionaryExportFileUUID + ".xlsx");

        // Обогащение пагинации данными из модели
        do {
            // Открытие файла, выбор нужной вкладки и класть страницу


            page = dictionaryService.getPage(dictionaryCriteria);
            dictionaryCriteria.setPageNum(dictionaryCriteria.getPageNum() + 1);

            // Экспорт данных из пагинации в модель
            exportDictionariesModels = page.getResultList().stream().map(dictionaryModel -> {

                var exportDictionaryModel = new ExportDictionariesModel();
                // Переложить все поля в модель из dictionaryModel
                exportDictionaryModel.setFromLanguageUUID(dictionaryModel.getFromLanguage());
                exportDictionaryModel.setToLanguageUUID(dictionaryModel.getToLanguage());
                exportDictionaryModel.setWord(dictionaryModel.getWord());
                exportDictionaryModel.setTranslation(dictionaryModel.getTranslation());
                exportDictionaryModel.setCreatedUserId(dictionaryModel.getCreatedUserId());
                exportDictionaryModel.setCreatedAt(dictionaryModel.getCreatedAt());
                exportDictionaryModel.setModifiedUserId(dictionaryModel.getModifiedUserId());
                exportDictionaryModel.setModifiedAt(dictionaryModel.getModifiedAt());

                return exportDictionaryModel;
            }).toList();

            var languageFromIds = exportDictionariesModels.stream()
                    .map(ExportDictionariesModel::getFromLanguageUUID)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var languageToIds = exportDictionariesModels.stream()
                    .map(ExportDictionariesModel::getToLanguageUUID)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var userCreatorsIds = exportDictionariesModels.stream()
                    .map(ExportDictionariesModel::getCreatedUserId)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            var userModifiersIds = exportDictionariesModels.stream()
                    .map(ExportDictionariesModel::getModifiedUserId)
                    .distinct().filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Вызывать метод из репозитория, который вернет модели. На вход список guid
            // получить все остальные сущности, остальные ID
            Map<UUID, LanguageModel> fromLanguageNamesMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(languageFromIds)) {
                fromLanguageNamesMap = languageService.getByIds(languageFromIds);
            }

            Map<UUID, LanguageModel> toLanguageNamesMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(languageToIds)) {
                toLanguageNamesMap = languageService.getByIds(languageToIds);
            }

            Map<UUID, UserModel> userCreatorsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(userCreatorsIds)) {
                userCreatorsMap = userService.getByIds(userCreatorsIds);
            }

            Map<UUID, UserModel> userModifiersMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(userModifiersIds)) {
                userModifiersMap = userService.getByIds(userModifiersIds);
            }

            // Засунуть Map в модель
            for (var exportDictionariesModel : exportDictionariesModels) {
                var fromLanguageName = fromLanguageNamesMap.get(exportDictionariesModel.getFromLanguageUUID());
                if (fromLanguageName != null) {
                    exportDictionariesModel.setFromLanguageName(fromLanguageName.getLanguageName());
                }

                var toLanguageName = toLanguageNamesMap.get(exportDictionariesModel.getToLanguageUUID());
                if (toLanguageName != null) {
                    exportDictionariesModel.setToLanguageName(toLanguageName.getLanguageName());
                }

                var userCreator = userCreatorsMap.get(exportDictionariesModel.getCreatedUserId());
                if (userCreator != null) {
                    exportDictionariesModel.setFullName(userCreator.getLastName() + " "
                            + userCreator.getFirstName());
                }

                var userModifier = userModifiersMap.get(exportDictionariesModel.getModifiedUserId());
                if (userModifier != null) {
                    exportDictionariesModel.setFullName(userModifier.getLastName() + " "
                            + userModifier.getFirstName());
                }
            }

            try {
                dictionaryExportFileUUID = UUID.randomUUID();

                WriteListToFile.writeExportListToFile("C:\\Users\\parinos.ma.kst\\" +
                        "IdeaProjects\\" + "translation-dictionary\\src\\main\\resources\\export\\"
                        + dictionaryExportFileUUID + ".xlsx", exportDictionariesModels);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new GUIDResultModel("CAN_NOT_EXPORT",
                        "Не удалось экспортировать данные");
            }
        } while (page.getResultList().size() == PAGE_SIZE);

        return new GUIDResultModel(dictionaryExportFileUUID);
    }

    // return type - MultipartFile
    public MultipartFileResultModel getExportDictionary(UUID id) {
        //byte[] result = new byte[];

        if (!isValidUUID(String.valueOf(id))) {
            return new MultipartFileResultModel("CAN_NOT_UPDATE",
                    "Не удалось обновить данные. Поля должны быть корректно заполнены");
        }

        String filePath = "C:\\Users\\parinos.ma.kst\\IdeaProjects\\" +
                "translation-dictionary\\src\\main\\resources\\export\\";

        try {
            File file = new File(filePath + id + ".xlsx");

            FileInputStream fileInputStream = new FileInputStream(file);

            long fileSize = file.length();

            byte[] fileBytes = new byte[(int) fileSize];

            fileInputStream.read(fileBytes);

            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            MultipartFile multipartFile = new MultipartFile() {
                @Override
                public String getName() {
                    return file.getName();
                }

                @Override
                public String getOriginalFilename() {
                    return file.getName();
                }

                @Override
                public String getContentType() {
                    return MediaType.APPLICATION_OCTET_STREAM_VALUE;
                }

                @Override
                public boolean isEmpty() {
                    return fileBytes.length == 0;
                }

                @Override
                public long getSize() {
                    return fileSize;
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return fileBytes;
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return new ByteArrayInputStream(fileBytes);
                }

                @Override
                public void transferTo(File dest) throws IOException, IllegalStateException {
                    try (OutputStream outputStream = new FileOutputStream(dest)) {
                        outputStream.write(fileBytes);
                    }
                }
            };

            //String strJson = null;

            //ClassPathResource classPathResource = new ClassPathResource(filePath + id + ".xlsx");
            /*ClassPathResource classPathResource = new ClassPathResource("json/data.json");
            try {
                byte[] binaryData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
                strJson = new String(binaryData, StandardCharsets.UTF_8);
            } catch (IOException exception) {
                exception.printStackTrace();
            }*/
            //return new StringResultModel(strJson);
            return new MultipartFileResultModel(multipartFile);

        } catch (IOException e) {
            return new MultipartFileResultModel("CAN_NOT_FIND_FILE",
                    "Не удалось найти файл по данному идентификатору");
        }


        /*try (
                FileInputStream fileInputStream = new FileInputStream(file);
                Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            // Кривая проверка на корректность файла. Мб вообще не поможет
            if (fileInputStream.readAllBytes() == null) {
                return new MultipartFileResultModel("CAN_NOT_FIND_FILE",
                        "Не удалось найти файл по данному идентификатору");
            }
            Sheet sheet = workbook.getSheetAt(0);

            *//*for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.STRING) {
                        System.out.print(cell.getStringCellValue() + "\t");
                    } else if (cellType == CellType.NUMERIC) {
                        System.out.print(cell.getNumericCellValue() + "\t");
                    } else if (cellType == CellType.BLANK) {
                        System.out.print("\t");
                    }
                }
                System.out.println();
            }*//*
        } catch (
                IOException e) {
            log.error(e.getMessage(), e);
        }*/

        //return new MultipartFileResultModel("asdasdsdad", "qwdqwd");
    }

    /*public MultipartFile getMultipartFile(MultipartFile multipartFile) {
        try {
            // Создаем временный файл
            File tempFile = File.createTempFile("temp", ".xlsx");

            // Получаем входной поток из MultipartFile
            InputStream inputStream = multipartFile.getInputStream();

            // Создаем выходной поток для записи содержимого во временный файл
            OutputStream outputStream = new FileOutputStream(tempFile);

            // Копируем содержимое из входного потока в выходной поток
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Закрываем потоки
            outputStream.close();
            inputStream.close();

            // Возвращаем временный файл как MultipartFile
            return new Mock(multipartFile.getName(), multipartFile.getOriginalFilename(), multipartFile.getContentType(), tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }*/
}
