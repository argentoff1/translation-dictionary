package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import io.ebean.DB;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DictionaryRepository {
    public List<DictionaryModel> showAll() {
        List<DictionaryEntity> dictionaryEntities = DB
                .find(DictionaryEntity.class)
                .findList();
        return dictionaryEntities.stream().map(this::getModel).collect(Collectors.toList());
    }

    public DictionaryModel getById(UUID id) {
        DictionaryEntity foundDictionaryEntity = DB
                .find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .findOne();

        return getModel(foundDictionaryEntity);
    }

    public DictionaryModel save(String word, String translation,
                                            UUID fromLanguage, UUID toLanguage) {

        DictionaryEntity entity = new DictionaryEntity();
        entity.setDictionaryId(UUID.randomUUID());
        entity.setWord(word);
        entity.setTranslation(translation);
        entity.setFromLanguage(fromLanguage);
        entity.setToLanguage(toLanguage);
        entity.setFromLanguage(entity.getFromLanguage());
        entity.setToLanguage(entity.getToLanguage());
        DB.insert(entity);

        return getModel(entity);
    }

    public DictionaryModel update(UUID id, String word, String translation) {
        DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .asUpdate()
                .set(DictionaryEntity.WORD, word)
                .set(DictionaryEntity.TRANSLATION, translation)
                .update();

        DictionaryEntity entity = new DictionaryEntity();
        entity.setDictionaryId(UUID.randomUUID());
        entity.setWord(word);
        entity.setTranslation(translation);
        entity.setFromLanguage(entity.getFromLanguage());
        entity.setToLanguage(entity.getToLanguage());

        return getModel(entity);
    }

    public boolean delete(UUID id) {
        DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .delete();

        return true;
    }

    private DictionaryModel getModel(DictionaryEntity entity) {
        var model = new DictionaryModel();
        model.setDictionaryId(entity.getDictionaryId());
        model.setWord(entity.getWord());
        model.setTranslation(entity.getTranslation());
        model.setFromLanguage(entity.getFromLanguage());
        model.setToLanguage(entity.getToLanguage());
        return model;
    }
}
