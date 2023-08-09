package ru.mmtr.translationdictionary.infrastruction.repositories.dictionary;

import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.infrastruction.entities.dictionary.DictionaryEntity;

import java.util.List;

@Repository
public class DictionaryRepository {
    public List<DictionaryEntity> getAllDictionaries() {
        return null;
    }

    public DictionaryEntity getDictionary(int id) {


        return null;
    }

    public void saveDictionary(DictionaryEntity dictionary) {

    }

    public void deleteDictionary(int id) {

    }
}
