package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import io.ebean.DB;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.models.dictionary.DictionaryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class DictionaryRepository {
    public List<DictionaryModel> getAllDictionaries() {
        List<DictionaryEntity> dictionaryEntities = DB
                .find(DictionaryEntity.class)
                .findList();

        List<DictionaryModel> dictionaryModels = new ArrayList<>();

        dictionaryEntities.forEach((dictionaryEntity) -> {
            var dictionaryModel = new DictionaryModel();
            dictionaryModel.setDictionaryId(dictionaryEntity.getDictionaryId());
            dictionaryModel.setWord(dictionaryEntity.getWord());
            dictionaryModel.setTranslation(dictionaryEntity.getTranslation());
            dictionaryModel.setFromLanguage(dictionaryEntity.getFromLanguage());
            dictionaryModel.setToLanguage(dictionaryEntity.getToLanguage());

            dictionaryModels.add(dictionaryModel);
        });

        return dictionaryModels;
    }

    public DictionaryModel getDictionary(UUID id) {
        DictionaryEntity foundDictionaryEntity = DB
                .find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .findOne();

        var dictionaryModel = new DictionaryModel();
        dictionaryModel.setDictionaryId(foundDictionaryEntity.getDictionaryId());
        dictionaryModel.setWord(foundDictionaryEntity.getWord());
        dictionaryModel.setTranslation(foundDictionaryEntity.getTranslation());
        dictionaryModel.setFromLanguage(foundDictionaryEntity.getFromLanguage());
        dictionaryModel.setToLanguage(foundDictionaryEntity.getToLanguage());

        return dictionaryModel;
    }

    public DictionaryModel createDictionary(String word, String translation,
                                            UUID fromLanguage, UUID toLanguage) {

        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setDictionaryId(UUID.randomUUID());
        dictionaryEntity.setWord(word);
        dictionaryEntity.setTranslation(translation);
        dictionaryEntity.setFromLanguage(fromLanguage);
        dictionaryEntity.setToLanguage(toLanguage);
        dictionaryEntity.setFromLanguage(dictionaryEntity.getFromLanguage());
        dictionaryEntity.setToLanguage(dictionaryEntity.getToLanguage());
        DB.insert(dictionaryEntity);

        var dictionaryModel = new DictionaryModel();
        dictionaryModel.setWord(dictionaryEntity.getWord());
        dictionaryModel.setTranslation(dictionaryEntity.getTranslation());
        dictionaryModel.setFromLanguage(dictionaryEntity.getFromLanguage());
        dictionaryModel.setToLanguage(dictionaryEntity.getToLanguage());

        return dictionaryModel;
    }

    public int saveDictionary(UUID id, String word, String translation) {
        int savedRows = DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .asUpdate()
                .set(DictionaryEntity.WORD, word)
                .set(DictionaryEntity.TRANSLATION, translation)
                .update();

        return savedRows;
    }

    public int deleteDictionary(UUID id) {
        int deletedRows = DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .delete();

        return deletedRows;
    }
}
