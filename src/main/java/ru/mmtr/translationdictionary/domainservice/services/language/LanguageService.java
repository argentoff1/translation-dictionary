package ru.mmtr.translationdictionary.domainservice.services.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.infrastruction.repositories.dictionary.DictionaryEntity;
import ru.mmtr.translationdictionary.infrastruction.repositories.language.LanguageEntity;
import ru.mmtr.translationdictionary.infrastruction.repositories.language.LanguageRepository;

import java.util.List;

// Бизнес-логика
@Service
public class LanguageService {

    public List<LanguageEntity> getAllLanguages() {
        return null;
    }

    public LanguageEntity getLanguage(int id) {
        return null;
    }

    public void saveLanguage(LanguageEntity language) {

    }

    public void deleteLanguage(int id) {

    }
}
