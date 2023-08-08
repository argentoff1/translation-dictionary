package ru.mmtr.translationdictionary.infrastruction.repositories.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import ru.mmtr.translationdictionary.infrastruction.entities.dictionary.DictionaryEntity;

import java.util.List;
import java.util.Optional;

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
