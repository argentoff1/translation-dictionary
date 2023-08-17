package ru.mmtr.translationdictionary.infrastructure.repositories.dictionary;

import io.ebean.DB;
import io.ebean.ExpressionList;
import org.springframework.stereotype.Repository;
import ru.mmtr.translationdictionary.domain.common.*;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryModel;
import ru.mmtr.translationdictionary.domain.dictionary.DictionaryPageRequestModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// Получение коллекции ID's, возвращает коллекцию слов
@Repository
public class DictionaryRepository {
    public CollectionResultModel<DictionaryModel> showAll() {
        List<DictionaryEntity> dictionaryEntities = DB
                .find(DictionaryEntity.class)
                .findList();

        return new CollectionResultModel<>(
                dictionaryEntities.stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    // Доделать!!!!!!!
    /*public CollectionResultModel<> getAllByIds() {

    }*/

    public PageResultModel<DictionaryModel> getPage (DictionaryPageRequestModel criteria) {
        var expression = DB
                .find(DictionaryEntity.class)
                .setMaxRows(criteria.getPageSize())
                .setFirstRow((criteria.getPageNum() - 1) * criteria.getPageSize())
                .where();

        expression = applyFilters(expression, criteria);

        var page = expression.findPagedList();

        return new PageResultModel<>(
                page.getTotalCount(),
                page.getList().stream().map(this::getModel).collect(Collectors.toList())
        );
    }

    private ExpressionList<DictionaryEntity> applyFilters(ExpressionList<DictionaryEntity> expression,
                                                          DictionaryPageRequestModel criteria) {
        if (criteria.getCreateDateFromFilter() != null) {
            expression = expression.ge(DictionaryEntity.DICTIONARY_CREATED_AT, criteria.getCreateDateFromFilter());
        }

        return expression;
    }

    public DictionaryModel getById(UUID id) {
        DictionaryEntity foundDictionaryEntity = DB
                .find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .findOne();

        return getModel(foundDictionaryEntity);
    }

    public StringResultModel getTranslatedWord(String word, UUID fromLanguage, UUID toLanguage) {
        DictionaryEntity foundTranslation = DB.
                find(DictionaryEntity.class).
                where().
                ilike(DictionaryEntity.WORD, "%" + word + "%").
                eq(DictionaryEntity.FROM_LANGUAGE, fromLanguage).
                eq(DictionaryEntity.TO_LANGUAGE, toLanguage).
                findOne();

        return new StringResultModel(foundTranslation.getTranslation());
    }

    public SuccessResultModel save(String word, String translation,
                                UUID fromLanguage, UUID toLanguage) {

        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setDictionaryId(UUID.randomUUID());
        dictionaryEntity.setWord(word);
        dictionaryEntity.setTranslation(translation);
        dictionaryEntity.setFromLanguage(fromLanguage);
        dictionaryEntity.setToLanguage(toLanguage);
        dictionaryEntity.setCreatedAt(LocalDateTime.now());

        /*dictionaryEntity.setFromLanguage(dictionaryEntity.getFromLanguage());
        dictionaryEntity.setToLanguage(dictionaryEntity.getToLanguage());*/
        DB.insert(dictionaryEntity);

        return new SuccessResultModel(true);
    }
    // Можно тут int, а сервис уже Success
    public Integer update(UUID id, String word, String translation) {

        /*DictionaryEntity entity = new DictionaryEntity();
        entity.setDictionaryId(UUID.randomUUID());
        entity.setWord(word);
        entity.setTranslation(translation);
        entity.setFromLanguage(entity.getFromLanguage());
        entity.setToLanguage(entity.getToLanguage());*/

        return DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .asUpdate()
                .set(DictionaryEntity.WORD, word)
                .set(DictionaryEntity.TRANSLATION, translation)
                .set(DictionaryEntity.DICTIONARY_MODIFIED_AT, LocalDateTime.now())
                .update();
    }

    public Integer delete(UUID id) {
        return DB.find(DictionaryEntity.class)
                .where()
                .eq(DictionaryEntity.DICTIONARY_ID, id)
                .delete();
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
