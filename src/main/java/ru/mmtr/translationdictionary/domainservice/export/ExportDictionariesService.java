package ru.mmtr.translationdictionary.domainservice.export;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;
import ru.mmtr.translationdictionary.domain.export.ExportDictionariesModel;
import ru.mmtr.translationdictionary.domain.language.LanguagePageRequestModel;
import ru.mmtr.translationdictionary.domainservice.dictionary.DictionaryService;
import ru.mmtr.translationdictionary.domainservice.language.LanguageService;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExportDictionariesService {
    private final Integer PAGE_SIZE = 100;
    private final LanguageService languageService;
    private final DictionaryService dictionaryService;

    public ExportDictionariesService(LanguageService languageService, DictionaryService dictionaryService) {
        this.languageService = languageService;
        this.dictionaryService = dictionaryService;
    }

    public MultipartFile exportDictionary() {
        MultipartFile multipartFile = null;

        var dictionaryCriteria = new DictionaryPageRequestModel();
        dictionaryCriteria.setPageNum(0);
        dictionaryCriteria.setPageSize(PAGE_SIZE);

        /*var languageCriteria = new LanguagePageRequestModel();
        languageCriteria.setPageNum(0);
        languageCriteria.setPageSize(PAGE_SIZE);*/

        PageResultModel<DictionaryModel> page;

        List<ExportDictionariesModel> exportDictionariesModelList = new ArrayList<>();
        List<UUID> fromLanguageList = new ArrayList<>();
        List<UUID> toLanguageList = new ArrayList<>();

        ArrayList<String> wordsList = new ArrayList<>();
        List<String> translationsList = new ArrayList<>();
        List<UUID> createdUserIdsList = new ArrayList<>();
        List<LocalDateTime> createdAtList = new ArrayList<>();
        List<UUID> modifiedUserIdsList = new ArrayList<>();
        List<LocalDateTime> modifiedAtList = new ArrayList<>();

        do {
            page = dictionaryService.getPage(dictionaryCriteria);
            dictionaryCriteria.setPageNum(dictionaryCriteria.getPageNum() + 1);

            for (var model : page.getResultList()) {
                fromLanguageList.add(model.getFromLanguage());
                toLanguageList.add(model.getToLanguage());

                wordsList.add(model.getWord());
                translationsList.add(model.getTranslation());
                createdUserIdsList.add(model.getCreatedUserId());
                createdAtList.add(model.getCreatedAt());
                modifiedUserIdsList.add(model.getModifiedUserId());
                modifiedAtList.add(model.getModifiedAt());
            }
        } while (page.getResultList().size() == PAGE_SIZE);

        try {
            multipartFile = convertDataFromListToFile(wordsList);

            saveMultipartFile(multipartFile, "C:\\Users\\parinos.ma.kst\\Export\\data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return multipartFile;
    }

    private MultipartFile convertDataFromListToFile(ArrayList<String> data) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : data) {
            stringBuilder.append(item).append("\n");
        }

        byte[] contentBytes = stringBuilder.toString().getBytes();
        InputStream inputStream = new ByteArrayInputStream(contentBytes);

        return new MultipartFile() {
            @Override
            public String getName() {
                return "data.txt";
            }

            @Override
            public String getOriginalFilename() {
                return "data.txt";
            }

            @Override
            public String getContentType() {
                return "text/plain";
            }

            @Override
            public boolean isEmpty() {
                return contentBytes.length == 0;
            }

            @Override
            public long getSize() {
                return contentBytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return contentBytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return inputStream;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
    }

    public String saveMultipartFile(MultipartFile multipartFile, String destinationPath) throws IOException {
        File file = new File(destinationPath);
        file.getParentFile().mkdirs();
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileCopyUtils.copy(multipartFile.getInputStream(), fileOutputStream);
        fileOutputStream.close();
        return file.getAbsolutePath();
    }
}
