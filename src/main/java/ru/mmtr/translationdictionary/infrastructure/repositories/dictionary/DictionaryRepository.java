package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.models.dictionary.DictionaryModel;

import java.util.List;
import java.util.UUID;

@Repository
public class DictionaryRepository {
    public List<DictionaryEntity> getAllDictionaries() {
        return null;
    }

    public DictionaryModel getDictionary(UUID id) {


        return null;
    }

    public DictionaryModel createDictionary() {
        return null;
    }

    public void saveDictionary(DictionaryEntity dictionary) {

    }

    public void deleteDictionary(UUID id) {

    }
}
