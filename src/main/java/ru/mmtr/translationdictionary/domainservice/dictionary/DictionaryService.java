package ru.mmtr.translationdictionary.domainservice.dictionary;

import org.springframework.stereotype.Service;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.dictionary.DictionaryRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public List<DictionaryModel> showAll() {


        return dictionaryRepository.showAll();
    }

    public DictionaryModel getById(UUID id) {
        return dictionaryRepository.getById(id);
    }

    public DictionaryModel save(String word, String translation, UUID fromLanguage, UUID toLanguage) {


        var result = dictionaryRepository.save(word, translation, fromLanguage, toLanguage);
        if (Objects.isNull(result)) {

        }
        return result;
    }

    public DictionaryModel update(UUID id, String word, String translation) {

        return dictionaryRepository.update(id, word, translation);
    }

    public boolean delete(UUID id) {
        boolean isDeleted;



        isDeleted = dictionaryRepository.delete(id);



        return isDeleted;
    }
}
