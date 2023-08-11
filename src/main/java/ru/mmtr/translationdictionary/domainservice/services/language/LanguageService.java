package ru.mmtr.translationdictionary.domainservice.services.language;

import io.ebean.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.models.language.LanguageModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageRepository;

import java.util.UUID;

// Бизнес-логика
@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepository;

    public LanguageModel getLanguage(UUID id) {
















        LanguageModel foundLanguageModel = languageRepository.getLanguage(id);

        return foundLanguageModel;
    }

    /*
    public List<LanguageEntity> getAllLanguages() {
        return null;
    }

    // МБ надо возвращать Entity
    public LanguageModel createLanguage(LanguageRepository languageRepository) {
        languageRepository.createLanguage();
    }

    public void saveLanguage(LanguageRepository languageRepository) {
        languageRepository.saveLanguage()
    }

    public void deleteLanguage(int id) {

    }*/
}
