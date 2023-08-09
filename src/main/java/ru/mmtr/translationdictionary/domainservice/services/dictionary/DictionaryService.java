package ru.mmtr.translationdictionary.domainservice.services.dictionary;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.infrastruction.repositories.dictionary.DictionaryEntity;

import java.util.List;

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
