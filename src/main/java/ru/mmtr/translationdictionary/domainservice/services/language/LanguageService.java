package ru.mmtr.translationdictionary.domainservice.services.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageEntity;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import java.util.List;
import java.util.UUID;

// Бизнес-логика
@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public LanguageModel getLanguage(UUID id) {



        LanguageModel languageModel = languageRepository.getLanguage(id);

        return languageModel;
    }

    public List<LanguageEntity> getAllLanguages() {
        return null;
    }

    public LanguageModel createLanguage(String languageName) {



        LanguageModel languageModel = languageRepository.createLanguage(languageName);

        return languageModel;
    }

    public String saveLanguage(UUID id, String languageName) {
        //LanguageModel languageModel = languageRepository.saveLanguage(id);


        int savedRows = languageRepository.saveLanguage(id, languageName);

        return "Было обновлено " + savedRows + " строк";
    }

    public String deleteLanguage(UUID id) {




        int deletedRows = languageRepository.deleteLanguage(id);

        return "Было удалено " + deletedRows + " строк";
    }
}
