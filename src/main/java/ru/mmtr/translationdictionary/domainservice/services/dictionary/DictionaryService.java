package ru.mmtr.translationdictionary.domainservice.services.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.infrastruction.entities.dictionary.DictionaryEntity;
import ru.mmtr.translationdictionary.infrastruction.repositories.dictionary.DictionaryRepository;

import java.util.List;

// Бизнес-логика
@Service
public class DictionaryService {
    /*@Autowired
    private DictionaryRepository dictionaryRepository;*/

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
