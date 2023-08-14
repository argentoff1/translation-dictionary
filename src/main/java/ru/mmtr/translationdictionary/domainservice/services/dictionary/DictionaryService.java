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
        return null;
    }

    public DictionaryModel getDictionary(UUID id) {


        DictionaryModel dictionaryModel = dictionaryRepository.getDictionary(id);

        return dictionaryModel;
    }

    public void saveDictionary(DictionaryEntity dictionary) {

    }

    public void deleteDictionary(UUID id) {

    }
}
