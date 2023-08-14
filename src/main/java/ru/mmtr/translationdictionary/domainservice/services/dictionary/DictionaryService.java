package ru.mmtr.translationdictionary.domainservice.services.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.models.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryEntity;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DictionaryService {
    @Autowired
    private DictionaryRepository dictionaryRepository;

    public List<DictionaryEntity> getAllDictionaries() {


        List<DictionaryEntity> dictionaryEntities = dictionaryRepository.getAllDictionaries();

        return dictionaryEntities;
    }

    public DictionaryModel getDictionary(UUID id) {




        DictionaryModel dictionaryModel = dictionaryRepository.getDictionary(id);

        return dictionaryModel;
    }

    public DictionaryModel createDictionary(String word, String translation, UUID fromLanguage, UUID toLanguage) {



        DictionaryModel dictionaryModel = dictionaryRepository.createDictionary(word, translation, fromLanguage, toLanguage);

        return dictionaryModel;
    }

    public String saveDictionary(UUID id, String word, String translation) {



        int savedRows = dictionaryRepository.saveDictionary(id, word, translation);

        return "Было обновлено " + savedRows + " строк";
    }

    public String deleteDictionary(UUID id) {



        int deletedRows = dictionaryRepository.deleteDictionary(id);

        return "Было удалено " + deletedRows + " строк";
    }
}
