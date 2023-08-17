package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import io.ebean.DB;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.PageModel;
import ru.mmtr.translationdictionary.domain.common.PageResultModel;
import ru.mmtr.translationdictionary.domain.common.SuccessResultModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.infrastructure.repositories.language.LanguageEntity;

import java.sql.Timestamp;
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

    public PageResultModel<DictionaryModel> showAllWithPagination(PageModel model) {
        var page = DB
                .find(DictionaryEntity.class)
                .setMaxRows(model.getPageSize())
                .setFirstRow((model.getPageNum() - 1) * model.getPageSize())
                .findPagedList();

        return new PageResultModel<>(
                page.getTotalCount(),
                page.getList().stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    public DictionaryModel getById(UUID id) {
        DictionaryEntity foundDictionaryEntity = DB
                .find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .findOne();

        return getModel(foundDictionaryEntity);
    }

    public DictionaryModel getTranslatedWord(String word, UUID fromLanguage, UUID toLanguage) {
        DictionaryEntity foundTranslation = DB.
                find(DictionaryEntity.class).
                where().
                ilike(DictionaryEntity.WORD, "%" + word + "%").
                eq(DictionaryEntity.FROM_LANGUAGE, fromLanguage).
                eq(DictionaryEntity.TO_LANGUAGE, toLanguage).
                findOne();

        return getTranslation(foundTranslation);
    }

    public SuccessResultModel save(String word, String translation,
                                UUID fromLanguage, UUID toLanguage) {

        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setDictionaryId(UUID.randomUUID());
        dictionaryEntity.setWord(word);
        dictionaryEntity.setTranslation(translation);
        dictionaryEntity.setFromLanguage(fromLanguage);
        dictionaryEntity.setToLanguage(toLanguage);

        Timestamp date = new Timestamp(System.currentTimeMillis());
        dictionaryEntity.setCreatedAt(date);

        /*dictionaryEntity.setFromLanguage(dictionaryEntity.getFromLanguage());
        dictionaryEntity.setToLanguage(dictionaryEntity.getToLanguage());*/
        DB.insert(dictionaryEntity);

        return new SuccessResultModel(true);
    }

    public SuccessResultModel update(UUID id, String word, String translation) {
        int updatedRows = DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .asUpdate()
                .set(DictionaryEntity.WORD, word)
                .set(DictionaryEntity.TRANSLATION, translation)
                .update();

        // Не робит
        if (updatedRows > 0) {
            LanguageEntity foundEntity = DB.find(LanguageEntity.class)
                    .where()
                    .eq(LanguageEntity.LANGUAGE_ID, id)
                    .findOne();

            if (foundEntity != null) {
                Timestamp date = new Timestamp(System.currentTimeMillis());
                foundEntity.setModifiedAt(date);
            }
        }

        /*DictionaryEntity entity = new DictionaryEntity();
        entity.setDictionaryId(UUID.randomUUID());
        entity.setWord(word);
        entity.setTranslation(translation);
        entity.setFromLanguage(entity.getFromLanguage());
        entity.setToLanguage(entity.getToLanguage());*/

        return new SuccessResultModel(true);
    }

    public SuccessResultModel delete(UUID id) {
        DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .delete();

        return new SuccessResultModel(true);
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

    private DictionaryModel getTranslation(DictionaryEntity entity) {
        if (entity == null) {
            return null;
        }

        var model = new DictionaryModel();
        model.setTranslation(entity.getTranslation());
        return model;
    }
}
