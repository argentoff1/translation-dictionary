package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import io.ebean.DB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageEntity;

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

    public Page<DictionaryModel> showAllWithPagination(Pageable pageable) {
        List<DictionaryEntity> entities = DB
                .find(DictionaryEntity.class)
                .findList();

        List<DictionaryModel> models = entities.stream().map(this::getModel).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), models.size());

        return new PageImpl<>(models.subList(start, end), pageable, models.size());
    }

    public DictionaryModel getById(UUID id) {
        DictionaryEntity foundDictionaryEntity = DB
                .find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .findOne();

        return getModel(foundDictionaryEntity);
    }

    public SuccessResultModel getTranslatedWord(String word, UUID fromLanguage, UUID toLanguage) {
        DB.
                find(DictionaryEntity.class).
                where().eq(DictionaryEntity.WORD, word).
                eq(DictionaryEntity.FROM_LANGUAGE, fromLanguage).
                eq(DictionaryEntity.TO_LANGUAGE, toLanguage).
                findOne();

        //return getModel(foundDictionaryEntity);
        return new SuccessResultModel(true);
    }

    public DictionaryModel save(String word, String translation,
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

        return getModel(dictionaryEntity);
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
        if (entity == null) {
            return null;
        }

        var model = new DictionaryModel();
        model.setDictionaryId(entity.getDictionaryId());
        model.setWord(entity.getWord());
        model.setTranslation(entity.getTranslation());
        model.setFromLanguage(entity.getFromLanguage());
        model.setToLanguage(entity.getToLanguage());
        return model;
    }
}
